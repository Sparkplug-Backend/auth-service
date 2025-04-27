package com.sparkplug.auth.infrastructure.security.service;

import com.sparkplug.auth.application.repository.UsersRepository;
import com.sparkplug.auth.domain.vo.Username;
import com.sparkplug.commonauthentication.user.SparkplugUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SparkplugUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public SparkplugUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userOptional = usersRepository.findByUsername(new Username(username));
        if (userOptional.isPresent()) {
            var user = userOptional.get();

            var id = user.getId();
            var email = user.getEmail() == null
                    ? null
                    : user.getEmail().value();

            var phoneNumber = user.getPhoneNumber() == null
                    ? null
                    : user.getPhoneNumber().value();

            var authorities = user.getAuthorities();
            var password = user.getPasswordHash();

            return new SparkplugUserDetails(id, email, phoneNumber, authorities, password, username);
        }

        throw new UsernameNotFoundException("User with value '" + username + "' not found.");
    }
}
