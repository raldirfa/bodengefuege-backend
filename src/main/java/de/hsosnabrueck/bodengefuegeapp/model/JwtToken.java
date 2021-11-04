package de.hsosnabrueck.bodengefuegeapp.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import de.hsosnabrueck.bodengefuegeapp.Config;

import java.security.Key;
import java.util.LinkedHashMap;
import java.util.Map;

//https://github.com/jwtk/jjwt

public class JwtToken {


    // public static void main(String[] args) {
    //     String jwt = createJWT("de.hsos.bodengefuege", "USERNAME", "PASSWORD");
    //     System.out.println(jwt);
    //     System.out.println(decodeJWT(jwt));
    // }
    
    //Sample method to construct a JWT
    public static String createJWT(String issuer, User user) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Config.SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id",user.getId());
        map.put("user", user.getEmail());


        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuer(issuer)
                .signWith(signingKey)
                .addClaims(map);    

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(Config.SECRET_KEY)).build().parseClaimsJws(jwt).getBody();

        return claims;
    }

}