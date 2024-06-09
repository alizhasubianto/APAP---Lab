package apap.tutorial.bacabaca.repository;
import apap.tutorial.bacabaca.model.Penulis;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SertifikasiDb extends JpaRepository<Penulis, Long> {
    
}
