package demo.recycling.mapper;

import demo.recycling.dto.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from users where email = #{email}")
    public Users selectUserOne(String email) throws Exception;

    @Insert("insert into users(userEmail, nickname) values(#{email}, #{nickname})")
    public int insertUserOne(Users users) throws Exception;

    @Select("select * from users where nickname= #{nickname}")
    public Users selectNickname(String nickcname) throws Exception;





}
