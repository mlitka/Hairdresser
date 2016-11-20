package com.litkowska.martyna.hairdresser.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

/**
 * Created by Martyna on 12.11.2016.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {

        return new ProviderManager(Arrays.asList(authenticationProvider));

    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManager());
        authenticationTokenFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        return authenticationTokenFilter;
    }


    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                // All urls must be authenticated (filter for token always fires (/**)
                .authorizeRequests()
                .antMatchers("/rest/**", "/user/**").permitAll()
//                .antMatchers("/rest/refresh").authenticated()
//                .antMatchers("/rest/**").authenticated()
                .anyRequest().authenticated()
                .and()
                // Call our errorHandler if authentication/authorisation fails
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //.and()
        // Custom JWT based security filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity.headers().cacheControl();


//        http
//                    .formLogin()
//                        .loginPage("/login")
//                        .loginPage( "/login" )
//                        .loginProcessingUrl( "/login" )
//                        .successForwardUrl("/")
//                .and()
//                    .logout()
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        .logoutSuccessUrl("/")
////                .httpBasic()
//                .and()
//                    .authorizeRequests()
//                        .antMatchers("/visits/**").access("hasRole('ADMIN')").anyRequest().authenticated()
//                        .antMatchers("/visits/{hairdresser}").access("hasRole('HAIRDRESSER')").anyRequest().authenticated()
//                        .antMatchers("/visit/reserve").access("hasAnyRole('ADMIN','USER')").anyRequest().authenticated()
//                    .anyRequest().permitAll()
//                .and().csrf().disable();//.addFilterBefore(new StatelessCSRFFilter(), CsrfFilter.class);
    }

    @Autowired
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("hair").password("hair").roles("HAIDRESSER");
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
    }

}
