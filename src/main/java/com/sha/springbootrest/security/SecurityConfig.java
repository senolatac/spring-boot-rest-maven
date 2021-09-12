package com.sha.springbootrest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sa
 * @date 2020-04-11
 * @time 13:49
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Cross-origin-resource-shairng: Serve it from -> localhost: 8080, Request from -> localhost:4200 (permit/block with cors)
        http.cors().and()
        .authorizeRequests()
                //There are public paths so reachable paths by everybody.
                .antMatchers("/error", "/api/user/**").permitAll()
                //These can be reachable for just admin role. In here, ADMIN means: ADMIN or ROLE_ADMIN;
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                //All remaining paths should need authentication.
                .anyRequest().fullyAuthenticated()
                .and()
                //logout will log the user out by invalidated session.
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/user/logout", "POST"))
                .and()
                //login form and path
                .formLogin().loginPage("/api/user/login").and()
                //Enable basic authentication. So our Authorization type is BasicAuthorization. bto(username:password)
                .httpBasic().and()
                //Default SessionPolicy is IF_REQUIRED: a session will be created only if required.
                //STATELESS: no session will be created or used.
                //Example: changeUserRole from USER to ADMIN with endpoint.
                // IF_REQUIRED keeps session-data(USER), STATELESS keeps new-data(ADMIN).
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //Cross side request forgery.
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                //Exp: addMapping("api/**").allowedeOrigins("http://d1.com, http://d2.com").allowedMethods("GET, POST");
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
