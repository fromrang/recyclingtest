package demo.recycling.mapper;

import demo.recycling.dto.Member;
import demo.recycling.dto.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RoomMapper {

    @Insert("INSERT INTO room(userEmail,title,password,rm_type,count,maxnum) VALUES(#{userEmail},#{title},#{password},#{rm_type},#{count},#{maxnum})")
    public int insertRoom(Room room) throws Exception;
    // 가장 최근에 입장한 방 번호 조회
    @Select("SELECT rum FROM room WHERE userEmail=#{userEmail} GROUP BY reg_date ORDER BY reg_date DESC Limit 1")
    public int selectRum(String userEmail) throws Exception;

    @Insert("INSERT INTO tag(rum,tag_name) VALUES(#{rum},#{tag_name})")
    public int insertTag(int rum, String tag_name) throws Exception;

    @Insert("INSERT INTO member(rum,nickname) VALUES(#{rum},#{nickname})")
    public int insertMember(int rum, String nickname) throws Exception;
    // 소속된 방 조회
    @Select("SELECT rum,title, rm_type, count, maxnum,reg_date,(SELECT tag_name from tag where tag.rum= room.rum) as tag FROM room WHERE rum in (SELECT rum FROM member WHERE nickname=#{nickname})")
    public List<Room> selectMyRoom(String nickname) throws Exception;
    // 태그값
    @Select("select tag_name from tag where rum in (SELECT rum FROM member WHERE nickname=#{nickname})")
    public List<String> selectTag(String nickname) throws Exception;
    // 방 가입
    @Update("update room set count = count + 1 where rum=#{rum}")
    public int joinRoom(int rum) throws Exception;
    // 현재 인원 , 정원 수 비교
    @Select("Select count,maxnum from room where rum =#{rum}")
    public List<Room> selectRoom(int rum) throws Exception;
    // 닉네임 체크
    @Select("Select * from member where nickname=#{nickname} and rum= #{rum}")
    public Member nicknameCheck(String nickname, int rum) throws Exception;


}
