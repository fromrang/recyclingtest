package demo.recycling.controller;

import demo.recycling.dto.*;
import demo.recycling.repository.PostDao;
import demo.recycling.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostDao postDao;
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

    @GetMapping("/entrance/{rum}")
    public ResponseEntity roomEnter(@PathVariable int rum){
        int roomType = postService.roomEnter(rum);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]roomEnter",roomType), HttpStatus.OK);
    }

    @PostMapping("/pwdcheck")
    public ResponseEntity checkPwd(@RequestBody Room room){
        boolean result = postService.checkPwd(room.getRum(),room.getPassword());
        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_EXIST, "[FAIL]checkPwd"), HttpStatus.OK);
        }else {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]checkPwd", room.getRum()), HttpStatus.OK);
        }
    }

    @DeleteMapping("/postdelete/{pseq}")
    public ResponseEntity deletePost(@PathVariable int pseq){
        boolean result = postService.deletePost(pseq);
        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]deletePostList"), HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]deletePostList",pseq), HttpStatus.OK);
        }
    }

    @PostMapping("/postinsert")
    public ResponseEntity postinsert(@RequestParam List<MultipartFile> files,
                                     @RequestParam int rum,@RequestParam String nickname,@RequestParam String content) throws Exception{
        Post post = new Post();
        post.setRum(rum);
        post.setNickname(nickname);
        post.setContent(content);

        boolean result = postService.postinsert(files,post);

        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[Fail]suggestionPost"), HttpStatus.OK);
        }
        else{
            int pseq = postDao.selectpseq();
            Post data = postDao.selectpostone(pseq);
            data.setImageList(postDao.selectImage(pseq));
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]suggestionPost",data), HttpStatus.OK);
        }

    }

    @PostMapping("postupdate")
    public ResponseEntity postupdate(@RequestParam List<MultipartFile> files,
                                     @RequestParam int pseq, @RequestParam int rum,@RequestParam String nickname,@RequestParam String content)throws Exception{

        Post post = new Post();
        post.setPseq(pseq);
        post.setRum(rum);
        post.setNickname(nickname);
        post.setContent(content);

        boolean result = postService.postupdate(files,post);

        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[Fail]suggestionPostUpdate"), HttpStatus.OK);
        }
        else{
            int pseqNum = postDao.selectpseq();
            Post data = postDao.selectpostone(pseqNum);
            data.setImageList(postDao.selectImage(pseqNum));
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]suggestionPost",data), HttpStatus.OK);
        }

    }



}
