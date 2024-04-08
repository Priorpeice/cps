package server.cps.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.cps.auth.repository.LoginRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    //Encoderf로 인코딩 한번 더 , roles에다가 넣기
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginRepository.findAuthById(username)
                .map(login ->
                        User.builder()
                                .username(login.getId())
                                .password(passwordEncoder.encode(login.getPw()))
                                .roles(login.getUserRole())
                                .build()
                ) .orElseThrow(() -> new UsernameNotFoundException("Could not found user for " + username));
    }
}
