package com.cs.ge.configuration;

import com.cs.ge.enums.Role;
import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class  SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected  void configure(AuthenticationManagerBuilder auth) throws Exception{
      //  auth.inMemoryAuthentication()
           //     .withUser("kwe").password(bCryptPasswordEncoder().encode("athe1").roles("ADMIN")
                //.and()
               // .withUser("min").password("minou").roles("ADMIN","USER");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
              //  .antMatchers(HttpMethod.POST,"/connexion").permitAll()
                .antMatchers(HttpMethod.POST,"inscription").permitAll()
               .anyRequest()
                .permitAll()
                     .and()
                .httpBasic();




    }
     @Bean
     public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
        }

}
