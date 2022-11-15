package demo.recycling.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.scribejava.core.model.OAuth2AccessToken;
import demo.recycling.dto.NaverLoginDto;
import demo.recycling.repository.NaverLoginBO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/getNaverAuthUrl")
    public @ResponseBody String getNaverAuthUrl(HttpSession session) throws Exception {

        String reqUrl = naverLoginBO.getAuthorizationUrl(session);
        return reqUrl;
    }

    @RequestMapping(value = "/login/oauth2/code/naver")
    public String oauthNaver(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {


        session = request.getSession();
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String error = request.getParameter("error");
//        System.out.println(session.getAttribute("oauth_state"));
//        System.out.println(state);

        // 로그인 팝업창에서 취소버튼 눌렀을경우
        if ( error != null ){
            if(error.equals("access_denied")){
                return "FAIL";
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


        return (String) resultMap.get("email");

    }
}
