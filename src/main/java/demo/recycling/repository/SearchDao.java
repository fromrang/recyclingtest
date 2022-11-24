package demo.recycling.repository;

import demo.recycling.dto.SearchDetail;
import demo.recycling.mapper.SearchMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchDao {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public SearchDetail selectDetailSearch(int data) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(SearchMapper.class).selectDetailSearch(data);
        }finally {
            sqlSession.close();
        }
    }

    public List<String>  selectResultsearch(String keyword) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(SearchMapper.class).selectResultsearch(keyword);
        }finally {
            sqlSession.close();
        }
    }
}
