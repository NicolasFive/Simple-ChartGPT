package cn.nf.chartgptserver.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "authorization")
public class AuthorizationConfig {
    private String secretkey;
    private int expiresec;
}
