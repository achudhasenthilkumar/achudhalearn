package com.cidc.demo;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

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
		claims.put("last_name", users.getLast_name());
		claims.put("avatar",users.getAvatar());
		claims.put("email",users.getEmail());

		return Jwts.builder()
			    .setId(UUID.randomUUID().toString())
			    .setSubject(users.getEmail())
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512,secret)
				.compact();
	}
}
