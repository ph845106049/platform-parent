package com.platform.idpauth.config;

import com.platform.authclient.context.AuthPrincipal;
import com.platform.authclient.security.TokenVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class IdpTokenVerifier implements TokenVerifier {

    @Value("${platform.auth.introspect-url}")
    private String introspectUrl; // 例如 http://idp-auth:8080/me

    private final RestTemplate restTemplate;

    public IdpTokenVerifier(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AuthPrincipal verify(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        ResponseEntity<Map> resp = restTemplate.exchange(
                introspectUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        Map body = resp.getBody();
        return new AuthPrincipal(
                Long.valueOf(body.get("userId").toString()),
                body.get("username").toString()
        );
    }
}
