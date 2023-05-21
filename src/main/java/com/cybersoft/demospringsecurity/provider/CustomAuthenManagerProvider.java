package com.cybersoft.demospringsecurity.provider;

import com.cybersoft.demospringsecurity.entity.UserEntity;
import com.cybersoft.demospringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenManagerProvider implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // lấy username người dùng truyền lên
        String username = authentication.getName();

        // lấy mật khẩu người dùng truyền lên
        String password = authentication.getCredentials().toString();

        // lấy thông tin user từ database thông qua username do người dùng truyền vào
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity != null){
            // so sánh password user truyền vào với password  Bcrypt trong database
           if( passwordEncoder.matches(password,userEntity.getPassword())){
               return new UsernamePasswordAuthenticationToken(username, userEntity.getPassword(), new ArrayList<>());
           }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        // hỗ trợ so sánh chứng thực cho AuthenticationManager
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
