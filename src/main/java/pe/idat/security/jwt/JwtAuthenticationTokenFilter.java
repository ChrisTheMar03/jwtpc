package pe.idat.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import pe.idat.model.jwt.JwtAuthenticationToken;

//filtrar el token correctamente
public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter 
{
	public JwtAuthenticationTokenFilter() {
		super("/rest/**"); //endpoint de inicio para el JWT
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException 
	{
		String authorization=request.getHeader("authorization");
		
		if(authorization!=null && authorization.startsWith("Bearer "))
		{
			String authenticationToken=authorization.substring(7);
			JwtAuthenticationToken token=new JwtAuthenticationToken(authenticationToken);
			
			return this.getAuthenticationManager().authenticate(token);
		}		
		
		throw new RuntimeException("¡Error, proceso JWT incorrecto!");
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
}
