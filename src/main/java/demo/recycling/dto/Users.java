package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Getter
@Setter

public class Users {
    private int useq;
    private String userEmail;
    private String nickname;
    private Date reg_date;
}
