package demo.recycling.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;

@Data
@NoArgsConstructor
public class KakaoLoginDto {
    private String iss; // ID 토큰을 발급한 인증 기관 정보, https://kauth.kakao.com로 고정
    private String aud; // ID 토큰이 발급된 앱의 앱 키, 인가 코드 받기 요청 시 client_id에 전달된 앱 키, Kakao SDK를 통한 카카오 로그인의 경우, 해당 SDK 초기화 시 사용된 앱 키
    private String sub; // ID 토큰에 해당하는 사용자의 회원번호
    private Integer iat; // ID 토큰 발급 또는 갱신 시각, UNIX 타임스탬프(Timestamp)
    private Integer exp; // ID 토큰 만료 시간, UNIX 타임스탬프(Timestamp)
    private Integer auth_time; // 사용자가 카카오 로그인을 통해 인증을 완료한 시각, UNIX 타임스탬프
    private String nonce;
    private String nickname;
    private String picture;
    private String email;
    private String id;
    private String connected_at;
    private boolean has_email;
    private boolean email_needs_agreement;
    private boolean is_email_valid;
    private boolean is_email_verified;
    private HashMap kakao_account;
}
