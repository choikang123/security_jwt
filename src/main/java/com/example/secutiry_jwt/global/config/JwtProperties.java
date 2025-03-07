package com.example.secutiry_jwt.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt") //yml 파일에서 jwt 접두사를 사진 설정값을 필드에 자동 주입하는 기능
public class JwtProperties {
    private String issuer;
    private String secretKey;
}
