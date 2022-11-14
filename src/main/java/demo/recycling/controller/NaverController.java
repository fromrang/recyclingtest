package demo.recycling.controller;

import com.github.scribejava.core.model.OAuth2AccessToken;
import demo.recycling.repository.NaverLoginBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

//        JSONParser parser = new JSONParser();
//        Gson gson = new Gson();

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


        // JSON 형태로 변환
//        Object obj = parser.parse(loginInfo);
//        JSONObject jsonObj = JSONObject.fromObject(gson.toJson(obj));
//        JSONObject callbackResponse = (JSONObject) jsonObj.get("response");
//        String naverUniqueNo = callbackResponse.get("id").toString();
//
//        if (naverUniqueNo != null && !naverUniqueNo.equals("")) {
//
//            /**
//
//             TO DO : 리턴받은 naverUniqueNo 해당하는 회원정보 조회 후 로그인 처리 후 메인으로 이동
//
//             */
//
//            // 네이버 정보조회 실패
//        } else {
//            throw new ErrorMessage("네이버 정보조회에 실패했습니다.");
//        }
        return loginInfo;
    }
}
