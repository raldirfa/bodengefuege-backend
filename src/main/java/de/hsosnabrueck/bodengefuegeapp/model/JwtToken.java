package hsosnabrueck.bodengefuegeapp.model;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.security.Key;

import io.jsonwebtoken.*;

import java.util.HashMap;
import java.util.Map;

//https://github.com/jwtk/jjwt

public class JwtToken {


    public static void main(String[] args) {
        String jwt = createJWT("de.hsos.bodengefuege", "USERNAME", "PASSWORD");
        System.out.println(jwt);
        System.out.println(decodeJWT(jwt));
    }
    // The secret key. This should be in a property file NOT under source
    // control and not hard coded in real life. We're putting it here for
    // simplicity.
    private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

    //Sample method to construct a JWT
    public static String createJWT(String issuer, String user, String password) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


        // long nowMillis = System.currentTimeMillis();
        // Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        Map<String, Object> map = new HashMap<>();  
        map.put("user", user);
        map.put("password", password);
        

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuer(issuer)
                .signWith(signingKey)
                .addClaims(map);

        //if it has been specified, let's add the expiration
        // if (ttlMillis >= 0) {
        //     long expMillis = nowMillis + ttlMillis;
        //     Date exp = new Date(expMillis);
        //     builder.setExpiration(exp);
        // }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).build().parseClaimsJws(jwt).getBody();

        return claims;
    }

}