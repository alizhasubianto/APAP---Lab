package apap.tutorial.bacabaca.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import apap.tutorial.bacabaca.DTO.BukuMapper;
import apap.tutorial.bacabaca.DTO.request.CreateBukuRequestDTO;
//import apap.tutorial.bacabaca.DTO.request.TranslateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.request.TranslateTitleBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.response.TranslateResponseDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.rest.BukuDetail;
//import apap.tutorial.bacabaca.rest.TopBooksDetail;
import apap.tutorial.bacabaca.restservice.BukuRestService;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class BukuRestController{

	@Autowired
	private BukuMapper bukuMapper;
	
	@Autowired
	private BukuRestService bukuRestService;

	private final String mockUrl = "https://dd8eab8a-0044-4b34-9163-f84f04aca765.mock.pstmn.io";

    private final String rapidApiBaseUrl = "text-translator2.p.rapidapi.com";

    private final String rapidApiKey = "ff855857afmshaa24f7e623a2facp176547jsn768ac5acc5a8";

    private final String rapidApiUrlTopBooks = "https://hapi-books.p.rapidapi.com/month/";

   // private final WebClient webClient;

    WebClient translateWebClient;

	@GetMapping(value="/buku/view-all")
	private List<Buku> retrieveAllBuku(){ return bukuRestService.retrieveRestAllBuku(); }

	@GetMapping(value="/buku/{id}")
	private Buku retrieveBuku(@PathVariable("id") String id){
		try {
			return bukuRestService.getRestBukuById(UUID.fromString(id));
		}
		catch (NoSuchElementException e){
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Id Buku " + id + " not found"
            );
		}
	}

	@PostMapping(value="/buku/create")
	public Buku restAddBuku(@Valid @RequestBody CreateBukuRequestDTO bukuDTO, BindingResult bindingResult){
	if(bindingResult.hasFieldErrors()){
		throw new ResponseStatusException(
			HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
		);
	} else{
		var buku = bukuMapper.createBukuRequestDTOToBuku(bukuDTO);
		bukuRestService.createRestBuku(buku);
		return buku;
	}
    }

    @PutMapping(value="/buku")
	public Buku updateBuku(@Valid @RequestBody UpdateBukuRequestDTO updateBukuRequestDTO){
        Buku buku = bukuRestService.getRestBukuById(updateBukuRequestDTO.getId());
        if (buku != null) {
			buku.setJudul(updateBukuRequestDTO.getJudul());
            buku.setTahunTerbit(updateBukuRequestDTO.getTahunTerbit());
            buku.setHarga(updateBukuRequestDTO.getHarga());
			bukuRestService.updateRestBuku(buku);
		}

        return buku;
	}

    @GetMapping("/buku/search")
    public List<Buku> searchBukuRestByJudul(@RequestParam(name = "query", required = false) String query, Model model) {
    List<Buku> listBuku;

    if (query != null && !query.isEmpty()) {
        // Jika kata kunci pencarian tidak kosong, cari berdasarkan judul
        listBuku = bukuRestService.searchBukuByJudul(query);
    } else {
        // Jika kata kunci pencarian kosong, tampilkan semua buku
        listBuku = bukuRestService.getAllBukuOrderByJudulAsc();
    }
    return listBuku;

    }

    @GetMapping("/random")
    public ResponseEntity random(){
        Random random = new Random();
        var theBool = random.nextBoolean();
        if(theBool){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value="/buku/status")
    private Mono<String> getStatus(){
        return bukuRestService.getStatus();
    }

    @PostMapping(value = "/full")
    private Mono<BukuDetail> postStatus(){
        return bukuRestService.postStatus();
    }

	@PostMapping(value = "/buku/translate")
    private Buku translateBookTitle(@Valid @RequestBody TranslateTitleBukuRequestDTO translateDTO, BindingResult bindingResult){
        Buku buku = bukuRestService.getRestBukuById(translateDTO.getBookId());
        if (buku != null) {
			TranslateResponseDTO translatedTitle = getData(translateDTO.getSourceLanguage(), translateDTO.getTargetLanguage(), buku.getJudul());
			buku.setJudul(translatedTitle.getData().getTranslatedText());
			bukuRestService.updateRestBuku(buku);
		}

        return buku;
    }

    private TranslateResponseDTO getData(String source_language, String target_language, String book_title){
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Define your form parameters
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("source_language", source_language);
        formParams.add("target_language",target_language);
        formParams.add("text", book_title);

        // Define your custom headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/x-www-form-urlencoded");
        headers.set("X-RapidAPI-Key", rapidApiKey); // masukan key api
        headers.set("X-RapidAPI-Host", rapidApiBaseUrl); // masukan host api

        // Create an HttpEntity with the custom headers
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formParams, headers);

        ResponseEntity<TranslateResponseDTO> responseEntity = restTemplate.exchange(
                "https://text-translator2.p.rapidapi.com/translate",
                HttpMethod.POST,
                httpEntity,
                TranslateResponseDTO.class
        );
        TranslateResponseDTO responseBody = responseEntity.getBody();
        return responseBody;
    }

}

