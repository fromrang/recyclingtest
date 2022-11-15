package demo.recycling.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
public class NaverLoginDto {
    private String resultcode;
    private String message;
    HashMap response;
}
