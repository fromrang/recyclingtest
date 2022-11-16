package demo.recycling.controller;

import demo.recycling.dto.DefaultRes;
import demo.recycling.dto.Image;
import demo.recycling.dto.Post;
import demo.recycling.dto.StatusCode;
import demo.recycling.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/post/{rum}")
    public ResponseEntity selectPost(@PathVariable int rum){
        List<Post> postList = postService.selectPost(rum);
        //System.out.println(postList);
        if(postList.isEmpty()){
            return new ResponseEntity(DefaultRes.res(StatusCode.NO_CONTENT, "[FAIL]selectPostList"), HttpStatus.OK);
        }else{
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]selectPostList",postList), HttpStatus.OK);
        }

    }
}
