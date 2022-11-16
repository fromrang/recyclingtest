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

    @GetMapping("/room/{nickname}")
    public ResponseEntity roomMyview(@PathVariable String nickname) throws NoSuchAlgorithmException {
        List<Room> myroomlist = roomService.selectMyRoom(nickname);
        List<String> taglist = roomService.selectTag(nickname);
        for(int i=0;i<taglist.size(); i++){
            myroomlist.get(i).setTag(taglist.get(i));
        }
        System.out.println(myroomlist);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]myroomview", myroomlist), HttpStatus.OK);
    }

    @PostMapping("/community")
    public ResponseEntity createRoom(@RequestBody Room room){
        System.out.println(room.getTags());
        int result = roomService.insertRoom(room);
        String stringTag = String.join("$",room.getTags()); // List Type을 String Type으로 변환 해주기
        int result2 = roomService.insertMember(room.getNickname());
        int result3 = roomService.insertTag(room, stringTag);
        if(result > 0 || result2 > 0 || result3>0){
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]roomInsert"), HttpStatus.OK);
        }else {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]roomInsert"), HttpStatus.BAD_REQUEST);
        }
    }

}
