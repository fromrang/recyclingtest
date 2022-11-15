package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@Getter
@Setter
public class Room {
    private int rum;
    private String userEmail;
    private String title;
    private int password;
    private String rm_type;
    private int count;
    private int maxnum;
    private Date reg_date;
}
