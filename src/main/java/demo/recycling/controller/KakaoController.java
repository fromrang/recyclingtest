package demo.recycling.controller;

import demo.recycling.dto.DefaultRes;
import demo.recycling.dto.StatusCode;
import demo.recycling.repository.Program;
import demo.recycling.service.KakaoMemberService;
import demo.recycling.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@AllArgsConstructor
public class KakaoController {
    @Autowired
    Program program;

    @Autowired
    UserService userService;

    @Autowired
    KakaoMemberService kakaoMemberService;

    @GetMapping("/kakao/login")
    public String kakaoCallback() {
        return "https://kauth.kakao.com/oauth/authorize?client_id=7a8c9cf9e63bae4750c392fc2390e44b&redirect_uri=http://localhost:7878/oauth/kakao/callback&response_type=code";
    }

    // 카카오 연동정보 조회
    @RequestMapping(value = "/oauth/kakao/callback")
    public ResponseEntity oauthKakao(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String code = request.getParameter("code");
        String error = request.getParameter("error");
        // 카카오로그인 페이지에서 취소버튼 눌렀을경우
        if (error != null) {
            if (error.equals("access_denied")) {
                return ResponseEntity.badRequest().body(null);
            }
        }

        String accessToken = kakaoMemberService.getAccessToken(code);
        //System.out.println("!!!!"+accessToken);
        String kakaoUniqueNo = kakaoMemberService.getKakaoUniqueNo(accessToken);

        if (kakaoUniqueNo != null && !kakaoUniqueNo.equals("")) {
            /**
             TO DO : 리턴받은 kakaoUniqueNo에 해당하는 회원정보 조회 후 로그인 처리 후 메인으로 이동
             */
            String nickname = userService.userExistCheck(kakaoUniqueNo);
            if(nickname.equals("false")){ // 닉네임 추가 창으로 넘어가기
                return new ResponseEntity(DefaultRes.res(StatusCode.NOT_EXIST, "[Fail]not exist user", kakaoUniqueNo), HttpStatus.OK);
            }

            String token = program.createToken(nickname);
            HashMap<String, String> data = new HashMap<>();
            data.put("nickname", nickname);
            data.put("jwt token", token);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]oauth/kakao/callback", data), HttpStatus.OK);

        } else {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[Fail]oauth/kakao/callback"), HttpStatus.BAD_REQUEST);
        }

    }

}
