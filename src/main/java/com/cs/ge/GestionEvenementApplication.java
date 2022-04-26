package com.cs.ge;
import com.cs.ge.repositories.UtilisateurRepository;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.SpringDataMongo3Driver;
import com.github.cloudyrock.spring.v5.EnableMongock;
import com.github.cloudyrock.spring.v5.MongockSpring5;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@EnableMongock
@SpringBootApplication
public class GestionEvenementApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionEvenementApplication.class, args);
	}
	//@Bean
	//PasswordEncoder passwordEncoder (){
	//return new BCryptPasswordEncoder();




	//@Bean
	//public AuthenticationProvider authenticationProvider(){
	//	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		//provider.setUserDetailsService();
//	}

	@Bean
	public MongockSpring5.MongockApplicationRunner mongockApplicationRunner(ApplicationContext springContext, UtilisateurRepository utilisateurRepository) {
		return MongockSpring5.builder()
				.setDriver(new SpringDataMongo3Driver(utilisateurRepository))
				.addChangeLogsScanPackages("your changeLog package path")
				.setSpringContext(springContext)
				// any extra configuration you need
				.buildApplicationRunner();
	}

}
