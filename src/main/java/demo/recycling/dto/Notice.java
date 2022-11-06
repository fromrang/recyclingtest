package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Getter
@Setter
public class Notice {
    private int nseq;
    private String title;
    private String content;
    private String writer;
    private Date reg_date;
}
