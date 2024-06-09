package apap.tutorial.bacabaca;

import apap.tutorial.bacabaca.DTO.BukuMapper;
import apap.tutorial.bacabaca.DTO.PenerbitMapper;
import apap.tutorial.bacabaca.DTO.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.request.CreatePenerbitRequestDTO;
import apap.tutorial.bacabaca.model.Role;
import apap.tutorial.bacabaca.repository.RoleDb;
import apap.tutorial.bacabaca.service.BukuService;
import apap.tutorial.bacabaca.service.PenerbitService;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BacabacaApplication {

    @Autowired
    private RoleDb roleDb;

    public static void main(String[] args) {
        SpringApplication.run(BacabacaApplication.class, args);
    }

    // Inisialisasi role "Admin" dan "User"
    private void initializeRoles() {
        Role adminRole = new Role();
        adminRole.setRole("Admin");
        roleDb.save(adminRole);

        Role userRole = new Role();
        userRole.setRole("User");
        roleDb.save(userRole);

        Role pustakawanRole = new Role();
        pustakawanRole.setRole("Pustakawan");
        roleDb.save(pustakawanRole);
    }

    // CommandLineRunner digunakan untuk execute code saat spring pertama kali start up
    @Bean
    @Transactional
    CommandLineRunner run(BukuService bukuService, PenerbitService penerbitService, BukuMapper bukuMapper, PenerbitMapper penerbitMapper) {
        return args -> {
            initializeRoles();

            var faker = new Faker(new Locale("in-ID"));

            for (int i = 0; i < 1000; i++) {
                CreatePenerbitRequestDTO penerbitDTO = new CreatePenerbitRequestDTO();
                penerbitDTO.setNamaPenerbit(faker.book().publisher());
                penerbitDTO.setEmail(faker.internet().emailAddress());
                penerbitDTO.setAlamat(faker.address().fullAddress());

                // Mapping penerbitDTO ke buku lalu save penerbit ke database
                var penerbit = penerbitMapper.createPenerbitRequestDTOToPenerbit(penerbitDTO);
                penerbit = penerbitService.createPenerbit(penerbit);

                int numberOfBooks = faker.number().numberBetween(1, 2);
                for (int j = 0; j < numberOfBooks; j++) {
                    // Membuat fake data memanfaatkan Java Faker
                    CreateBukuRequestDTO bukuDTO = new CreateBukuRequestDTO();

                    bukuDTO.setHarga(new BigDecimal(Math.random() * 1000000));
                    bukuDTO.setJudul(faker.book().title());
                    bukuDTO.setTahunTerbit(String.valueOf(faker.date().past(36500, TimeUnit.DAYS).getYear() + 1900));
                    // Mapping bukuDTO ke buku lalu save buku ke database
                    var buku = bukuMapper.createBukuRequestDTOToBuku(bukuDTO);
                    buku.setPenerbit(penerbit);
                    bukuService.saveBuku(buku);
                }
            }
        };
    }
}
