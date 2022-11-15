package demo.recycling.repository;

import demo.recycling.dto.Users;
import demo.recycling.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    //사용자 추가
    public int insertUserDao(Users users) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(UserMapper.class).insertUserOne(users);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }
    // 사용자 존재 여부
    public Users selectUserDao(String email) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            Users users = sqlSession.getMapper(UserMapper.class).selectUserOne(email);
            return users;
        }finally {
            sqlSession.close();
        }
    }

    // 닉네임 체크
    public Users selectNicknameDao(String nickname) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Users users = sqlSession.getMapper(UserMapper.class).selectNickname(nickname);
            return users;
        }finally {
            sqlSession.close();
        }
    }
}
