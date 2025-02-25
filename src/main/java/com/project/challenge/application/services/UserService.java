package com.project.challenge.application.services;

import com.project.challenge.application.dto.UserDto;
import com.project.challenge.application.mapper.UserMapper;
import com.project.challenge.domain.entity.Users;
import com.project.challenge.domain.model.CustomUserDetails;
import com.project.challenge.infrastructure.persistence.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        Optional<Users> userDetail = userInfoRepository.findByUsername(user);

        return userDetail.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + user));
    }

    public void addUser(UserDto userDto) {

        var user = userMapper.toEntity(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        userInfoRepository.save(user);

    }
}