package my.SimplyPosting.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenProvider {
    @Autowired
    private JwtEncoder encoder;

    public String generateToken(String username) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .subject(username)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}
