package apap.tutorial.bacabaca.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import apap.tutorial.bacabaca.DTO.PenulisMapper;
import apap.tutorial.bacabaca.DTO.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.request.CreatePenulisRequestDTO;
import apap.tutorial.bacabaca.DTO.response.DeleteMultiplePenulisDTO;
import apap.tutorial.bacabaca.model.Penulis;
import apap.tutorial.bacabaca.model.Sertifikasi;
import apap.tutorial.bacabaca.repository.PenulisDb;
import apap.tutorial.bacabaca.repository.SertifikasiDb;
import apap.tutorial.bacabaca.service.PenulisService;

@Controller
public class PenulisController {
    @Autowired
    PenulisService penulisService;

    @Autowired
    PenulisMapper penulisMapper;

    @Autowired
    SertifikasiDb sertifikasiDb;

    @GetMapping("penulis/create")
    public String formAddPenulis(Model model){
        // Membuat DTO untuk dikirimkan ke view.
        var penulisDTO = new CreatePenulisRequestDTO();

        // Kirim list penulis untuk menjadi pilihan pada dropdown.
        //var listSertifikatExisting = sertifikasiDb.getAllPenulis();
        //model.addAttribute("listPenulisExisting", listPenulisExisting);

        model.addAttribute("penulisDTO", penulisDTO);
        
        return "form-create-penulis";
    }

    @PostMapping("penulis/create")
    public String addPenulis(
        @ModelAttribute CreatePenulisRequestDTO createPenulisRequestDTO,
        Model model
    ) {
        // Dengan bantuan mapper, buat object Penulis dengan data yang berasal dari DTO.
        var penulis = penulisMapper.createPenulisRequestDTOToPenulis(createPenulisRequestDTO);

        for(Sertifikasi sertifikasi: penulis.getListSertifikasi()){
            sertifikasi.setPenulis(penulis);
            sertifikasiDb.save(penulis);
        }
        
        penulisService.createPenulis(penulis);
        // sertifikasiDb.save(penulis);

        model.addAttribute("penulis", createPenulisRequestDTO);
        return "success-create-penulis";
    }

    @GetMapping("penulis/viewall")
    public String listPenulis(Model model){
        var listPenulis = penulisService.getAllPenulis();
        var deleteDTO = new DeleteMultiplePenulisDTO();

        model.addAttribute("activePage", "penulis");
        model.addAttribute("listPenulis", listPenulis);
        model.addAttribute("deleteDTO", deleteDTO);

        return "viewall-penulis";
    }

    @PostMapping("penulis/delete")
    public String DeleteMultiplePenulis(
        @ModelAttribute DeleteMultiplePenulisDTO deleteDTO
    ){
        penulisService.deleteListPenulis(deleteDTO.getListPenulis());
        return "success-delete-penulis";
    }

    @PostMapping(value = "penulis/create", params = {"addRow"})
    public String addRowSertifikasi(
        @ModelAttribute CreatePenulisRequestDTO createPenulisRequestDTO,
        Model model
    ){
        if(createPenulisRequestDTO.getListSertifikasi() == null || createPenulisRequestDTO.getListSertifikasi().size() == 0){
            createPenulisRequestDTO.setListSertifikasi(new ArrayList<>());
        }
    
    // Memasukkan Penulis baru (kosong) ke list, untuk dirender sebagai row baru.
    createPenulisRequestDTO.getListSertifikasi().add(new Sertifikasi());

    // // Kirim list penerbit penulis untuk menjadi pilihan pada dropdown.
    // model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
    // model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());

    model.addAttribute("penulisDTO", createPenulisRequestDTO);
    return "form-create-penulis";
    }

    @PostMapping(value="penulis/create", params = {"deleteRow"})
    public String deleteRowSertifikasi(
        @ModelAttribute CreatePenulisRequestDTO createPenulisRequestDTO,
        @RequestParam("deleteRow") int row,
        Model model
    ){
        createPenulisRequestDTO.getListSertifikasi().remove(row);
        model.addAttribute("penulisDTO", createPenulisRequestDTO);
        // model.addAttribute("listPenulisExisting", penulisService.getAllPenulis());
        // model.addAttribute("listPenerbit", penerbitService.getAllPenerbit());
        return "form-create-penulis";
    }


    
}
