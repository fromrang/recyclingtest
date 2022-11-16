package demo.recycling.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.scribejava.core.model.OAuth2AccessToken;
import demo.recycling.dto.DefaultRes;
import demo.recycling.dto.NaverLoginDto;
import demo.recycling.dto.StatusCode;
import demo.recycling.repository.NaverLoginBO;
import demo.recycling.repository.Program;
import demo.recycling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
public class NaverController {
    @Autowired
    NaverLoginBO naverLoginBO;

    @Autowired
    Program program;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/getNaverAuthUrl")
    public @ResponseBody String getNaverAuthUrl(HttpSession session) throws Exception {

        String reqUrl = naverLoginBO.getAuthorizationUrl(session);
        return reqUrl;
    }

    @RequestMapping(value = "/login/oauth2/code/naver")
    public ResponseEntity oauthNaver(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {


        session = request.getSession();
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String error = request.getParameter("error");
//        System.out.println(session.getAttribute("oauth_state"));
//        System.out.println(state);

        // 로그인 팝업창에서 취소버튼 눌렀을경우
        if ( error != null ){
            if(error.equals("access_denied")){
                return ResponseEntity.badRequest().body(null);
            }
        }

        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
        //로그인 사용자 정보를 읽어온다.
        String loginInfo = naverLoginBO.getUserProfile(session, oauthToken);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        NaverLoginDto naverLoginDto = objectMapper.readValue(loginInfo, new TypeReference<NaverLoginDto>() {});
        HashMap resultMap = naverLoginDto.getResponse();

        String userEmail = (String) resultMap.get("email");

        String nickname = userService.userExistCheck(userEmail);
        if(nickname.equals("false")){ // 닉네임 추가 창으로 넘어가기
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_EXIST, "[Fail]not exist user", userEmail), HttpStatus.OK);
        }

        String token = program.createToken(nickname);
        HashMap<String, String> data = new HashMap<>();
        data.put("nickname", nickname);
        data.put("jwt token", token);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]oauthNaver", data), HttpStatus.OK);

    }
}
