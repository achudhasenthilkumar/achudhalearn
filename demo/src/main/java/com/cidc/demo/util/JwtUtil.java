package com.cidc.demo.util;

import java.nio.file.AccessDeniedException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.cidc.demo.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private static String secret="secret_key";

	private static int expiryDuration= 60 * 60;

	public String generateJWT(User users)
	{
		Long issuedtime=System.currentTimeMillis();
		Long Expirytime=issuedtime+expiryDuration+1000;

		Date IssuedAt=new Date(issuedtime);
		Date ExpiryAt=new Date(Expirytime);

		Claims claims=Jwts.claims()
				.setIssuedAt(IssuedAt)
				.setExpiration(ExpiryAt)
				.setSubject(users.getEmail());

		claims.put("first_name",users.getFirst_name());
		claims.put("email",users.getEmail());


		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512,secret)
				.compact();
	}
	  public Claims verify(String authorization) throws Exception {

	        try {
	            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody();
	            return claims;
	        } catch(Exception e) {
	            throw new AccessDeniedException("Access Denied");
	        }

	    }
	}



//	//retrieve username from jwt token
//	public String getUsernameFromToken(String token) {
//		return getClaimFromToken(token, Claims::getSubject);
//	}
//
//	//retrieve expiration date from jwt token
//	public Date getExpirationDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getExpiration);
//	}
//
//	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = getAllClaimsFromToken(token);
//		return claimsResolver.apply(claims);
//	}
//    //for retrieveing any information from token we will need the secret key
//	public Claims getAllClaimsFromToken(String token) {
//		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//}
