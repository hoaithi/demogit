package com.cybersoft.demospringsecurity.service;


import com.cybersoft.demospringsecurity.entity.UserEntity;
import com.cybersoft.demospringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username);

        if(user == null){
            // không tìm thấy user nào có username như vậy trong hệ thống
            throw new UsernameNotFoundException("User không tồn tại");
        }else{
            User user1 = new User(user.getUsername(), user.getPassword(), new ArrayList<>());
            return user1;
        }
    }
}
