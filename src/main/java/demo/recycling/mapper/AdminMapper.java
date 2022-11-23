package demo.recycling.mapper;

import demo.recycling.dto.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Mapper
public interface AdminMapper {


    @Insert("INSERT INTO admin(email,pw,name,position,tel,authority) VALUES(#{email},#{pw},#{name},#{position},#{tel},#{authority})")
    public int insertAdmin(Admin admin) throws Exception;      // 관리자 계정 가입


    @Select("Select * from admin where email=#{email}")
    public Admin emailcheck(String email) throws Exception;    // 이메일 중복체크


    @Select("Select email, name, position, authority, status from admin where email=#{email} and pw = #{pw}")
    public Admin loginEmailPw(String email, String pw) throws Exception;   // 로그인

    @Update("Update admin set pw=#{pw},name=#{name},position=#{position},tel=#{tel},authority=#{authority} where aseq=#{aseq}")
    public int updateAdmin(Admin admin) throws Exception;   // 관리자 수정

    @Update("Update admin set status='1' where aseq = #{aseq}")
    public int deleteAdmin(int aseq) throws Exception;   // 관리자 삭제


    @Select("Select aseq,email,name,position,tel,authority,reg_date,status from admin where status = 0") //보안 문제 비밀번호 제외
    public List<Admin> selectAdminList() throws Exception; // 관리자 목록 조회


}
