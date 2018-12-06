package config;

import config.authentication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableSpringDataWebSupport
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    MyUserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;
    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider());
    }

    private RequestMatcher csrfRequestMatcher = new RequestMatcher() {

        private RegexRequestMatcher requestMatcher =
                new RegexRequestMatcher("/login", null);

        @Override
        public boolean matches(HttpServletRequest request) {
            return requestMatcher.matches(request);
        }

    };


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .requireCsrfProtectionMatcher(csrfRequestMatcher)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and().authorizeRequests().antMatchers("/**").permitAll()
                .and().addFilterAt(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin().permitAll()
                .and().logout().logoutSuccessHandler(RESTLogoutHandler())
                .and().addFilterAfter(new CsrfGrantingFilter(), SessionManagementFilter.class);
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(RESTAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(RESTAuthenticationFailureHandler());
        return filter;

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        AuthenticationProvider authProvider
                = new AuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RESTAuthenticationSuccessHandler RESTAuthenticationSuccessHandler() {
        return new RESTAuthenticationSuccessHandler();
    }

    @Bean
    public RESTAuthenticationFailureHandler RESTAuthenticationFailureHandler() {
        return new RESTAuthenticationFailureHandler();
    }

    @Bean
    RESTLogoutHandler RESTLogoutHandler() {
        return new RESTLogoutHandler();
    }

    @Bean
    public RequestContextListener RequestContextListener() {
        return new RequestContextListener();
    }
}
