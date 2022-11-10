package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class RecylingMap {
    String name;
    String adderess;
    String number;
    Double lat; //위도(y좌표) 36...
    Double lon; //경도(x좌표) 127...
    int code; //0 재활용센터,1 배터리,2 쓰레기
}
