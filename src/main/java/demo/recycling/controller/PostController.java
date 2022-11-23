package demo.recycling.controller;

import demo.recycling.dto.*;
import demo.recycling.repository.PostDao;
import demo.recycling.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
public class PostController {
    @Autowired
    PostDao postDao;
    @Autowired
    PostService postService;

    //게시글 보기
    @GetMapping("/post/{rum}")
    public ResponseEntity selectPost(@PathVariable int rum){
//        List<Post> postList = postService.selectPost(rum);
        HashMap<String,Object> postList= postService.selectPost(rum);
        //System.out.println(postList);
        if(postList.isEmpty()){
            return new ResponseEntity(DefaultRes.res(StatusCode.NO_CONTENT, "[SUCCESS]selectPostList", postList), HttpStatus.OK);
        }else{
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]selectPostList",postList), HttpStatus.OK);
        }
    }
    //비밀방 체크
    @GetMapping("/entrance/{rum}")
    public ResponseEntity roomEnter(@PathVariable int rum){
        int roomType = postService.roomEnter(rum);
        HashMap<String, Integer> data = new HashMap<>();
        data.put("rm_type", roomType);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]roomEnter",data), HttpStatus.OK);
    }

    @PostMapping("/pwdcheck")
    public ResponseEntity checkPwd(@RequestBody Room room){
        boolean result = postService.checkPwd(room.getRum(),room.getPassword());
        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_EXIST, "[FAIL]checkPwd", "wrong password"), HttpStatus.OK);
        }else {
            HashMap<String, Integer> data = new HashMap<>();
            data.put("rum", room.getRum());
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]checkPwd", data), HttpStatus.OK);
        }
    }

    @DeleteMapping("/post/{pseq}")
    public ResponseEntity deletePost(@PathVariable int pseq){
        boolean result = postService.deletePost(pseq);
        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]deletePostList"), HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]deletePostList",pseq), HttpStatus.OK);
        }
    }

    @PostMapping("/post")
    public ResponseEntity postinsert(@RequestParam(value = "files",required = false) List<MultipartFile> files ,
                                     @RequestParam int rum,@RequestParam String nickname,@RequestParam String content) throws Exception{
        boolean result;
        Post post = new Post();
        post.setRum(rum);
        post.setNickname(nickname);
        post.setContent(content);

        if(StringUtils.isEmpty(files)){
            result = postService.insertPost(post);
        }
        else{
            result = postService.insertPost(files,post);
        }

        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[Fail]insertPost"), HttpStatus.OK);
        }
        else{
            int pseq = postDao.selectpseq();
            Post data = postDao.selectpostone(pseq);
            data.setImageList(postDao.selectImage(pseq));
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]insertPost",data), HttpStatus.OK);
        }

    }


    @PostMapping("/modification")
    public ResponseEntity postupdate(@RequestParam(value = "files",required = false) List<MultipartFile> files,
                                     @RequestParam int pseq, @RequestParam int rum,@RequestParam String nickname,@RequestParam String content,@RequestParam(value = "images",required = false) List<String> images)throws Exception{

        boolean result;
        Post post = new Post();
        post.setPseq(pseq);
        post.setRum(rum);
        post.setNickname(nickname);
        post.setContent(content);

        // 추가 파일이 없을 경우
        if(StringUtils.isEmpty(files)){
            if(StringUtils.isEmpty(images)){ // 추가 파일이 없으면서 삭제 이미지가 없을 경우
                result = postService.postupdate(post);
            }// 추가 파일이 없으면서 삭제 이미지가 있을 경우
            else{
                result = postService.postupdate(post,images);
            }
        }
        else{// 추가 파일이 있고 삭제 이미지가 없을 경우
            if(StringUtils.isEmpty(images)){
                result = postService.postupdate(files,post);
            }// 추가 파일이 있으면서 삭제 이미지도 있을 경우
            else{
                result = postService.postupdate(files,post,images);
            }
        }


        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[Fail]updatePost"), HttpStatus.OK);
        }
        else{
            Post data = postDao.selectpostone(pseq);
            data.setImageList(postDao.selectImage(pseq));
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]updatePost",data), HttpStatus.OK);
        }

    }



//    @PostMapping("postupdate")
//    public ResponseEntity postupdate(@RequestParam List<MultipartFile> files,
//                                     @RequestParam int pseq, @RequestParam int rum,@RequestParam String nickname,@RequestParam String content)throws Exception{
//
//        Post post = new Post();
//        post.setPseq(pseq);
//        post.setRum(rum);
//        post.setNickname(nickname);
//        post.setContent(content);
//
//        boolean result = postService.postupdate(files,post);
//
//        if(!result){
//            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[Fail]suggestionPostUpdate"), HttpStatus.OK);
//        }
//        else{
//            int pseqNum = postDao.selectpseq();
//            Post data = postDao.selectpostone(pseqNum);
//            data.setImageList(postDao.selectImage(pseqNum));
//            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]suggestionPost",data), HttpStatus.OK);
//        }
//
//    }




}
