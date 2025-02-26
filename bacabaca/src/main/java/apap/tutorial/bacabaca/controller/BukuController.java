package apap.tutorial.bacabaca.controller;
import apap.tutorial.bacabaca.DTO.BukuMapper;
import apap.tutorial.bacabaca.DTO.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.response.ReadBukuResponseDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penulis;
// import apap.tutorial.bacabaca.rest.TopBooks;
// import apap.tutorial.bacabaca.rest.TopBooksDetail;
import apap.tutorial.bacabaca.restservice.BukuRestService;
import apap.tutorial.bacabaca.service.BukuService;
import apap.tutorial.bacabaca.service.BukuServiceImpl;
import apap.tutorial.bacabaca.service.PenerbitService;
import apap.tutorial.bacabaca.service.PenulisService;
import apap.tutorial.bacabaca.service.UserService;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class BukuController {
    
    @Autowired
    private BukuMapper bukuMapper;

    @Autowired
    private BukuService bukuService;

    @Autowired
    private PenerbitService penerbitService;

    @Autowired
    private PenulisService penulisService;

    @Autowired
    private BukuRestService bukuRestService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("activePage", "home");
        return "home";
    }

    @GetMapping("buku/create")
    public String formAddBuku(Model model, Principal principal){
        String username = principal.getName();
        // Membuat DTO baru sebagai isian form pengguna
        if (userService.hasRole(username, "Pustakawan")) {
            var bukuDTO = new CreateBukuRequestDTO();
            model.addAttribute("bukuDTO", bukuDTO);
            
            var listPenerbit = penerbitService.getAllPenerbit();
            model.addAttribute("listPenerbit", listPenerbit);

            var listPenulisExisting = penulisService.getAllPenulis();
            model.addAttribute("listPenulisExisting", listPenulisExisting);

            return "form-create-buku";
        } else {
            return "error-page"; 
        }
    }

    @PostMapping("buku/create")
    public String addBuku(@Valid @ModelAttribute CreateBukuRequestDTO bukuDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            // Mendapatkan pesan error untuk masing-masing field yang tidak valid
            String judulError = bindingResult.getFieldError("judul") != null ? bindingResult.getFieldError("judul").getDefaultMessage() : null;
            String tahunTerbitError = bindingResult.getFieldError("tahunTerbit") != null ? bindingResult.getFieldError("tahunTerbit").getDefaultMessage() : null;
            String hargaError = bindingResult.getFieldError("harga") != null ? bindingResult.getFieldError("harga").getDefaultMessage() : null;
            String penerbitError = bindingResult.getFieldError("penerbit") != null ? bindingResult.getFieldError("penerbit").getDefaultMessage() : null;
            
            // Menambahkan pesan-pesan error ke objek Model
            model.addAttribute("judulError", judulError);
            model.addAttribute("tahunTerbitError", tahunTerbitError);
            model.addAttribute("hargaError", hargaError);
            model.addAttribute("penerbitError", penerbitError);
        
        return "error-view-valid";
        }

        if(bukuService.isJudulExist(bukuDTO.getJudul())){
            var errorMessage = "Maaf, judul buku sudah ada";
            model.addAttribute("errorMessage", errorMessage);
            return "failed-create-update-buku";
        }
       
        var buku = bukuMapper.createBukuRequestDTOToBuku(bukuDTO);

        // Memanggil Service addBuku
        bukuService.saveBuku(buku);

        // Add variabel id buku ke 'id untuk dirender di thmeleaf'
        model.addAttribute("id", buku.getId());

        // Add variabel judul ke 'judul' untuk dirender di thymeleaf
        model.addAttribute("judul", buku.getJudul());

        return "success-create-buku";

    }

    @GetMapping("buku/viewall")
    public String listBuku(Model model){
        //Mendapatkan semua buku
        List<Buku> listBuku = bukuService.getAllBukuOrderByJudulAsc();
        // for (Buku buku : listBuku) {
        //     System.out.println(buku.getJudul());
        // }

        model.addAttribute("activePage", "buku");
        //Add variabel semua bukuModel ke "ListBuku" untuk dirender pada thymelaf
        model.addAttribute("listBuku", listBuku);
        return "viewall-buku";
    }

    @GetMapping("buku/viewall-with-datatables")
    public String listBukuWithDatatables(Model model){
        //Mendapatkan semua buku
        List<Buku> listBuku = bukuService.getAllBukuOrderByJudulAsc();
        // for (Buku buku : listBuku) {
        //     System.out.println(buku.getJudul());
        // }

        model.addAttribute("activePage", "buku");
        //Add variabel semua bukuModel ke "ListBuku" untuk dirender pada thymelaf
        model.addAttribute("listBuku", listBuku);
        return "viewall-with-datatables";
    }

    @GetMapping("/buku/{id}")
    public String detailBuku(@PathVariable("id") UUID id, Model model){
        //Mendapatkan buku melalui kodeBuku
        var buku = bukuService.getBukuById(id);
        
        ReadBukuResponseDTO bukuDTO = bukuMapper.bukuToReadBukuResponseDTO(buku);
        model.addAttribute("buku", bukuDTO);
        return "view-buku";
    }

    @GetMapping("/buku/{id}/update")
    public String formUpdateBuku(@PathVariable("id") UUID id, Model model, Principal principal){
        String username = principal.getName();
        if (userService.hasRole(username, "Pustakawan")) {
        // Mengambil buku dengan id tersebut
            var buku = bukuService.getBukuById(id);

            // Memindahkan data ke buku ke DTO untuk selanjutnya diubah di form pengguna
            var bukuDTO = bukuMapper.bukuToUpdateBukuRequestDTO(buku);

            // Kirim list penulis untuk menjadi pilihan pada dropdown.
            var listPenulisExisting = penulisService.getAllPenulis();
            model.addAttribute("listPenulisExisting", listPenulisExisting);

            model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());
            model.addAttribute("bukuDTO", bukuDTO);

            return "form-update-buku";
        } else {
            // Jika pengguna bukan Pustakawan
            return "error-page";
        }
    }

    @PostMapping("buku/update")
    public String updateBuku(@Valid @ModelAttribute UpdateBukuRequestDTO bukuDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            // Mendapatkan pesan error untuk masing-masing field yang tidak valid
            String judulError = bindingResult.getFieldError("judul") != null ? bindingResult.getFieldError("judul").getDefaultMessage() : null;
            String tahunTerbitError = bindingResult.getFieldError("tahunTerbit") != null ? bindingResult.getFieldError("tahunTerbit").getDefaultMessage() : null;
            String hargaError = bindingResult.getFieldError("harga") != null ? bindingResult.getFieldError("harga").getDefaultMessage() : null;
            String penerbitError = bindingResult.getFieldError("penerbit") != null ? bindingResult.getFieldError("penerbit").getDefaultMessage() : null;
            
            // Menambahkan pesan-pesan error ke objek Model
            model.addAttribute("judulError", judulError);
            model.addAttribute("tahunTerbitError", tahunTerbitError);
            model.addAttribute("hargaError", hargaError);
            model.addAttribute("penerbitError", penerbitError);
        
            return "error-view-valid";
        }

        if (bukuService.isJudulExist(bukuDTO.getJudul(), bukuDTO.getId())){
            var errorMessage = "Maaf, judul buku sudah ada";
            model.addAttribute("errorMessage", errorMessage);
            return "failed-create-update-buku";
        }

        var bukuFromDto = bukuMapper.updateBukuRequestDTOToBuku(bukuDTO);

        //Memanggil service addBuku
        var buku = bukuService.updateBuku(bukuFromDto);

        // Add variabel kode buku ke 'kode' untuk dirender di thymeleaf
        model.addAttribute("id", buku.getId());

        // Add variabel judul ke 'judul' untuk dirender di thymeleaf
        model.addAttribute("judul", buku.getJudul());

        return "success-update-buku";
    }

    @GetMapping("/buku/{id}/delete")
    public String deleteBuku(@PathVariable("id") UUID id, Model model){
       var buku = bukuService.getBukuById(id);
       //bukuService.deletedBuku(buku);
       bukuService.deletedBuku(buku);

       model.addAttribute("id", id);
       
       return "success-delete-buku";
    }

    @GetMapping("/buku/search")
    public String searchBukuByJudul(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
    List<Buku> listBuku;

    if (keyword != null && !keyword.isEmpty()) {
        // Jika kata kunci pencarian tidak kosong, cari berdasarkan judul
        listBuku = bukuService.searchBukuByJudul(keyword);
    } else {
        // Jika kata kunci pencarian kosong, tampilkan semua buku
        listBuku = bukuService.getAllBukuOrderByJudulAsc();
    }

    model.addAttribute("listBuku", listBuku);
    return "viewall-with-datatables";
}

    @PostMapping(value = "buku/create", params = {"addRow"})
    public String addRowPenulisBuku(
        @ModelAttribute CreateBukuRequestDTO createBukuRequestDTO,
        Model model
    ){
        if(createBukuRequestDTO.getListPenulis() == null || createBukuRequestDTO.getListPenulis().size() == 0){
            createBukuRequestDTO.setListPenulis(new ArrayList<>());
        }
    
    // Memasukkan Penulis baru (kosong) ke list, untuk dirender sebagai row baru.
    createBukuRequestDTO.getListPenulis().add(new Penulis());

    // Kirim list penerbit penulis untuk menjadi pilihan pada dropdown.
    model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
    model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());

    model.addAttribute("bukuDTO", createBukuRequestDTO);
    return "form-create-buku";
    }

    @PostMapping(value="buku/create", params = {"deleteRow"})
    public String deleteRowPenulisBuku(
        @ModelAttribute CreateBukuRequestDTO createBukuRequestDTO,
        @RequestParam("deleteRow") int row,
        Model model
    ){
        createBukuRequestDTO.getListPenulis().remove(row);
        model.addAttribute("bukuDTO", createBukuRequestDTO);
        model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
        model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());
        return "form-create-buku";
    }

    @PostMapping(value = "buku/update", params = {"addRow"})
    public String addRowPenulisBukuUpdate(
        @ModelAttribute UpdateBukuRequestDTO updateBukuRequestDTO,
        Model model
    ){
        if(updateBukuRequestDTO.getListPenulis() == null || updateBukuRequestDTO.getListPenulis().size() == 0){
            updateBukuRequestDTO.setListPenulis(new ArrayList<>());
        }
    
    // Memasukkan Penulis baru (kosong) ke list, untuk dirender sebagai row baru.
    updateBukuRequestDTO.getListPenulis().add(new Penulis());

    // Kirim list penerbit penulis untuk menjadi pilihan pada dropdown.
    model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
    model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());

    model.addAttribute("bukuDTO", updateBukuRequestDTO);
    return "form-update-buku";
    }

    @PostMapping(value="buku/update", params = {"deleteRow"})
    public String deleteRowPenulisBukuUpdate(
        @ModelAttribute UpdateBukuRequestDTO updateBukuRequestDTO,
        @RequestParam("deleteRow") int row,
        Model model
    ){
        updateBukuRequestDTO.getListPenulis().remove(row);
        model.addAttribute("bukuDTO", updateBukuRequestDTO);
        model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
        model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());
        return "form-update-buku";
    }

    // @GetMapping("buku/chart")
    // public String chartBuku(Model model){
    //     // Menggunakan tanggal bulan dan tahun saat ini
    //     LocalDate date = LocalDate.now();
    //     int month = date.getMonthValue();
    //     int year = date.getYear();

    //     // Mengonversi month dan year ke dalam String
    //     String monthString = String.valueOf(month);
    //     String yearString = String.valueOf(year);

    //     // Memanggil metode untuk mendapatkan daftar buku populer
    //     Flux<TopBooksDetail> topBooksFlux = bukuRestService.getTopBooks(monthString, yearString);

    //     // Mengumpulkan elemen-elemen dari Flux ke dalam List
    //     List<TopBooksDetail> topBooks = topBooksFlux.collectList().block();

    //     // Logika tambahan untuk memproses data
    //     Map<String, Float> simplifiedPopularBooks = new HashMap<>();
    //     for (TopBooksDetail book : topBooks) {
    //         simplifiedPopularBooks.put(book.getJudul(), book.getRating().floatValue()); // Mengonversi rating ke tipe Float
    //     }

    //     // Simpan data yang telah diproses dalam model
    //     model.addAttribute("popularBooks", simplifiedPopularBooks);
        
    //     return "view-penerbit-chart";
    // }


    @GetMapping("buku/chartbukudanharga")
    public String chartBukuDanHarga(Model model){
        var listBuku = bukuService.getBukuDanHarga();
        model.addAttribute("listBuku", listBuku);
	
	    return "view-bukuharga-chart";
    }

    @GetMapping("buku/chart")
    public String chartBukuPopuler(Model model) {
        Map<String, Long> listBuku = bukuService.getPopularBooks();
        model.addAttribute("listBuku", listBuku);

        return "view-buku-chart";
    }

}
