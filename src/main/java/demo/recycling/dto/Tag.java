package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Getter
@Setter

public class Tag {
    private int tseq;
    private int rum;
    private String tag_name;
    private Date reg_date;
}
