package hu.unideb.inf.carinspection;

import hu.unideb.inf.carinspection.data.AppUserRepository;
import hu.unideb.inf.carinspection.domain.AppUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader implements CommandLineRunner {

    private final AppUserRepository appUserRepository;

    public TestDataLoader(AppUserRepository appUserRepository) {this.appUserRepository = appUserRepository;}

    @Override
    public void run(String... args) throws Exception {
        //appUserRepository.save(AppUser.builder().username("alice").password("123").build());
    }
}
