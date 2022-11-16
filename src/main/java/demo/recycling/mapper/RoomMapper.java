package demo.recycling.mapper;

import demo.recycling.dto.Member;
import demo.recycling.dto.Room;
import demo.recycling.dto.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

}
