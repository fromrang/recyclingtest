package demo.recycling.repository;

import demo.recycling.dto.Image;
import demo.recycling.dto.Post;
import demo.recycling.mapper.PostMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDao {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<Post> selectPost(int rum) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(PostMapper.class).selectPost(rum);
        }finally {
            sqlSession.close();
        }
    }

    public List<Image> selectImage(int pseq) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(PostMapper.class).selectImage(pseq);
        }finally {
            sqlSession.close();
        }
    }

    public  int selectType(int rum) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(PostMapper.class).selectType(rum);
            return result;
        }finally {
            sqlSession.close();
        }
    }

    public  int selectPwd(int rum) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(PostMapper.class).selectPwd(rum);
            return result;
        }finally {
            sqlSession.close();
        }
    }

    public int deletePost(int pseq) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(PostMapper.class).deletePost(pseq);
            return result;
        }finally {
            sqlSession.close();
        }
    }

    public int deleteImage(int pseq) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(PostMapper.class).deleteImage(pseq);
            return result;
        }finally {
            sqlSession.close();
        }
    }

    // Post ?????? ??????
    public int insertpost(Post post)throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(PostMapper.class).insertpost(post);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }

    // Post ????????? ?????? Image??? ??????
    public int insertimage(String image_name, int pseq) throws  Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(PostMapper.class).insertimage(image_name,pseq);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }

    }

    // ?????? ??? Post pseq ????????????.(??? ??????????????? ???????????? ??????)
    public int selectpseq() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int pseq = sqlSession.getMapper(PostMapper.class).selectpseq();
            sqlSession.commit();
            return pseq;
        }finally {
            sqlSession.close();
        }
    }

    // post??? ?????? ????????????.
    public Post selectpostone(int pseq)throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            Post post = sqlSession.getMapper(PostMapper.class).selectpostone(pseq);
            sqlSession.commit();
            return post;
        }finally {
            sqlSession.close();
        }
    }

    // Image??? ?????? ?????? ????????????.
    public int deleteimage(int pseq, String imageName)throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(PostMapper.class).deleteimage(pseq,imageName);
            sqlSession.commit();
            return result;
        }
        finally {
            sqlSession.close();
        }
    }

    // Post ?????? ???????????? ??????(Content???)
    public int updatepost(String content, int pseq) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(PostMapper.class).updatepost(content,pseq);
            sqlSession.commit();
            return result;
        }
        finally {
            sqlSession.close();
        }

    }

}
