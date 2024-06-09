package apap.tutorial.bacabaca.service;

import apap.tutorial.bacabaca.DTO.request.CreateUserRequestDTO;
import apap.tutorial.bacabaca.DTO.request.LoginJwtRequestDTO;
import apap.tutorial.bacabaca.model.UserModel;

public interface UserService {
    
    UserModel addUser(UserModel user, CreateUserRequestDTO createUserRequestDTO);

    String encrypt(String password);

    String loginJwtAdmin(LoginJwtRequestDTO loginJwtRequestDTO);

    boolean hasRole(String username, String roleName);
}
