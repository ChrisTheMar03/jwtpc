package pe.idat.security.jwt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import pe.idat.jwt.JwtValidator;
import pe.idat.model.jwt.JwtAuthenticationToken;
import pe.idat.model.jwt.JwtUser;
import pe.idat.model.jwt.JwtUserDetails;

//bean para la autenticación real del token JWT
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider
{
	@Autowired
	@Qualifier("beanValidator")
	private JwtValidator jwtValidator;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
		UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
		throws AuthenticationException 
	{		
		JwtAuthenticationToken authenticationToken=(JwtAuthenticationToken)authentication;
		String token=authenticationToken.getToken();
		
		JwtUser jwtUser=jwtValidator.validatorToken(token);
		
		if(jwtUser!=null)
		{
			Collection<GrantedAuthority> authorities=AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());
			JwtUserDetails jwtUserDetails=new JwtUserDetails(jwtUser.getUserId(),jwtUser.getUsername(),token,authorities);
			
			return jwtUserDetails;
		}
		
		throw new RuntimeException("¡Error, token JWT incorrecto!");
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
