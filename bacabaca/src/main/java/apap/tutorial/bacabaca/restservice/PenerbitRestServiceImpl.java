package apap.tutorial.bacabaca.restservice;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tutorial.bacabaca.DTO.request.UpdatePenerbitRequestDTO;

import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.repository.PenerbitDb;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PenerbitRestServiceImpl implements PenerbitRestService{

	@Autowired
	private PenerbitDb penerbitDb;

	@Override
	public List<Penerbit> retrieveRestAllPenerbit(){ return penerbitDb.findAll(); }

	@Override
    public Penerbit createRestPenerbit(Penerbit penerbit) { return penerbitDb.save(penerbit); }

	@Override
    public Penerbit getRestPenerbitById(long idPenerbit){
        for (Penerbit penerbit : retrieveRestAllPenerbit()){
            if (penerbit.getIdPenerbit() == idPenerbit){
                return penerbit;
            }
        }
        return null;
    };

    @Override
    public Penerbit updateRestPenerbit(long idPenerbit, UpdatePenerbitRequestDTO updatePenerbitRequestDTO){
        Penerbit penerbit = penerbitDb.findByIdPenerbit(idPenerbit);
        
        if(penerbit != null){
            penerbit.setNamaPenerbit(updatePenerbitRequestDTO.getNamaPenerbit());
            penerbit.setAlamat(updatePenerbitRequestDTO.getAlamat());
            penerbit.setEmail(updatePenerbitRequestDTO.getEmail());

            return penerbitDb.save(penerbit);

        } else {
            throw new NoSuchElementException("Penerbit dengan ID " + idPenerbit + " tidak ditemukan");
        }

    }

    @Override
    public void deletedRestPenerbit(Penerbit penerbit){
        penerbit.setDeleted(true);
        penerbitDb.save(penerbit);
    }
}
