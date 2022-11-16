package demo.recycling.mapper;

import demo.recycling.dto.Member;
import demo.recycling.dto.Room;
import demo.recycling.dto.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface RoomMapper {

    @Insert("INSERT INTO room(userEmail,title,password,rm_type,count,maxnum) VALUES(#{userEmail},#{title},#{password},#{rm_type},#{count},#{maxnum})")
    public int insertRoom(Room room) throws Exception;

    @Select("SELECT rum FROM room WHERE userEmail=#{userEmail} GROUP BY reg_date ORDER BY reg_date DESC Limit 1")
    public int selectRum(String userEmail) throws Exception;

    @Insert("INSERT INTO tag(rum,tag_name) VALUES(#{rum},#{tag_name})")
    public int insertTag(int rum, String tag_name) throws Exception;

    @Insert("INSERT INTO member(rum,nickname) VALUES(#{rum},#{nickname})")
    public int insertMember(int rum, String nickname) throws Exception;

    @Select("SELECT *,(SELECT tag_name from tag where tag.rum= room.rum) as tag FROM room WHERE rum in (SELECT rum FROM member WHERE nickname=#{nickname})")
    public List<Room> selectMyRoom(String nickname) throws Exception; // 소속된 방 조회

    @Select("select tag_name from tag where rum in (SELECT rum FROM member WHERE nickname=#{nickname})")
    public List<String> selectTag(String nickname) throws Exception; // 태그값

}
