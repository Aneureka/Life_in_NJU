package leftovers;

import leftovers.security.CustomAuthenticationProvider;
import leftovers.security.JWTAuthenticationFilter;
import leftovers.security.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

/**
 * Created by kevin on 2017/5/15.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityCofig extends WebSecurityConfigurerAdapter{
    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    protected void configure(HttpSecurity http) throws Exception{
        http
            .authorizeRequests() //定制我们应用中的哪些请求需要用户被认证
                .antMatchers("/api/user/signup").permitAll()
                .antMatchers("/api/user/forgetPassword").permitAll()
                .antMatchers("/api/user/checkRegisterInfo").permitAll()
                .antMatchers("/api/user/**").authenticated()
                .antMatchers("/api/comment/get_remote_auth_s3").authenticated()
                .anyRequest().permitAll()
                .and()
            .addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .csrf()
                .disable(); //关闭csrf验证
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
