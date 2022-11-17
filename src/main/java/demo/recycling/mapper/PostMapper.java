package demo.recycling.mapper;

import demo.recycling.dto.Image;
import demo.recycling.dto.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    // 게시물을 최신순으로 정렬
    @Select("SELECT * FROM post WHERE rum=#{rum} order by reg_date desc")
    public List<Post> selectPost(int rum) throws Exception;

    @Select("SELECT * FROM image WHERE pseq=#{pseq}")
    public List<Image> selectImage(int pseq) throws Exception;
    // 방 타입 구분(공개방, 비밀방)
    @Select("SELECT rm_type FROM room WHERE rum=#{rum}")
    public int selectType(int rum) throws Exception;
    // 방 비밀번호 체크
    @Select("SELECT password FROM room WHERE rum=#{rum}")
    public int selectPwd(int rum) throws Exception;
    // 방 게시글 삭제
    @Delete("DELETE FROM post WHERE pseq=#{pseq}")
    public int deletePost(int pseq) throws Exception;

    @Delete("DELETE FROM image WHERE pseq=#{pseq}")
    public  int deleteImage(int pseq) throws Exception;

    // Post 정보 저장
    @Insert("INSERT INTO post(rum,nickname,content,profile_image) VALUE(#{rum},#{nickname},#{content},'logo.jpg')")
    public int insertpost(Post post) throws Exception;

    // Post 정보에 있는 Image들 저장
    @Insert("insert into image(pseq,image_name) value(#{pseq},#{image_name})")
    public int insertimage(String image_name, int pseq) throws  Exception;

    // 저장 된 Post pseq 받아오기.(잘 저장됐는지 확인하기 위해)
    @Select("select pseq from post order by pseq desc limit 1")
    public int selectpseq() throws Exception;

    // post의 정보 가져오기.
    @Select("SELECT * FROM post where pseq = #{pseq}")
    public Post selectpostone(int pseq)throws Exception;

    // Image에 있는 사진 삭제하기.
    @Delete("DELETE FROM image where pseq = #{pseq} and image_name = #{imageName}")
    public int deleteimage(int pseq, String imageName)throws Exception;

    // Post 정보 업데이트 하기(Content만)
    @Update("update post set content = #{content}, reg_date = NOW() where pseq = #{pseq}")
    public int updatepost(String content, int pseq) throws Exception;

}
