package com.infotec.springbootjwtroles.auth.security;

import com.infotec.springbootjwtroles.auth.user.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserAccountRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No existe: " + username));
        var authorities = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();

        return User.withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(authorities.toArray(new GrantedAuthority[0]))
                .build();
    }
}
