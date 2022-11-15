package demo.recycling.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import demo.recycling.dto.*;
import demo.recycling.repository.Program;
import demo.recycling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class GoogleController {
    @Autowired
    Program program;

    @Autowired
    UserService userService;

    @Value("${google.login.url}")
    String googleLoginUrl;
    @Value("${google.client.id}")
    String googleClientId;
    @Value("${google.redirect.url}")
    String googleRedirectUrl;
    @Value("${google.secret}")
    String googleClientSecret;
    @Value("${google.auth.url}")
    String googleAuthUrl;

    // 구글 로그인창 호출
    @RequestMapping(value = "/getGoogleAuthUrl")
    public @ResponseBody String getGoogleAuthUrl(HttpServletRequest request) throws Exception {



        String reqUrl = googleLoginUrl + "/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUrl
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";

        return reqUrl;
    }

    // 구글 연동정보 조회
    @RequestMapping(value = "/google/auth")
    public ResponseEntity oauth_google(HttpServletRequest request, @RequestParam(value = "code") String authCode, HttpServletResponse response) throws Exception {

        // restTemplate 호출
        RestTemplate restTemplate = new RestTemplate();

        GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .code(authCode)
                .redirectUri(googleRedirectUrl)
                .grantType("authorization_code")
                .build();


        try {
            // Http Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GoogleOAuthRequest> httpRequestEntity = new HttpEntity<>(googleOAuthRequestParam, headers);
            ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(googleAuthUrl + "/token", httpRequestEntity, String.class);

            // ObjectMapper를 통해 String to Object로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
            GoogleLoginResponse googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleLoginResponse>() {});

            // 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
            String jwtToken = googleLoginResponse.getIdToken();

            // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
            String requestUrl = UriComponentsBuilder.fromHttpUrl(googleAuthUrl + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();

            String resultJson = restTemplate.getForObject(requestUrl, String.class);

            if(resultJson != null) {
                GoogleLoginDto userInfoDto = objectMapper.readValue(resultJson, new TypeReference<GoogleLoginDto>() {});
                String nickname = userService.userExistCheck(userInfoDto.getEmail());
                if(nickname.equals("false")){ // 닉네임 추가 창으로 넘어가기
                    return new ResponseEntity(DefaultRes.res(StatusCode.NOT_EXIST, "[Fail]not exist user", userInfoDto.getEmail()), HttpStatus.OK);
                }

                String token = program.createToken(nickname);
                HashMap<String, String> data = new HashMap<>();
                data.put("nickname", nickname);
                data.put("jwt token", token);
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]oauth_google", data), HttpStatus.OK);
            }
            else {
                throw new Exception("Google OAuth failed!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(null);

    }

}
