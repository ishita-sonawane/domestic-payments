package com.onlinebanking.domesticpayments.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour
    private static final String PUBLIC_KEY_BASE64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmJNt5DXZxOhP72DwAKg5fJ2tZYAsyVWZiw0EpXEsH1h6oGtjHRlOayLR66aKKuebn6wB4TofhPx4g9SslXbjTkfGPjEZCqrhmpwruAX863dRLJ8Yv19SM1tvGI6QQC85eKrhV5VJ1FNkajPTBXWg0kAWFW55WI3HqFoHB3Qb2mn84+21nMw4hQl3TpjR5HqEz4aOJY2WVlCr3daQ8rN7U0kwX/HyFcx0rJ6zHSSpgvG7yD99yDJ/EVdfi2tGSJrTLsnkc3n68zdIxUhTv8hIFr7WcyimWxh5Zohb2h4pYwTdT87TPf+5Mc+0zTqmHV5RWOzT0NRdlO8CfkcoZsroeQIDAQAB";
    private static final String PRIVATE_KEY_BASE64 =
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCYk23kNdnE6E/vYPAAqDl8na1lgCzJVZmLDQSlcSwfWHqga2MdGU5rItHrpooq55ufrAHhOh+E/HiD1KyVduNOR8Y+MRkKquGanCu4Bfzrd1Esnxi/X1IzW28YjpBALzl4quFXlUnUU2RqM9MFdaDSQBYVbnlYjceoWgcHdBvaafzj7bWczDiFCXdOmNHkeoTPho4ljZZWUKvd1pDys3tTSTBf8fIVzHSsnrMdJKmC8bvIP33IMn8RV1+La0ZImtMuyeRzefrzN0jFSFO/yEgWvtZzKKZbGHlmiFvaHiljBN1PztM9/7kxz7TNOqYdXlFY7NPQ1F2U7wJ+Ryhmyuh5AgMBAAECggEABQNEUdedFdWQxBYaEdjKZA2QJAIP4UuBKxWHPgzs/K39ABFBz95uz6FKEU/G6/2br3rIvbFvAKuvepv9pul23Vxepy4YxbDIjw+rdgp9p+94c3AEsaDm7qNbOozQhh38aY+qWKTMff/WuuXZ/IIZVwmzpV+cTVcBz3D/GHUYRyw1mh7ZPZCm3dcfgCOENYARQExjX2cOh1KqbjU0JZl7XAm0RC6Q5IFpqD6CS+ZMfeKF8gXnl0feZm7irm7Ek7OqjcwrTJ27dOaCf9zrt1iuNF/DTnuWalKLb2+4HE6ri+7MgV7ACN59YkAz4iHrzqlwGR8o9tiqg5ApZ1j/tTDZEwKBgQC8MdZTOB1xqZmO1LCiMHDHoen1m2S0sxjNcoYCfPliSLjtzmtXNAg+NsJy2IrUH4TDx8S8tEeRrIZtxYTdv3QpqTqrKFxUMZEhSjAWCPUsTLTUwTh8T39Zj4jeiET8fMs0VsQ4rqH7plO+6eJZCvx28lXFn3GtFuIOVAcfjpwRZwKBgQDPjEnpSh9gzo7hF+p7tvAbaws6ECmQNYPK5VgVfPbWiYP/Xez25doR69fyOXkSOIXRb1O0HbYi64J663oN9iDx1XMxWl7G9gvC+HVx78SEoMRF83YwwPT1rJLpztUrjzZ1DiJ5KAESU+kXAACBKFk98rdicuaZjt+FJ+xnx3OrHwKBgDCONzsfdlFWLd1xOOWP0/ld6CxLXI9WyiZvzu2jawCVvMj2gjFsplfO7xqMjj0uqKWOzE7XwMNwHPsDhEVmWUVKeW2hqzi51TUenAuDYiZ84AcolzdTl4r3ApxP0mTGmfM2E8iAHiD8iAzw8UqCECNsYP7tJXpANjD2MyRMOi4vAoGAA+BS4RCJVX2GHZ4cuwLHqTtukj8LB654L6no4z3aPleDJ5nReyr/z6Xf+p4oLLbxiN/TaGHFrRFI9pK/TNNz+hBKfnl5m62suo95Yg9gVDnMcKIDaxWvfYcjl0pNoOqj0bvZ2PluS7FVgSB24fKm+Ak4c5ZByExq0EnWmHmZJ3ECgYA1W5OcbAoUSol3ZGlDKCH0kbxXROq1tqWvCjkCOswHu8sSG4KMGOpIBGGNbxA+xr2lCbVjIyiQdITm8MSv5oGf2Drq5L7EVlR/0yq9gdgWP9TuLEXkXFlnryCs8ms/LDCT/brapz/mLG+4eQK1JLRQwuTLBshXeZxpLZLbuuSblg=="; // Replace with your actual private key (PKCS#8, base64, no headers)

    //get public key from base64 string
    private PublicKey getPublicKey() {
        byte[] keyBytes = Base64.getDecoder().decode(PUBLIC_KEY_BASE64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        try {
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private PrivateKey getPrivateKey() {
        byte[] keyBytes = Base64.getDecoder().decode(PRIVATE_KEY_BASE64);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    //this is not used, we are getting token from the request header
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuer("online-banking")  // Optional: add this if you use requireIssuer
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }
    // This method is used to parse and validate the JWT token
    protected Jws<Claims> getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getPublicKey())
//              .requireIssuer("online-banking")  // Optional: add if you set an issuer
                .build()
                .parseSignedClaims(token);
    }

    public String extractUsername(String token) {
        return getClaims(token).getPayload().getSubject();
    }

    public boolean validateToken(String token) {
        return !getClaims(token).getPayload().getExpiration().before(new Date());
    }
}