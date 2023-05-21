package com.cybersoft.demospringsecurity.config;

import com.cybersoft.demospringsecurity.filter.JwtFilter;
import com.cybersoft.demospringsecurity.provider.CustomAuthenManagerProvider;
import com.cybersoft.demospringsecurity.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    CustomAuthenManagerProvider customAuthenManagerProvider;


    @Autowired
    JwtFilter jwtFilter;
    /*
    * khai báo dạng mã hóa
    * */

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // tạo danh sách user dùng để chứng thực khi đăng nhập và lưu ở memory
    /*@Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){

        UserDetails user = User.withUsername("user1")
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin  );
    }*/


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws  Exception{
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenManagerProvider).build();


    }


    // custome lại thông tin cấu hình của spring security
    // đây là phần quan trọng nhất của spring security
    // cấu hình lại spring security, cho phép quy  địn cấu hình, cho phép đường link nào được phép truy cập vào website
    //crsf: dùng để chống hacker không cho người dùng khác đăng nhập vào
    // vì vậy chúng ta cần disable nó để những khách hàng được cung cấp link có thể vào được
    //
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        // csrf: dùng để chống hacker không cho người dùng khác đăng nhập vàp
        // vì vậy chúng ta cần disable nó để những khách hàng được cung cấp linh có thể vào được
        return httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // authorizeHttpRequests: tất cả các yêu cầu gọi vào link web từ phía người dùng đều cần phải chứng thực
                .authorizeHttpRequests()
                    // antMatchers: quy định link cần chứng thực, permitAll cho phép vào thoải mái
                    .antMatchers("/hello").permitAll()
                    // quy định link "/admin": chỉ người nào có quyền là admin mới vào được link
                    .antMatchers("/admin").hasRole("ADMIN")
                    .antMatchers("/login/**").permitAll()

                    .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // do là chúng ta đang sử dụng chuẩn cũ nên cần thêm htttpBasic vào
                // sau này làm việc với chuẩn mới hiện nay thì không cần sử dụng tới vì đã được cài đặt sẵn
                .build();
    }




}
