package demo.recycling.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoLoginResponse {
    private String token_type; // 토큰 타입, bearer로 고정
    private String access_token;   // 사용자 액세스 토큰 값
    private String expires_in;    // 액세스 토큰과 ID 토큰의 만료 시간(초)
    private String refresh_token; // 사용자 리프레시 토큰 값
    private String refresh_token_expires_in;   // 리프레시 토큰 만료 시간(초)
    private String scope;

}
