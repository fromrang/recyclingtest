package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
@Getter
@Setter
public class Admin {
    private int aseq;
    private String email;
    private String pw;
    private String name;
    private String position;
    private String tel;
    private String authority;
    private String status;
    private Date reg_date;
}



