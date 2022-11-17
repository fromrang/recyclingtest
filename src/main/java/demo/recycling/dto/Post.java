package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@Getter
@Setter
public class Post {
    private int pseq;
    private int rum;
    private String nickname;
    private String content;
    private String profile_image;
    private List<Image> imageList; // 이미지 여러개 받기
    private Date reg_date;
}
