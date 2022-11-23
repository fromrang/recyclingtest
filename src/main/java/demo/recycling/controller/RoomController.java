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
    public ResponseEntity roomMyview(@PathVariable String nickname) {
        if (nickname == null || nickname.equals(""))
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]parameter error"), HttpStatus.BAD_REQUEST);
        List<Room> myroomlist = roomService.selectMyRoom(nickname);
        if (myroomlist.isEmpty()) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]roomMyview", myroomlist), HttpStatus.OK);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]roomMyview", myroomlist), HttpStatus.OK);
    }

    //방 만들기
    @PostMapping("/community")
    public ResponseEntity createRoom(@RequestBody Room room) {

        if (room.getNickname() == null || room.getTitle() == null || room.getRm_type() == null || room.getMaxnum() == 0 || room.getTags() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]parameter error"), HttpStatus.BAD_REQUEST);
        }

        int result = roomService.insertRoom(room);

        if (result > -1) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]createRoom", room), HttpStatus.OK);
        } else {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]createRoom"), HttpStatus.BAD_REQUEST);
        }
    }

    // 방 입장
    @PostMapping("/room")
    public ResponseEntity joinRoom(@RequestBody Member member) throws NoSuchAlgorithmException {
        String result = roomService.joinRoom(member);
        if (result.equals("existUser")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]joinRoom", "existsUser"), HttpStatus.OK);
        } else if (result.equals("countOver")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.COUNT_OVER, "[FAIL]joinRoom", "countOver"), HttpStatus.BAD_REQUEST);
        } else if (result.equals("countOK")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.COUNT_OK, "[SUCCESS]joinRoom", "countOK"), HttpStatus.OK);
        } else{
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[ERROR]joinRoom"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/room")
    public ResponseEntity insertRoomMember(@RequestBody Member member) throws NoSuchAlgorithmException{
        try {
            boolean result = roomService.insertRoomMember(member);
            if (!result){
                return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]insertRoomMember"), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]insertRoomMember", member), HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/searchroom")
    public ResponseEntity searchThing(@RequestParam String keyword) {
        List<Room> roomListAll = roomService.searchRoom(keyword);
        if (roomListAll.isEmpty()) {
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_EXIST, "[FAIL]RoomList", "일치하는 방이 없습니다.다른 검색어를 입력해주세요"), HttpStatus.OK);
        } else {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]searchRoomList", roomListAll), HttpStatus.OK);
        }
    }
}
