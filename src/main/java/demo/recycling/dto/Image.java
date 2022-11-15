package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Getter
@Setter

public class Image {
    private int iseq;
    private int pseq;
    private String image_name;
    private Date reg_date;
}
