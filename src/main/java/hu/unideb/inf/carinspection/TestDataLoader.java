package hu.unideb.inf.carinspection;

import hu.unideb.inf.carinspection.data.AppUserRepository;
import hu.unideb.inf.carinspection.data.CarRepository;
import hu.unideb.inf.carinspection.domain.AppUser;
import hu.unideb.inf.carinspection.domain.Car;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader implements CommandLineRunner {

    private final AppUserRepository appUserRepository;

    private final CarRepository carRepository;

    public TestDataLoader(AppUserRepository appUserRepository, CarRepository carRepository) {this.appUserRepository = appUserRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        appUserRepository.save(AppUser.builder().username("alice").password("123").build());
        carRepository.save(Car.builder().owner(null).expirationDate(null).vin("01234567899876543").plateNumber("DEF-456").build());
    }
}
