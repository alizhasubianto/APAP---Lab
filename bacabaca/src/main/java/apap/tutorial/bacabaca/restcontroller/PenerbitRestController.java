package apap.tutorial.bacabaca.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import apap.tutorial.bacabaca.DTO.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.request.CreatePenerbitRequestDTO;
import apap.tutorial.bacabaca.DTO.request.UpdatePenerbitRequestDTO;
import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.restservice.PenerbitRestService;
import jakarta.validation.Valid;
import apap.tutorial.bacabaca.DTO.PenerbitMapper;

@RestController
@RequestMapping("/api")
public class PenerbitRestController{

	@Autowired
	private PenerbitRestService penerbitRestService;

	@Autowired
	private PenerbitMapper penerbitMapper;

	@GetMapping(value="/penerbit/view-all")
	private List<Penerbit> retrieveAllPenerbit(){ return penerbitRestService.retrieveRestAllPenerbit();}

	@GetMapping(value="/penerbit/{idPenerbit}")
	private Penerbit retrievePenerbit(@PathVariable("idPenerbit") Long idPenerbit){
		try {
			return penerbitRestService.getRestPenerbitById(idPenerbit);
		}
		catch (NoSuchElementException e){
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Id Buku " + idPenerbit + " not found"
            );
		}
	}

	@PostMapping(value="/penerbit")
	public Penerbit restAddPenerbit(@Valid @RequestBody CreatePenerbitRequestDTO penerbitDTO, BindingResult bindingResult){
	if(bindingResult.hasFieldErrors()){
		throw new ResponseStatusException(
			HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
		);
	} else{
		var penerbit = penerbitMapper.createPenerbitRequestDTOToPenerbit(penerbitDTO);
		penerbitRestService.createRestPenerbit(penerbit);
		return penerbit;
	}

	}

	@PutMapping(value="/penerbit/{idPenerbit}")
	public Penerbit updatePenerbit(@PathVariable("idPenerbit") Long idPenerbit, @RequestBody UpdatePenerbitRequestDTO updatePenerbitRequestDTO){
		try {
			return penerbitRestService.updateRestPenerbit(idPenerbit, updatePenerbitRequestDTO);
		}
		catch (NoSuchElementException e){
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Id Penerbit " + idPenerbit + " not found"
            );
		}
	}

	@DeleteMapping(value="/penerbit/{idPenerbit}")
	public void deletePenerbit(@PathVariable("idPenerbit") Long idPenerbit){
		try{
			var penerbit = penerbitRestService.getRestPenerbitById(idPenerbit);
       		penerbitRestService.deletedRestPenerbit(penerbit);
		}
		catch (NoSuchElementException e){
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Id Penerbit " + idPenerbit + " not found"
            );
		}
	}

}

