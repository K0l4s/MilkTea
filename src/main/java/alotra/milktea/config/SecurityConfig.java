package alotra.milktea.config;

import alotra.milktea.repository.IRoleRepo;
import alotra.milktea.repository.IUserRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    IUserRepo userRepo;
    @Autowired
    IRoleRepo roleRepo;

    @Bean
    public UserDetailsService userDetailsService() {
        List<alotra.milktea.entity.User> users = userRepo.findAll();
        List<UserDetails> userDetailsList = new ArrayList<>();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        for (alotra.milktea.entity.User item : users) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(item.getUsername())
                    .password(passwordEncoder.encode(item.getPassword()))
                    .roles(item.getRole().getName())
                    .authorities(item.getRole().getName())
                    .accountLocked(!item.isEnable())
//                    .disabled(item.isEnable())
                    .build();
            userDetailsList.add(userDetails);
        }

        return new InMemoryUserDetailsManager(
               userDetailsList
        );
//        return userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN","CREATOR","EDITOR")
                        .requestMatchers("/add/**").hasAnyAuthority("ADMIN","CREATOR")
                        .requestMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
                        .requestMatchers("/delete/**").hasAuthority("ADMIN")
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/forgotPassword").permitAll()
                        .requestMatchers("/vetifyRegister").permitAll()
                        .requestMatchers("/forgot").permitAll()
                        .requestMatchers("/sendReqPass").permitAll()
                        .requestMatchers("/resources/templates/home/**").permitAll()
                        .anyRequest().authenticated()

                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home",true)
                        .permitAll().successHandler(successHandler()))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .deleteCookies("username")
                        .logoutSuccessUrl("/login")
                        .permitAll())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/error/403"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            // Đăng nhập thành công, thiết lập Session và Cookie
            String username = authentication.getName();

            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            Cookie usernameCookie = new Cookie("username", username);
            usernameCookie.setMaxAge(3600); // Thời gian sống của cookie (giây), ở đây là 1 giờ
            response.addCookie(usernameCookie);

            // Chuyển hướng đến trang home
            response.sendRedirect("/home");
        };
    }
}
