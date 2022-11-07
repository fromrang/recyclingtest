package demo.recycling.mapper;

import demo.recycling.dto.Mail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MailMapper {

    @Insert("insert into mail(useremail, title, content) values(#{useremail}, #{title}, #{content})")
    public int insertMail(Mail mail) throws Exception;      // 메일 전송 내용
}
