package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.Date;

@Service
@Getter
@Setter
public class Mail {
    private int mseq;
    private String useremail;
    private String title;
    private String content;
    private Date reg_date;
    private MultipartFile files;


    private int sseq;
}
