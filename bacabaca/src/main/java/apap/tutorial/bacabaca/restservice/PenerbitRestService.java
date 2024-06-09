package apap.tutorial.bacabaca.restservice;
import java.util.List;

import apap.tutorial.bacabaca.DTO.request.UpdatePenerbitRequestDTO;
import apap.tutorial.bacabaca.model.Penerbit;

public interface PenerbitRestService{
	List<Penerbit> retrieveRestAllPenerbit();

	Penerbit createRestPenerbit(Penerbit penerbit);

	Penerbit getRestPenerbitById(long idPenerbit);

	Penerbit updateRestPenerbit(long idPenerbit, UpdatePenerbitRequestDTO updatePenerbitRequestDTO);

	void deletedRestPenerbit(Penerbit penerbit);
	
}

