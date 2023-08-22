package pe.idat.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.idat.jwt.JwtGenerator;
import pe.idat.model.Login;
import pe.idat.model.jwt.JwtUser;

@RestController
public class HomeRestController 
{
	@Autowired
	@Qualifier("beanGenerator")
	private JwtGenerator jwtGenerator;
	
	private static Collection<Double> itemsMovimientoBancario=new ArrayList<>();
	static
	{
		itemsMovimientoBancario.add(+200.0);
		itemsMovimientoBancario.add(+400.0);
		itemsMovimientoBancario.add(-100.0);
		itemsMovimientoBancario.add(+500.0);
		itemsMovimientoBancario.add(-700.0);
	}
	
	public HomeRestController() {		
	}	
	
	@GetMapping("/rest/accessBank")
	public ResponseEntity<?> movimientoBancario_GET() {
		return new ResponseEntity<>(itemsMovimientoBancario,HttpStatus.OK);
	}
	
	@PostMapping("/token")
	public ResponseEntity<String> generateToken_POST(@RequestBody Login login)
	{
		String username=login.getUsername();
		String password=login.getPassword();
		
		if(username.equals("katiuska") && password.equals("k123"))
		{
			JwtUser jwtUser=new JwtUser(1,username,"ADMIN");
			String token=this.jwtGenerator.generateToken(jwtUser);
			
			return new ResponseEntity<String>(token,HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
