package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Getter
@Setter
public class Suggestion {
    private int aseq;
    private String email;
    private String title;
    private String content;
    private Date reg_date;
}



