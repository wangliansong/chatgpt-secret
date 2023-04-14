package com.omggpt.bj.config;

import com.omggpt.bj.filter.JwtLoginFilter;
import com.omggpt.bj.filter.JwtVerifyFilter;
import com.omggpt.bj.handle.MyAccessDeniedHandle;
import com.omggpt.bj.handle.MyAuthenticationEntryPoint;
import com.omggpt.bj.mapper.UserMapper;
import com.omggpt.bj.service.IUserService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OmgSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Resource
    private IUserService userService;
    @Resource
    private MyAccessDeniedHandle myAccessDeniedHandle;
    @Resource
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedissonClient redissonClient;


    @Bean
    public JwtConfig getJwtConfig() {
        return new JwtConfig();
    }

    /**
     * Remove the ROLE_ prefix
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.    //关闭csrf
                csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandle) // 自定义无权限访问
                .authenticationEntryPoint(myAuthenticationEntryPoint) // 自定义未登录返回
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 允许访问
                .antMatchers("/login").permitAll()
                .antMatchers("/api/register").permitAll()
                .anyRequest().authenticated() // 其他请求拦截
                .and()
                .cors();
        http.addFilterBefore(new JwtLoginFilter(super.authenticationManager(), userMapper, redissonClient), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtVerifyFilter(super.authenticationManager(), redissonClient), BasicAuthenticationFilter.class);
        //禁用session
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // UserDetailsService类
        auth.userDetailsService(userService)
                // 加密策略
                .passwordEncoder(passwordEncoder);

    }

    /**
     * 解决 AuthenticationManager 无法注入的问题
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
