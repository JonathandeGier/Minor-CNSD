package nl.quintor._security.service;

import lombok.RequiredArgsConstructor;
import nl.quintor._security.repository.UserRepository;
import nl.quintor._security.security.UserDetailsAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;



@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsAdapter(userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user with username: %s was found", username))));
    }


}
