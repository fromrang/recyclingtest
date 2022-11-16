package demo.recycling.controller;

import demo.recycling.dto.*;
import demo.recycling.repository.RoomDao;
import demo.recycling.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {
    @Autowired
    RoomDao roomDao;
    @Autowired
    RoomService roomService;

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
