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



}
