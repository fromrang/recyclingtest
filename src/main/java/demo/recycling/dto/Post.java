package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Getter
@Setter
public class Post {
    private int pseq;
    private int num;
    private String nickname;
    private String content;
    private String image_name;
    private Date reg_date;
}
