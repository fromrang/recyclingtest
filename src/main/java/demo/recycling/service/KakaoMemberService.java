package demo.recycling.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import demo.recycling.dto.KakaoLoginDto;
import demo.recycling.dto.KakaoLoginResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.HashMap;

@Service
public class KakaoMemberService{

    // 카카오 로그인 access_token 리턴
    public String getAccessToken(String code) throws Exception {

        String accessToken = "";

        // restTemplate을 사용하여 API 호출
        RestTemplate restTemplate = new RestTemplate();
        String reqUrl = "/oauth/token";
        URI uri = URI.create("https://kauth.kakao.com" + reqUrl);

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.set("grant_type", "authorization_code");
        parameters.set("client_id", "7a8c9cf9e63bae4750c392fc2390e44b");
        //parameters.set("redirect_uri", "http://localhost:7878/oauth/kakao/callback");

        parameters.set("redirect_uri", "http://yodidamayo.tk:8080/oauth/kakao/callback");
        parameters.set("code", code);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(uri, restRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
        KakaoLoginResponse kakaoLoginResponse = objectMapper.readValue(apiResponse.getBody(), new TypeReference<KakaoLoginResponse>() {});
       // String responseBody = apiResponse.getBody();
        accessToken = kakaoLoginResponse.getAccess_token();
        System.out.println(kakaoLoginResponse);
        //accessToken = (String) responseBody.get("access_token");

        return accessToken;
    }

    // 카카오 사용자 id 추출
    public String getKakaoUniqueNo(String accessToken) throws Exception {

        HashMap kakaoUniqueNo;

        // restTemplate을 사용하여 API 호출
        RestTemplate restTemplate = new RestTemplate();
        String reqUrl = "/v2/user/me";
        URI uri = URI.create("https://kapi.kakao.com" + reqUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + accessToken);
        headers.set("KakaoAK", "3ad0fa1ef488c8b77d31426574d80879");

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("property_keys", "[\"kakao_account.email\"]");

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(uri, restRequest, String.class);
        //System.out.println(apiResponse.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        KakaoLoginDto kakaoLoginDto = objectMapper.readValue(apiResponse.getBody(), new TypeReference<KakaoLoginDto>() {});
        //System.out.println(kakaoLoginDto);
        //JSONObject responseBody = apiResponse.getBody();
        //kakaoUniqueNo= kakaoLoginDto.getEmail();
        kakaoUniqueNo= kakaoLoginDto.getKakao_account();
        //System.out.println(kakaoUniqueNo.get("email"));
        //kakaoUniqueNo = Integer.toString(responseBody.getInt("id"));

        return (String) kakaoUniqueNo.get("email");

    }
}