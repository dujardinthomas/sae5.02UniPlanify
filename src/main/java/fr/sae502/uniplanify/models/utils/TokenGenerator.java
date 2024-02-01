package fr.sae502.uniplanify.models.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    public String generateToken(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[length];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
    
}
