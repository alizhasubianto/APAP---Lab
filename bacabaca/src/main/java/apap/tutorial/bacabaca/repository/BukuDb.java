package apap.tutorial.bacabaca.repository;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface BukuDb extends JpaRepository<Buku, UUID> {
    // Nomor 1
    List<Buku> findByIsDeleted(boolean isDeleted);

    // Nomor 4
    List<Buku> findByOrderByJudulAsc();

    List<Buku> findByJudulContainingIgnoreCase(String keyword);

    //List<Buku> sortBukuByJudulLower();
    List<Buku> findByJudulStartsWith(String prefix);

   // Buku findByIdBuku(UUID idBuku);
}
