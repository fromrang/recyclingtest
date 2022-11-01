package demo.recycling.mapper;

import demo.recycling.dto.Suggestion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SuggestionMapper {

    @Select("select * from suggestion")
    public List<Suggestion> selectSuggestion() throws Exception;

    @Insert("insert into suggestion(email, title, content) values(#{email}, #{title}, #{content})")
    public int insertSuggestion(Suggestion suggestion) throws Exception;
}
