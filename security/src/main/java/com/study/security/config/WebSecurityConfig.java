package com.study.security.config;

//https://www.baeldung.com/security-spring

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
		.csrf().disable() // post 방식으로 값을 전송할 때 token을 사용해야하는 보안 설정을 해제
			.authorizeHttpRequests((requests)->requests
					.antMatchers("/","/home","/register").permitAll()
					.antMatchers("/hello").hasRole("user")
					.anyRequest().authenticated()
					)
			.formLogin((form)->form
					.loginPage("/login")
					.permitAll()
					.loginProcessingUrl("/home")
					)
			.logout((logout)->logout
					.deleteCookies("JSESSIONID")
					.permitAll());
		
		return http.build();

	}
	//jdbc 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
	  throws Exception {
	    auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery("select username, password, enabled " //순서중요
	        + "from userTBL "
	        + "where username = ?")
	      .authoritiesByUsernameQuery("select u.username, r.name "
	        + "from user_roleTBL ur inner join userTBL u on ur.user_id = u.id "
	        + "inner join roleTBL r on ur.role_id = r.id "
	        + "where u.username = ?");
	}
	

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	

}
