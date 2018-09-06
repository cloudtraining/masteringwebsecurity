package io.cloudtraining.springbootsecurity;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jwt.JWTClaimsSet;
import java.util.Date;

public class JwtToken {

    public void getJwtToken(){

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject("my-jwt-token")
                .issueTime(new Date())
                .expirationTime( new Date(System.currentTimeMillis() + (1000*3600)) ) //1hr
                .jwtID("79941e10-3e99-4cd8-8fff-461e93aa5452")
                .claim("http://example.com/is_root", true)
                .build();

        Payload payload = new Payload(claims.toJSONObject());
        JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);

        String secret = "EA3F10AE5F0A4F50596812E2529DA3F8";  //asecuritysite.com/encryption/keygen
        byte[] secretKey = secret.getBytes();
        DirectEncrypter directEncrypter = null;
        try {
            directEncrypter = new DirectEncrypter(secretKey);
            JWEObject jweObject = new JWEObject(header, payload);
            jweObject.encrypt(directEncrypter);
            String token = jweObject.serialize();
        } catch (KeyLengthException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
    }

}