package apap.tutorial.bacabaca.service;
import apap.tutorial.bacabaca.model.Buku;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;


@Service
public interface BukuService {
    // Method untuk menambahkan
    void saveBuku(Buku buku);

    // Method untuk mendapatkan buku yang telah tersimpan
    List<Buku> getAllBuku();

    // Method untuk mendapatkan data buku berdasarkan kode buku
    Buku getBukuById(UUID id);

    boolean isJudulExist(String judul);
    
    boolean isJudulExist(String judul, UUID id);

    Buku updateBuku(Buku buku);
   
    // Nomor 1
    void deletedBuku(Buku buku);

    // Nomor 4
    List<Buku> getAllBukuOrderByJudulAsc();

    //Nomor 3
    List<Buku> searchBukuByJudul(String keyword);

    //void deletedBuku(UUID id);

    //List<Buku> findByPenerbitIdAndIsDeleted(Long idPenerbit, boolean b);

    Map<String, BigDecimal> getBukuDanHarga();

    Map<String, Long> getPopularBooks();
}
