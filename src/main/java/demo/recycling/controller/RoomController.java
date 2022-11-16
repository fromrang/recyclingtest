package demo.recycling.controller;

import demo.recycling.dto.*;
import demo.recycling.repository.RoomDao;
import demo.recycling.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class RoomController {
    @Autowired
    RoomDao roomDao;
    @Autowired
    RoomService roomService;

    @Autowired
    Tag tag;

    //사용자 별 방리스트
    @GetMapping("/room/{nickname}")
    public ResponseEntity roomMyview(@PathVariable String nickname)  {
        if(nickname == null || nickname.equals("")) return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]parameter error"), HttpStatus.BAD_REQUEST);
        List<Room> myroomlist = roomService.selectMyRoom(nickname);
        if(myroomlist.isEmpty()){
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, "[FAIL]/room/{nickname}"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]myroomview", myroomlist), HttpStatus.OK);
    }
    
    //방 만들기
    @PostMapping("/community")
    public ResponseEntity createRoom(@RequestBody Room room){

        int result = roomService.insertRoom(room);

        if(result > -1 ){
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]roomInsert"), HttpStatus.OK);
        }else {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]roomInsert"), HttpStatus.BAD_REQUEST);
        }
    }

}
