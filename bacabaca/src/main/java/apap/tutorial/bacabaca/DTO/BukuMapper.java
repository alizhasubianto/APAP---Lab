package apap.tutorial.bacabaca.DTO;

import apap.tutorial.bacabaca.DTO.request.CreateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.request.UpdateBukuRequestDTO;
import apap.tutorial.bacabaca.DTO.response.ReadBukuResponseDTO;
import apap.tutorial.bacabaca.model.Buku;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BukuMapper {
    Buku createBukuRequestDTOToBuku(CreateBukuRequestDTO createBukuRequestDTO);
    Buku updateBukuRequestDTOToBuku(UpdateBukuRequestDTO updateBukuRequestDTO);

    UpdateBukuRequestDTO bukuToUpdateBukuRequestDTO(Buku buku);
    ReadBukuResponseDTO bukuToReadBukuResponseDTO(Buku buku);

    @AfterMapping
    default void setNamaPenerbit(@MappingTarget ReadBukuResponseDTO responseDTO, Buku buku) {
        responseDTO.setNamaPenerbit(buku.getPenerbit().getNamaPenerbit());
    }
}
