package apap.tutorial.bacabaca.service;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
//import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import apap.tutorial.bacabaca.repository.BukuDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import apap.tutorial.bacabaca.DTO.response.PopularBookResponseDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;


@Service
public class BukuServiceImpl implements BukuService {
    @Autowired
    BukuDb bukuDb;

    @Override
    public void saveBuku(Buku buku){
        bukuDb.save(buku);
    }

    @Override
    public List<Buku> getAllBuku() { 
        return bukuDb.findAll();
    }

    @Override
    public Buku getBukuById(UUID kodeBuku){
        for (Buku buku: getAllBuku()){
            if (buku.getId().equals(kodeBuku)){
                return buku;
            }
        }
        return null;
    }

    @Override
    public Buku updateBuku(Buku bukuFromDto){
        Buku buku = getBukuById(bukuFromDto.getId());
        if (buku != null){
            buku.setHarga((bukuFromDto.getHarga()));
            buku.setJudul((bukuFromDto.getJudul()));
            buku.setListPenulis((bukuFromDto.getListPenulis()));
            buku.setTahunTerbit((bukuFromDto.getTahunTerbit()));
            buku.setPenerbit((bukuFromDto.getPenerbit()));
            bukuDb.save(buku);
        }
        return buku;
    }

    @Override
    public boolean isJudulExist(String judul){
        return getAllBuku().stream().anyMatch(b -> b.getJudul().equals(judul));
    }

    @Override
    public boolean isJudulExist(String judul, UUID id){
        return getAllBuku().stream().anyMatch(b -> b.getJudul().equals(judul) && !b.getId().equals(id));
    }

    // Nomor 1
    @Override
    public void deletedBuku(Buku buku){
        buku.setDeleted(true);
        bukuDb.save(buku);
    }

    // Nomor 4
    @Override
    public List<Buku> getAllBukuOrderByJudulAsc(){
        List<Buku> listBuku = bukuDb.findByOrderByJudulAsc();
        
        listBuku.sort((buku1, buku2) -> buku1.getJudul().compareToIgnoreCase(buku2.getJudul()));

        return listBuku;
    }

    // Nomor 3
    @Override
    public List<Buku> searchBukuByJudul(String keyword){
        return bukuDb.findByJudulContainingIgnoreCase(keyword);
    }

    @Override
    public Map<String, BigDecimal> getBukuDanHarga(){
        List<Buku> listBuku = bukuDb.findByIsDeleted(false);
        Map<String, BigDecimal> bukudanharga = new HashMap<>();

        for (Buku buku : listBuku){
            String judulBuku = buku.getJudul();
            BigDecimal hargaBuku = buku.getHarga();

            bukudanharga.put(judulBuku, hargaBuku);
        }
        
        return bukudanharga;

}

    @Override
    public Map<String, Long> getPopularBooks(){
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        LocalDate currentDate = LocalDate.now();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/x-www-form-urlencoded");
        headers.set("X-RapidAPI-Key", "ff855857afmshaa24f7e623a2facp176547jsn768ac5acc5a8"); // masukan key api
        headers.set("X-RapidAPI-Host", "hapi-books.p.rapidapi.com"); // masukan host api

        // Create an HttpEntity with the custom headers
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromUriString("https://hapi-books.p.rapidapi.com/month/" + String.valueOf(currentDate.getYear()) + "/" + String.valueOf(currentDate.getMonthValue()))
                .build()
                .toUri();

        ResponseEntity<PopularBookResponseDTO[]> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                PopularBookResponseDTO[].class
        );

        PopularBookResponseDTO[] popularBooksArray = responseEntity.getBody();

        Map<String, Long> ratingPopularBooks = new HashMap<>();

        for (PopularBookResponseDTO book : popularBooksArray) {
            String bookName = book.getName();
            Long bookRating = book.getRating();

            ratingPopularBooks.put(bookName, bookRating);
        }

        return ratingPopularBooks;
    }
}


