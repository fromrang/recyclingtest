package demo.recycling.service;

import demo.recycling.dto.Image;
import demo.recycling.dto.Post;
import demo.recycling.repository.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    Post post;
    @Autowired
    PostDao postDao;

    public List<Post> selectPost(int rum){
        try{
            List<Post> postAll = postDao.selectPost(rum);
            for(Post postList:postAll){
                postList.setImageList(postDao.selectImage(postList.getPseq()));
            }
            return postAll;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
