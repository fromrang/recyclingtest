package demo.recycling.repository;

import demo.recycling.dto.Suggestion;
import demo.recycling.mapper.SuggestionMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SuggestionDao {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<Suggestion> selectSuggestion() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(SuggestionMapper.class).selectSuggestion();
        }finally {
            sqlSession.close();
        }
    }
    public int insertSuggestion(Suggestion suggestion) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(SuggestionMapper.class).insertSuggestion(suggestion);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }

}
