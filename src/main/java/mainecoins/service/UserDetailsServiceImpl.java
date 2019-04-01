package mainecoins.service;

import lombok.extern.slf4j.Slf4j;
import mainecoins.Repository.CustomUserRepository;
import mainecoins.exception.CustomException;
import mainecoins.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser user = customUserRepository.findByEmail(email);

        if (user == null) {
            log.info("User with name: " + email + " not found");
            throw new CustomException("User with email: " + email + " not found", HttpStatus.NOT_FOUND);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true,
                new ArrayList<>());

    }
}
