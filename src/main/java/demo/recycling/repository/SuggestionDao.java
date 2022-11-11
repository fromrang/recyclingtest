package demo.recycling.repository;

import demo.recycling.dto.Notice;
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

    public List<Notice> selectNoticeAllDao() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(SuggestionMapper.class).selectNoticeAll();
        }finally {
            sqlSession.close();
        }
    }

    public int insertNoticeDao(Notice notice) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(SuggestionMapper.class).insertNoticeOne(notice);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }
    public Notice selectNoticeOneDao(int nseq) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(SuggestionMapper.class).selectNoticeOne(nseq);
        }finally {
            sqlSession.close();
        }
    }
    //문의하기 삭제
    public int updateSuggestionStatusDao(int sseq) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(SuggestionMapper.class).updateSuggestionStatus(sseq);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }
    //공지사항 수정
    public int updateNoticeOneDao(Notice notice) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(SuggestionMapper.class).updateNoticeOne(notice);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }
    //공지사항 삭제
    public int updateNoticeStatusDao(int nseq) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(SuggestionMapper.class).updateNoticeStatus(nseq);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }


}
