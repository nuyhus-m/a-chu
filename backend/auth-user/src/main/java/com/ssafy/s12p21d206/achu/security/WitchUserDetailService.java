package com.ssafy.s12p21d206.achu.security;

import com.ssafy.s12p21d206.achu.auth.domain.user.AuthUser;
import com.ssafy.s12p21d206.achu.auth.domain.user.AuthUserRepository;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WitchUserDetailService implements UserDetailsService {

  private final AuthUserRepository userRepository;

  public WitchUserDetailService(AuthUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AuthUser user = userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

    return new AuthUserDetails(user.id(), user.username(), user.password(), List.of("USER"));
  }
}
