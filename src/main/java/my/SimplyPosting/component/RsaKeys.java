package my.SimplyPosting.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
@ConfigurationProperties(prefix = "rsa")
@Data
public class RsaKeys {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
}
