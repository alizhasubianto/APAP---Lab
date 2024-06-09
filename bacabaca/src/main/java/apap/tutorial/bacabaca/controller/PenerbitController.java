package apap.tutorial.bacabaca.controller;

import apap.tutorial.bacabaca.DTO.PenerbitMapper;
import apap.tutorial.bacabaca.DTO.request.CreatePenerbitRequestDTO;
import apap.tutorial.bacabaca.model.Role;
import apap.tutorial.bacabaca.service.BukuService;
import apap.tutorial.bacabaca.service.PenerbitService;
import apap.tutorial.bacabaca.service.UserService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PenerbitController {
    @Autowired
    PenerbitService penerbitService;

    @Autowired
    BukuService bukuService;

    @Autowired
    PenerbitMapper penerbitMapper;

    @Autowired
    UserService userService;

    @GetMapping("penerbit/create")
    public String formAddPenerbit(Model model, Principal principal) {
        String username = principal.getName();
        
        if (userService.hasRole(username, "Pustakawan")) {
            var penerbitDTO = new CreatePenerbitRequestDTO();
            model.addAttribute("penerbitDTO", penerbitDTO);

            return "form-create-penerbit";
        } else {
            // Jika pengguna bukan Pustakawan
            return "error-page";
        }
    }



    @PostMapping("penerbit/create")
    public String addPenerbit(@ModelAttribute CreatePenerbitRequestDTO createPenerbitRequestDTO, Model model){
    // Membuat object Penerbit dengan data yang berasal dari DTO
    var penerbit = penerbitMapper.createPenerbitRequestDTOToPenerbit(createPenerbitRequestDTO);

    //Memanggil Service createPenerbit
    penerbit = penerbitService.createPenerbit(penerbit);

    // Menambah penerbit ke model Thymeleaf
    model.addAttribute("penerbit", createPenerbitRequestDTO);

    return "success-create-penerbit";
    }

    @GetMapping("penerbit/viewall")
    public String listPenerbit(Model model){
        // Membuat penerbitDTO baru untuk diisi di form
        var listPenerbit = penerbitService.getAllPenerbit();

        model.addAttribute("activePage", "penerbit");
        //Menambah penerbitDTO ke model Thmeleaf
        model.addAttribute("listPenerbit", listPenerbit);

        return "viewall-penerbit";
    }

    @GetMapping("penerbit/{idPenerbit}")
    public String detailPenerbit(@PathVariable("idPenerbit") Long idPenerbit, Model model){
        //Mendapatkan buku melalui kodeBuku
        var penerbit = penerbitService.getPenerbitById(idPenerbit);

        // Mendapatkan buku yang terhubung dengan penerbit dan belum dihapus (soft delete)
       // List<Buku> bukuList = bukuService.findByPenerbitIdAndIsDeleted(idPenerbit, false);


        model.addAttribute("penerbit", penerbit);
       // model.addAttribute("bukuList", bukuList);

        return "view-penerbit";
    }

    @GetMapping("penerbit/chart")
    public String chartPenerbit(Model model){
        var listPenerbit = penerbitService.getPublisherBookCounts();
        model.addAttribute("listPenerbit", listPenerbit);
	
	    return "view-penerbit-chart";
    }
}

