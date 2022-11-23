package demo.recycling.controller;

import demo.recycling.dto.*;
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


    //문의하기 글보기
    @GetMapping("/suggestion")
    public ResponseEntity suggestionView(){
        List<Suggestion> suggestions = suggestionService.allSuggestion();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]suggestionView", suggestions), HttpStatus.OK);

    }

    //문의하기 글 하나 보기
    @GetMapping("suggestion/{sseq}")
    public ResponseEntity seggestionOneView(@PathVariable int sseq){
        Suggestion suggestion = suggestionService.suggestionOneService(sseq);
        if(suggestion == null){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]seggestionOneView"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]seggestionOneView",suggestion),HttpStatus.OK);
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
    //건의하기 삭제하기
    @DeleteMapping("/suggestion/{sseq}")
    public ResponseEntity suggestiondelete(@PathVariable int sseq){
        boolean result = suggestionService.serviceSuggestionDelete(sseq);
        if(result){
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]suggestiondelete", sseq), HttpStatus.OK);
        }else{
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]suggestiondelete"), HttpStatus.BAD_REQUEST);
        }
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
    //공지사항 삭제
    @DeleteMapping("/notice/{nseq}")
    public ResponseEntity noticeDelete(@PathVariable int nseq){
        boolean result = suggestionService.serviceNoticeDelete(nseq);
        if(result){
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]noticeDelete", nseq), HttpStatus.OK);
        }else{
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]noticeDelete"), HttpStatus.BAD_REQUEST);
        }
    }

    //공지사항 수정
    @PutMapping("/notice")
    public ResponseEntity noticeRevise(@RequestBody Notice notice){
        if(notice.getNseq()== 0 || notice.getTitle() == null || notice.getContent() == null) return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[SUCCESS]noticeRevise"), HttpStatus.BAD_REQUEST);
        boolean result = suggestionService.serviceNoticeUpdate(notice);
        if(result){
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]noticeRevise", notice), HttpStatus.OK);
        }else{
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]noticeRevise"), HttpStatus.BAD_REQUEST);
        }
    }

}