package de.hsosnabrueck.bodengefuegeapp.model;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.security.Key;

import io.jsonwebtoken.*;

import java.util.HashMap;
import java.util.Map;

//Source: https://github.com/jwtk/jjwt

public class JwtToken {
    private static String SECRET_KEY = "S9coD45ANeXrKJJ_XKdiQLvwyP_qalszR8KFvAoKrPovl6A1Jl2LKTMLwma6iL9wwdpwbYE5ogipCYQ2jNR62g";//"oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

    // // Test Main
    // public static void main(String[] args) {
    //     String jwt = createJWT("de.hsos.bodengefuege", "USERNAME", "PASSWORD");
    //     System.out.println(jwt);
    //     System.out.println(getPasswordFromJWT(jwt));
    // }
    
    //Sample method to construct a JWT
    public static String createJWT(String issuer, String user, String password) {

        //The JWT signature algorithm
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //Sign JWT with ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> map = new HashMap<>();  
        map.put("user", user);
        map.put("password", password);        

        //Set JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuer(issuer)
                .signWith(signingKey)
                .addClaims(map);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    private static Claims decodeJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).build().parseClaimsJws(jwt).getBody();

        return claims;
    }

    //Return User From JWT
    public static String getUserFromJWT(String jwt){
        String user = (String)decodeJWT(jwt).get("user");

        return user;
    }

    //Return Password From JWT
    public static String getPasswordFromJWT(String jwt){
        String password = (String)decodeJWT(jwt).get("password");

        return password;
    }

}