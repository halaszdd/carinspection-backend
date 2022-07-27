package hu.unideb.inf.carinspection;

import hu.unideb.inf.carinspection.data.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public DefaultUserDetailsService(AppUserRepository appUserRepository) {this.appUserRepository = appUserRepository;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new DefaultUserDetails(appUserRepository.findByUsername(username));
    }
}
