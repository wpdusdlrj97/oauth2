package org.client;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.client.constrant.UserRole;
import org.client.entity.ResourceOwner;
import org.client.repository.ResourceOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ClientApplication  implements CommandLineRunner, ServletContextInitializer
{
	
	@Autowired private ResourceOwnerRepository repository;
	@Autowired private PasswordEncoder passwordEncoder;
	
    public static void main( String[] args )
    {
        SpringApplication.run(ClientApplication.class, args);
    }
    
    @Override
	public void run(String... args) throws Exception {
		
		ResourceOwner user = new ResourceOwner();
		user.setId(1l);
		user.setUsername("1223yys@naver.com");
		user.setPassword(passwordEncoder.encode("password"));
		user.setRole(UserRole.ROLE_USER);
		
		repository.save(user);
		
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.getSessionCookieConfig().setName("clientsession");
	}
    
}
