package apap.tutorial.bacabaca.restservice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tutorial.bacabaca.DTO.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.request.UpdatePenerbitRequestDTO;
//import apap.tutorial.bacabaca.DTO.request.TranslateBukuRequestDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.model.Penulis;
import apap.tutorial.bacabaca.model.Sertifikasi;
import apap.tutorial.bacabaca.repository.BukuDb;
import apap.tutorial.bacabaca.rest.BukuDetail;
// import apap.tutorial.bacabaca.rest.TopBooks;
// import apap.tutorial.bacabaca.rest.TopBooksDetail;
// import apap.tutorial.bacabaca.rest.TranslateDetail;
// import apap.tutorial.bacabaca.rest.TranslateResponse;
import jakarta.transaction.Transactional;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Mono;



@Service
@Transactional
public class BukuRestServiceImpl implements BukuRestService{
    @Autowired
    private BukuDb bukuDb;

    private final WebClient webClient;

    @Override
    public void createRestBuku(Buku buku) {bukuDb.save(buku);};

    @Override
    public void updateRestBuku(Buku buku){
        bukuDb.save(buku);
    }

    @Override
    public List<Buku> retrieveRestAllBuku(){ return bukuDb.findByOrderByJudulAsc(); };

    @Override
    public Buku getRestBukuById(UUID id){
        for (Buku buku : retrieveRestAllBuku()){
            if(buku.getId().equals(id)){
                return buku;
            }
        }
        return null;
    };

    private final String mockUrl = "https://dd8eab8a-0044-4b34-9163-f84f04aca765.mock.pstmn.io";

    // private final String rapidApiBaseUrl = "text-translator2.p.rapidapi.com";

    // private final String rapidApiKey = "ff855857afmshaa24f7e623a2facp176547jsn768ac5acc5a8";

    // private final String rapidApiUrlTopBooks = "https://hapi-books.p.rapidapi.com/month/";



    // WebClient translateWebClient;

    public BukuRestServiceImpl(WebClient.Builder webClientBuilder){
	    this.webClient = webClientBuilder.baseUrl(mockUrl).build(); // mock server
    }

    @Override
    public Mono<String> getStatus(){
    return this.webClient.get().uri("/rest/buku/status").retrieve().bodyToMono(String.class);
    };

    @Override
    public Mono<BukuDetail> postStatus(){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("judul", "Buku Cara Membaca Jilid 2");
        data.add("tahunTerbit", "2003");
        var response = this.webClient
            .post()
            .uri("/rest/buku/full-status")
            .bodyValue(data)
            .retrieve()
            .bodyToMono(BukuDetail.class);
        return response;
    }

    
    @Override
    public List<Buku> getBukuByHurufAwal(String prefix){
        List<Buku> bukuWithPrefix = bukuDb.findByJudulStartsWith(prefix);
        return bukuWithPrefix;
    }

    @Override
    public List<Sertifikasi> getBukuSerifikasi(UUID idBuku){
        Buku buku = getRestBukuById(idBuku);
        List<Penulis> listPenulis = buku.getListPenulis();
        List<Sertifikasi> listSertifikasi = new ArrayList<>();
        for (Penulis penulis : listPenulis) {
            listSertifikasi.addAll(penulis.getListSertifikasi());
        }
        return listSertifikasi;
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


}


