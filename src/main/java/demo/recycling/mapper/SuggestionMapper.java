package demo.recycling.mapper;

import demo.recycling.dto.Notice;
import demo.recycling.dto.Suggestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SuggestionMapper {

    @Select("select * from suggestion where status='0'")
    public List<Suggestion> selectSuggestion() throws Exception;

    @Insert("insert into suggestion(email, title, content) values(#{email}, #{title}, #{content})")
    public int insertSuggestion(Suggestion suggestion) throws Exception;
    
    //문의하기 삭제
    @Update("update suggestion set status='1' where sseq=#{sseq}")
    public int updateSuggestionStatus(int sseq) throws Exception;

    @Select("select * from notice where status='0'")
    public List<Notice> selectNoticeAll() throws Exception;

    @Insert("insert into notice(title, content, writer) values(#{title}, #{content}, '관리자')")
    public int insertNoticeOne(Notice notice) throws Exception;

    @Select("select * from notice where nseq=#{nseq}")
    public Notice selectNoticeOne(int nseq) throws Exception;

    //공지사항 삭제
    @Update("update notice set status='1' where nseq=#{nseq}")
    public int updateNoticeStatus(int nseq) throws Exception;

    //공지사항 수정
    @Update("update notice set title=#{title}, content=#{content}, reg_date=now() where nseq=#{nseq}")
    public int updateNoticeOne(Notice notice) throws Exception;

}
