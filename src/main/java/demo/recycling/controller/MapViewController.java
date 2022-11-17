package demo.recycling.controller;

import demo.recycling.dto.DefaultRes;
import demo.recycling.dto.RecylingMap;
import demo.recycling.dto.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MapViewController {

    @Autowired
    demo.recycling.service.MapService MapService;

    //위치 주소 받기
    @PostMapping(value = "/coordinate", produces="application/json;charset=UTF-8")
    public ResponseEntity CoordinateGet(@RequestBody RecylingMap map){
        List<RecylingMap> Coordinate =MapService.TotalAroundRecyclingMap(map.getLat(),map.getLon(),map.getCode());
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]coordinateView", Coordinate), HttpStatus.OK);

    }

    //위치 주소 보내주기
//    @GetMapping("/coordinate")
//    public ResponseEntity CoordinateView(@RequestBody RecylingMap map){
//        List<RecylingMap> Coordinate =MapService.TotalAroundRecyclingMap(map.getLat(),map.getLon(),map.getCode());
//        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]coordinateView",Coordinate), HttpStatus.OK);
//
//    }








}
