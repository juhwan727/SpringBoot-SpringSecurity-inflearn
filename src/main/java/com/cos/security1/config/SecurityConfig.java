package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)  // secured 어노테이션 활성화 / preAuthorize(전), postAuthorize(후) 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()                                                            // 로그인이 필요한 페이지
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")      // admin 또는 manager 권한이 필요한 페이지
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")                                   // admin 권한이 필요한 페이지
                .anyRequest().permitAll()                                                                                      // 이 밖에 나머지 페이지는 모두 허용
                .and()
                .formLogin()
                .loginPage("/loginForm")
//                .usernameParameter("id")   username 말고 다른 파라미터 사용할 때
                .loginProcessingUrl("/login")                                                                                   // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
                .defaultSuccessUrl("/");                                                                                        // login 후 무조건 index가 아닌 요청했던 페이지로 돌아가줌
        
    }
}
