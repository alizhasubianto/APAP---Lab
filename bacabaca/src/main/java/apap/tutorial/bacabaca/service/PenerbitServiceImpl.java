package apap.tutorial.bacabaca.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import apap.tutorial.bacabaca.DTO.BukuMapper;
import apap.tutorial.bacabaca.repository.BukuDb;
import apap.tutorial.bacabaca.repository.PenerbitDb;
import apap.tutorial.bacabaca.repository.UserDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tutorial.bacabaca.model.Buku;
import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.model.UserModel;

@Service
public class PenerbitServiceImpl implements PenerbitService{
    @Autowired
    PenerbitDb penerbitDb;

    @Autowired
    UserDb userDb;

    @Override
    public List<Penerbit> getAllPenerbit(){ return penerbitDb.findAll();}

    @Override
    public Penerbit getPenerbitById(Long idPenerbit){
        return penerbitDb.findById(idPenerbit).get();
    }

    @Override
    public Penerbit createPenerbit(Penerbit penerbit) {
        // TODO Auto-generated method stub
        return penerbitDb.save(penerbit);
    }

    @Override
    public Map<String, Integer> getPublisherBookCounts(){
        List<Penerbit> listPenerbit = getAllPenerbit();
        Map<String, Integer> publisherBookCounts = new HashMap<>();

        for (Penerbit penerbit : listPenerbit){
            String publisherName = penerbit.getNamaPenerbit();
            int bookCount = penerbit.getListBuku().size();

            publisherBookCounts.put(publisherName, bookCount);
        }
        
        return publisherBookCounts;
    }
    
}

