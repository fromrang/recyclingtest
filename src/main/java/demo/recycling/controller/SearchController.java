package demo.recycling.controller;

import demo.recycling.dto.DefaultRes;
import demo.recycling.dto.SearchDetail;
import demo.recycling.dto.StatusCode;
import demo.recycling.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    demo.recycling.service.SearchService SearchService;


    //검색 결과 리스트
    @GetMapping("/search")
    public ResponseEntity getseacrch(@RequestParam String data){
        List<SearchDetail> result = new ArrayList<>();

        result = SearchService.getSearchdata(data);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]selectSearchList", result), HttpStatus.OK);
    }


    //검색 결과를 눌렀을떼 정보 리스트
    @GetMapping("/searchresult")
    public ResponseEntity getresult(@RequestParam  int separnum ,@RequestParam  String type){
        Map data = new HashMap();

        data.put("type",type);
        data.put("code",separnum);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]selectSearchResult",data), HttpStatus.OK);
    }

}
