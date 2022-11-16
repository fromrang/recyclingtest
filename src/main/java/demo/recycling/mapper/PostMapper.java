package demo.recycling.mapper;

import demo.recycling.dto.Image;
import demo.recycling.dto.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("SELECT * FROM post WHERE rum=#{rum} order by reg_date desc")
    public List<Post> selectPost(int rum) throws Exception;

    @Select("SELECT * FROM image WHERE pseq=#{pseq}")
    public List<Image> selectImage(int pseq) throws Exception;

}
