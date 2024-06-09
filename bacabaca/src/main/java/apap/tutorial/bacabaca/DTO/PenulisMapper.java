package apap.tutorial.bacabaca.DTO;

import apap.tutorial.bacabaca.DTO.request.CreatePenulisRequestDTO;
import apap.tutorial.bacabaca.model.Penulis;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PenulisMapper {
    Penulis createPenulisRequestDTOToPenulis(CreatePenulisRequestDTO createPenulisRequestDTO);
}
