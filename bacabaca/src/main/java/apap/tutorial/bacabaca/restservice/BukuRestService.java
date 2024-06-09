package apap.tutorial.bacabaca.restservice;

import java.util.List;
import java.util.UUID;

import apap.tutorial.bacabaca.DTO.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.request.UpdatePenerbitRequestDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.model.Sertifikasi;
import apap.tutorial.bacabaca.rest.BukuDetail;
// import apap.tutorial.bacabaca.rest.TopBooks;
// import apap.tutorial.bacabaca.rest.TopBooksDetail;
//import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BukuRestService{
	void createRestBuku(Buku buku);

	List<Buku> retrieveRestAllBuku();

	Buku getRestBukuById(UUID id);

    Mono<String> getStatus();
    Mono<BukuDetail> postStatus();

	void updateRestBuku(Buku buku);

	// Mono<String> translateBookTitle(UUID id, String sourceLanguage, String targetLanguage);
	// Flux<TopBooksDetail> getTopBooks(String year, String month);

	List<Buku> getBukuByHurufAwal(String prefix);
    List<Sertifikasi> getBukuSerifikasi(UUID idBuku);

	List<Buku> searchBukuByJudul(String query);
	List<Buku> getAllBukuOrderByJudulAsc();

	//Buku updateBukuRest(Buku buku, UpdateBukuRequestDTO updateBukuRequestDTO);

	
}
