package kz.bitlab.bootcamp.finalproject.config;

import kz.bitlab.bootcamp.finalproject.models.ActiveUserStore;
import kz.bitlab.bootcamp.finalproject.models.MyAuthenticationSuccessHandler;
import kz.bitlab.bootcamp.finalproject.models.MyLogoutSuccessHandler;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import kz.bitlab.bootcamp.finalproject.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true, securedEnabled = true)
public class SecurityConfig {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userService()).passwordEncoder(passwordEncoder());

        http.exceptionHandling().accessDeniedPage("/forbidden");
        http.authorizeRequests().antMatchers("/css/**", "/js/**").permitAll();

        http.formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/auth") // <form action = "/auth" method = "post">
                .usernameParameter("user_email") // <input type = "email" name = "user_email">
                .passwordParameter("user_password") // <input type = "password" name = "user_password">
                .successHandler(myAuthenticationSuccessHandler)
                .failureUrl("/signin?error") //redirect when incorrect inputs
                .permitAll();

        http.sessionManagement()
//                .invalidSessionUrl("/invalidSession.html")
                .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
                .sessionFixation().none();

        http.logout()
                .logoutUrl("/signout") // action = "/signout"
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();

        http.csrf().disable();

        return http.build();
    }

}
