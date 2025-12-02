package dev.karimshaaban.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        //since we created users here spring boot wouldn't use username & password in application.properties
//        UserDetails john = User.builder().username("john").password("{noop}test123").roles("EMPLOYEE").build();
//        UserDetails mary = User.builder().username("mary").password("{noop}test123").roles("EMPLOYEE","MANAGER").build();
//        UserDetails susan = User.builder().username("susan").password("{noop}test123").roles("EMPLOYEE","MANAGER","ADMIN").build();
//        return new InMemoryUserDetailsManager(john,mary,susan);
//}
    //must have users & authorities(roles) tables
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        //define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT user_id, pw, active FROM members WHERE user_id=?");
        //define query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT user_id, role FROM roles WHERE user_id=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
            configurer
                    .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("EMPLOYEE")
                    .requestMatchers(HttpMethod.POST,"/api/employees").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PUT,"/api/employees/**").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PATCH,"/api/employees/**").hasRole("Manager")
                    .requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("ADMIN")

        );
        //use HTTP Basic Authentication
        http.httpBasic(Customizer.withDefaults());
        //disable csrf not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
        http.csrf(csrf->csrf.disable());
        return http.build();
    }
}
