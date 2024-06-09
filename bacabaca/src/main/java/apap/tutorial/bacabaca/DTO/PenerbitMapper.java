package apap.tutorial.bacabaca.DTO;

import apap.tutorial.bacabaca.DTO.request.CreatePenerbitRequestDTO;

import apap.tutorial.bacabaca.model.Penerbit;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PenerbitMapper {
    Penerbit createPenerbitRequestDTOToPenerbit(CreatePenerbitRequestDTO createPenerbitRequestDTO);

    // @AfterMapping
    // default void setIsDeleted(@MappingTarget Penerbit penerbit, CreatePenerbitRequestDTO responseDTO) {
    //     penerbit.sDeleted();
    // }
}
