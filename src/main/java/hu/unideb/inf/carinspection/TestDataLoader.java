package hu.unideb.inf.carinspection;

import hu.unideb.inf.carinspection.data.AppUserRepository;
import hu.unideb.inf.carinspection.data.CarRepository;
import hu.unideb.inf.carinspection.data.InspectionRepository;
import hu.unideb.inf.carinspection.data.SiteRepository;
import hu.unideb.inf.carinspection.domain.AppUser;
import hu.unideb.inf.carinspection.domain.Car;
import hu.unideb.inf.carinspection.domain.Inspection;
import hu.unideb.inf.carinspection.domain.Site;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader implements CommandLineRunner {

    private final AppUserRepository appUserRepository;

    private final CarRepository carRepository;

    private final InspectionRepository inspectionRepository;

    private final PasswordEncoder passwordEncoder;

    private final SiteRepository siteRepository;

    public TestDataLoader(AppUserRepository appUserRepository, CarRepository carRepository, InspectionRepository inspectionRepository, PasswordEncoder passwordEncoder, SiteRepository siteRepository) {
        this.appUserRepository = appUserRepository;
        this.carRepository = carRepository;
        this.inspectionRepository = inspectionRepository;
        this.passwordEncoder = passwordEncoder;
        this.siteRepository = siteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        appUserRepository.save(AppUser.builder().username("alice").password(passwordEncoder.encode("123")).build());
        carRepository.save(Car.builder().owner(appUserRepository.findByUsername("alice")).expirationDate(null).vin("01234567899876543").plateNumber("DEF-456").build());
        Car car = carRepository.findAll().get(0);
        inspectionRepository.save(Inspection.builder().car(car).inspector(null).site(null).build());
        inspectionRepository.save(Inspection.builder().car(null).inspector(null).site(null).build());
        siteRepository.save(Site.builder().name("Debrecen BartokBela u").build());
    }
}
