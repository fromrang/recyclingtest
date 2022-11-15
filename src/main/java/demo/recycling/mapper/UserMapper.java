package demo.recycling.mapper;

import demo.recycling.dto.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from users where userEmail = #{email}")
    public Users selectUserOne(String email) throws Exception;

    @Insert("insert into users(userEmail, nickname) values(#{userEmail}, #{nickname})")
    public int insertUserOne(Users users) throws Exception;

    @Select("select * from users where nickname= #{nickname}")
    public Users selectNickname(String nickcname) throws Exception;





}
