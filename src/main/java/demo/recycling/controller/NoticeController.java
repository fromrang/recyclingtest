package demo.recycling.controller;

import demo.recycling.dto.DefaultRes;
import demo.recycling.dto.Notice;
import demo.recycling.dto.StatusCode;
import demo.recycling.dto.Suggestion;
import demo.recycling.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class NoticeController {
    @Autowired
    SuggestionService suggestionService;


    //건의하기 글보기
    @GetMapping("/suggestion")
    public ResponseEntity suggestionView(){
        List<Suggestion> suggestions = suggestionService.allSuggestion();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]suggestionView", suggestions), HttpStatus.OK);

    }
    //건의하기 글쓰기
    @PostMapping("/suggestion")
    public ResponseEntity suggestioninsert(@RequestBody Suggestion suggestion){
        if(suggestion.getEmail() == null){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]suggestioninsert"), HttpStatus.BAD_REQUEST);
        }
        boolean result = suggestionService.insertSuggestion(suggestion);
        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.DB_ERROR, "[FAIL]suggestioninsert"), HttpStatus.OK);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]suggestioninsert", suggestion), HttpStatus.OK);
    }
    //공지사항 보기
    @GetMapping("/notice")
    public ResponseEntity noticeAllView(){
        List<Notice> noticeList = suggestionService.serviceNoticeAll();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]noticeAllView", noticeList), HttpStatus.OK);


    }
    //공지사항 추가하기
    @PostMapping("/notice")
    public ResponseEntity noticeInsert(@RequestBody Notice notice){
        Boolean result = suggestionService.serviceNoticeInsert(notice);
        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]noticeInsert"), HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]noticeInsert", notice), HttpStatus.OK);
        }
    }
    //공지사항 하나만 보기
    @GetMapping("/notice/{nseq}")
    public ResponseEntity noticeOneView(@PathVariable int nseq){
        Notice notice = suggestionService.serviceNoticeSelectOne(nseq);
        if(notice == null){
            return new ResponseEntity(DefaultRes.res(StatusCode.NO_CONTENT, "[FAIL]noticeOneView"), HttpStatus.OK);
        }else{
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]noticeOneView", notice), HttpStatus.OK);
        }
    }

}