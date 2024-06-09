package apap.tutorial.bacabaca.DTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import apap.tutorial.bacabaca.DTO.request.CreateUserRequestDTO;
import apap.tutorial.bacabaca.DTO.response.CreateUserResponseDTO;
import apap.tutorial.bacabaca.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    UserModel createUserRequestDTOToUserModel(CreateUserRequestDTO createUserRequestDTO);
    
    @Mapping(target = "role", ignore = true)
    CreateUserResponseDTO createUserResponseDTOToUserModel(UserModel userModel);
}
