package pe.idat.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pe.idat.security.jwt.JwtAuthenticationEntryPoint;
import pe.idat.security.jwt.JwtAuthenticationProvider;
import pe.idat.security.jwt.JwtAuthenticationSuccessHandler;
import pe.idat.security.jwt.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;
	
	@Autowired
	private JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		//permiso JWT
		http.authorizeRequests()
		    .antMatchers("**/rest/**").authenticated();
		
		//excepcion JWT
		http.authorizeRequests().and()
		    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
		
		//filtrar solicitud de seguridad JWT
		http.addFilterBefore(authenticationTokenFilter(),UsernamePasswordAuthenticationFilter.class);
		
		http.authorizeRequests().and()
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests().and()
		    .csrf().disable();
		
		//evitar visualización del historial del navegador
		http.headers().cacheControl();
	}
	
	//bean para recuperar información
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
	}
	
	//bean para filtrar y extraer el token JWT de la autenticación
	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilter()
	{
		JwtAuthenticationTokenFilter filter=new JwtAuthenticationTokenFilter();
		
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
		
		return filter;
	}
}
