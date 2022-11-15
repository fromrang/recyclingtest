package demo.recycling.controller;

import demo.recycling.dto.DefaultRes;
import demo.recycling.dto.StatusCode;
import demo.recycling.dto.Users;
import demo.recycling.repository.Program;
import demo.recycling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Program program;

    @PostMapping("/registration")
    public ResponseEntity registUser(@RequestBody Users users){
        if(users.getNickname() == null || users.getUserEmail() == null){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]parameter error"), HttpStatus.BAD_REQUEST);
        }
        Boolean result = userService.userNicknameCheck(users.getNickname());
        if(result) { //회원가입 가능
            userService.userRegister(users); //DB에 회원 추가
            String token = program.createToken(users.getNickname());
            HashMap<String, String> data = new HashMap<>();
            data.put("nickname", users.getNickname());
            data.put("jwt token", token);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]registration", data), HttpStatus.OK); //토큰 전달 완료

        }else{
            return new ResponseEntity(DefaultRes.res(StatusCode.CREATED, "[Fail]exist user"), HttpStatus.OK);
        }
    }




}
