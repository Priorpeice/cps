package server.cps.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.cps.auth.repository.LoginRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoginRepository loginRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginRepository.findById(username)
                .map(login ->
                        User.builder()
                                .username(login.getId())
                                .password(login.getPw())
                                .authorities(String.valueOf(login.getMember().getRole().getUserRole()))
                                .build()
                ) .orElseThrow(() -> new UsernameNotFoundException("Could not found user for " + username));
    }
}
