package com.adminservice;
//import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    //@Autowired
    //DataSource dataSource;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(requests -> requests
        .anyRequest().permitAll())
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults());  // Enable CORS

        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        UserDetails citizen = User.withUsername("citizen")
                .password("{noop}citizen123")
                .roles("CITIZEN")
                .build();
                
        /*
        JdbcUserDetailsManager userDetailsManager   =   new JdbcUserDetailsManager(dataSource);

        userDetailsManager.createUser(admin);
        
        userDetailsManager.createUser(citizen);
        return userDetailsManager; 
        */
        return new InMemoryUserDetailsManager(admin, citizen);
    }
}
