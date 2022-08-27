package hu.unideb.inf.carinspection;

import hu.unideb.inf.carinspection.data.*;
import hu.unideb.inf.carinspection.domain.*;
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

    private final GroupRepository groupRepository;

    private final InspectorRepository inspectorRepository;

    public TestDataLoader(AppUserRepository appUserRepository, CarRepository carRepository, InspectionRepository inspectionRepository, PasswordEncoder passwordEncoder, SiteRepository siteRepository, GroupRepository groupRepository, InspectorRepository inspectorRepository) {
        this.appUserRepository = appUserRepository;
        this.carRepository = carRepository;
        this.inspectionRepository = inspectionRepository;
        this.passwordEncoder = passwordEncoder;
        this.siteRepository = siteRepository;
        this.groupRepository = groupRepository;
        this.inspectorRepository = inspectorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        groupRepository.save(Group.builder().groupName("customer").build());
        groupRepository.save(Group.builder().groupName("admin").build());
        Site defaultSite = siteRepository.save(Site.builder().name("No Site").build());
        Inspector defaultInspector = inspectorRepository.save(Inspector.builder().firstName("No Inspector").build());

        appUserRepository.save(AppUser.builder().username("alice").password(passwordEncoder.encode("123")).firstname("Alice").lastname("Smith").email("alicemail@gmail.com").group(groupRepository.findByGroupName("admin")).build());
        carRepository.save(Car.builder().owner(appUserRepository.findByUsername("alice")).expirationDate(null).vin("01234567899876543").plateNumber("DEF-456").build());
        Car car = carRepository.findAll().get(0);
        inspectionRepository.save(Inspection.builder().car(car).inspector(defaultInspector).site(defaultSite).result("PENDING").build());
        //inspectionRepository.save(Inspection.builder().car(null).inspector(null).site(null).build());
        siteRepository.save(Site.builder().name("Debrecen BartokBela u").build());
        siteRepository.save(Site.builder().name("Budapest Hungária k.u").build());
        siteRepository.save(Site.builder().name("A 67-es út").build());
        inspectorRepository.save(Inspector.builder().firstName("Gábor").lastName("Halász").build());
        inspectorRepository.save(Inspector.builder().firstName("Árpád").lastName("Tóth").build());
    }
}
