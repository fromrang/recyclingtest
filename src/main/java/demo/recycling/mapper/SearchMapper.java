package demo.recycling.mapper;

import demo.recycling.dto.SearchDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {

    @Select("SELECT * FROM searchDetail where d_no=#{d_no)}")
    public SearchDetail selectDetailSearch(int d_no) throws Exception;

    @Select("SELECT s_no FROM searchResult where r_name like CONCAT('%', #{keyword}, '%')")
    public List<String> selectResultsearch(String keyword) throws Exception;

}
