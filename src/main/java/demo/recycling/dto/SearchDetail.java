package demo.recycling.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class SearchDetail {
    int no; // 값이 없을 경우 999, 값이 있을 경우 0
    int separnum; // 분류넘버 (세부 분류기준 분류 넘버 포함)
    int d_no; // 세부 분류기준 넘버
    String type; // 분리수거 종류 이름
    String d_name; //검색시 이름
    String d_image; // 검색시 이미지
}
