package demo.recycling.repository;

import demo.recycling.dto.Member;
import demo.recycling.dto.Room;
import demo.recycling.dto.Tag;
import demo.recycling.dto.Users;
import demo.recycling.mapper.RoomMapper;
import demo.recycling.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.beans.PropertyEditorSupport;
import java.util.List;

@Repository
public class RoomDao {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public int insertRoom(Room room) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(RoomMapper.class).insertRoom(room);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }

    // UserMapper에서 nickname을 기준으로 user 모든 정보를 담아온것을 users에 담음
    public String selectUserInfo(String nickname) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Users users = sqlSession.getMapper(UserMapper.class).selectNickname(nickname);
            if(users == null){
                return null;
            }
            //System.out.println(users.getUserEmail());
            return users.getUserEmail(); // User의 Email정보 추출

        }finally {
            sqlSession.close();
        }
    }

    public int insertTag(int rum, String tags) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(RoomMapper.class).insertTag(rum, tags);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }

    // Room에서 rum추출
    public int selectRum(String userEmail) throws Exception{
        //System.out.println("!!!!"+userEmail);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int rum = sqlSession.getMapper(RoomMapper.class).selectRum(userEmail);
            return rum;
        }finally {
            sqlSession.close();
        }

    }

    public int insertMember(int rum, String nickname) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(RoomMapper.class).insertMember(rum,nickname);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }

    public List<Room> selectMyRoom(String nickname) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(RoomMapper.class).selectMyRoom(nickname);
        }finally {
            sqlSession.close();
        }
    }

    public List<String> selectTag(String nickname) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(RoomMapper.class).selectTag(nickname);
        }finally {
            sqlSession.close();
        }
    }

    public int joinRoom(int rum) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(RoomMapper.class).joinRoom(rum);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }

    public List<Room> selectRoom(int rum) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(RoomMapper.class).selectRoom(rum);
        }finally {
            sqlSession.close();
        }
    }

    public Member nicknameCheck(String nickname , int rum) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Member member = sqlSession.getMapper(RoomMapper.class).nicknameCheck(nickname, rum);
            return member;
        } finally {
            sqlSession.close();
        }
    }

    public List<Room> selectTag() throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(RoomMapper.class).selectTagList();
        }finally {
            sqlSession.close();
        }
    }

    public List<Room> selectTitle(String keyword) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(RoomMapper.class).selectTitle(keyword);
        }finally {
            sqlSession.close();
        }

    }
    public String postRumTitle (int rum) throws Exception{
        SqlSession sqlSession=sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(RoomMapper.class).postRumTitle(rum);
        }finally {

        }
    }
}
