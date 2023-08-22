package pe.idat.model.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails
{
	private static final long serialVersionUID=1L;
	
	private Integer userId;
	private String username;
	private String token;
	private Collection<? extends GrantedAuthority> authorities;
	
	public JwtUserDetails() {		
	}

	public JwtUserDetails(Integer userId, String username, String token, Collection<? extends GrantedAuthority> authorities) {
		this.userId = userId;
		this.username = username;
		this.token = token;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; //no expire la cuenta
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; //no bloqueada la cuenta
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; //no expire las credenciales
	}

	@Override
	public boolean isEnabled() {
		return true; //habilitado
	}
}
