package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Getter
@Setter

public class Member {
    private int mseq;
    private int rum;
    private String nickname;
    private Date reg_date;
}
