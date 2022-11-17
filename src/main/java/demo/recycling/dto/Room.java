package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@Getter
@Setter
public class Room implements Comparable<Room>{
    private int rum;
    private String userEmail;
    private String title;
    private int password;
    private String rm_type;
    private int count;
    private int maxnum;
    private Date reg_date;
    private String tag;
    private List<String> tags;
    private String nickname;

    public int compareTo(Room Room) {
        return this.reg_date.compareTo(Room.reg_date);
    }
}
