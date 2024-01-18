package com.coco.security;

import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

   @Autowired
   private DataSource dataSource;
   @Autowired
   private SecurityUserDetailsService securityUserDetailsService;	

   // HttpSecurity - 사용자 인증 정보를 가지고 있는 객체
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
      // url이 "/"인 경우 모든 접근 허가
      security.authorizeRequests().antMatchers("/", "/system/**").permitAll();
      // authenticated - id.pwd를 통해 사용자 인증이 된 사람만 접근할 수 있는 url
      security.authorizeRequests().antMatchers("/board/**").authenticated();
      security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
      
      // csrf - Cross Site Request Forgery (sns 사용자 ID를 도용한 웹 사이트 공격)
      security.csrf().disable();

      // 사용자 인증을 위한 로그인 화면 사용 설정
      //security.formLogin().loginPage("/system/login").defaultSuccessUrl("/index", true); // 로그인에 사용할 url지정 -> 로그인 페이지
                                                                     // 제공하는 메소드 제공해야함
      
      security.formLogin().loginPage("/system/login").successHandler((request, response, authentication) -> {
          Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
          if (roles.contains("ROLE_ADMIN")) {
             response.sendRedirect("/admin/adminPage");
          } else {
             response.sendRedirect("/index");
          }
       }).permitAll();

      security.exceptionHandling().accessDeniedPage("/accessDenied");

      security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/");

      // Member 테이블에서 사용자 조회후, UserDetails 객체 변환 서비스
      security.userDetailsService(securityUserDetailsService);

      return security.build();
   }
   /*
	 * 비밀번호 암호화
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}  
