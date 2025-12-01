package carros.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final String SECRET = "MiClaveSuperSecreta1234567890MiClaveSuperSecreta";
	private final long EXPIRATION = 1000 * 60 * 60; // 1 hora

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}

	public String generarToken(String username, String rol) {
		return Jwts.builder().setSubject(username).claim("rol", rol).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public Claims validarToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	public String extraerUsuario(String token) {
		return validarToken(token).getSubject();
	}

	public String extraerRol(String token) {
		return validarToken(token).get("rol", String.class);
	}
}
