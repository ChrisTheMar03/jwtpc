package pe.idat.jwt;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import pe.idat.model.jwt.JwtUser;

@Component("beanValidator")
public class JwtValidator 
{
	public JwtUser validatorToken(String token)
	{
		JwtUser jwtUser=null;
		
		try
		{
			//obtener el body (PAYLOAD)
			Claims claims=Jwts.parser().setSigningKey("MY_SECRET").parseClaimsJws(token).getBody();
			
			jwtUser=new JwtUser();
			
			//cargando el objeto jwtUser de la información del token
			jwtUser.setUserId(Integer.parseInt(claims.get("userId").toString()));
			jwtUser.setUsername(claims.getSubject());
			jwtUser.setRole(claims.get("role").toString());
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return jwtUser;
	}
}
