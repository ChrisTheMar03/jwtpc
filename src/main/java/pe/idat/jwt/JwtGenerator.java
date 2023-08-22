package pe.idat.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pe.idat.model.jwt.JwtUser;

@Component("beanGenerator")
public class JwtGenerator 
{
	private int expirationInMs=60400; //equivale a 60 segundos para expirar
	
	public String generateToken(JwtUser jwtUser)
	{
		String tokenJWT=null;
		
		try
		{
			//fecha de expiración
			Date dateExpiration=new Date(new Date().getTime()+this.expirationInMs);
			
			//diccionario de datos, nos permite preparar información para la llave.
			Claims claims=Jwts.claims().setSubject(jwtUser.getUsername())
					                   .setIssuedAt(new Date()).setExpiration(dateExpiration);
			
			claims.put("userId",jwtUser.getUserId().toString());
			claims.put("role",jwtUser.getRole());
			
			//construcción del token
			tokenJWT=Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256,"MY_SECRET").compact();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return tokenJWT;
	}
}
