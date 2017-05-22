package com.ij.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ij.model.Driver;
import com.ij.model.service.DriverRepository;

/**
 * 
 * @author Indy
 *
 */
@Configurable
@EnableWebSecurity
// Modifying or overriding the default spring boot security.
public class WebConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	DriverRepository driverRepository;
	
	// This method is for overriding the default AuthenticationManagerBuilder.
	// We can specify how the user details are kept in the application. It may
	// be in a database, LDAP or in memory.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService());
		
	}
	@Bean
	protected
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Driver driver = driverRepository.findByUsername(username);
				if (driver != null) {
					return new User(driver.getUsername(), driver.getPassword(), true, true, true, true,
							getAuthorities(driver));
				} else {
					throw new UsernameNotFoundException("could not find the user '" + username + "'");
				}
			}

			private Set<GrantedAuthority> getAuthorities(Driver driver){
		        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		        for(String role : driver.getRoles()) {
		            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
		            authorities.add(grantedAuthority);
		        }
		        return authorities;
		    }

		};
	}
	


	// This method is for overriding some configuration of the WebSecurity
	// If you want to ignore some request or request patterns then you can
	// specify that inside this method
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	// This method is used for override HttpSecurity of the web Application.
	// We can specify our authorisation criteria inside this method.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// starts authorising configurations
				.authorizeRequests()
				// ignore the "/" and "/index.html"
				.antMatchers("/", "/index.html", "/app/app.js", "/css/**", "/favicon.ico").permitAll()
				// following URL is only accessible for ADMIN users
				.antMatchers("/drivers/admin/**").hasAuthority("ADMIN")
				// following URL is only accessible users how has either USER or ADMIN authority
				.antMatchers("/drivers/user/**").hasAnyAuthority("USER","ADMIN")
				// authenticate all remaining URLS
				.anyRequest().fullyAuthenticated().and()
				// enabling the basic authentication
				.httpBasic().and()
				// configuring the session as state less. Which means there is
				// no session in the server
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// disabling the CSRF - Cross Site Request Forgery
				.csrf().disable();
	}

}
