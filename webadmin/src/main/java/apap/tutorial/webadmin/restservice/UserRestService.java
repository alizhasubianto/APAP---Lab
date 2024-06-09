package apap.tutorial.webadmin.restservice;

import apap.tutorial.webadmin.DTO.UserRequestDTO;
import apap.tutorial.webadmin.DTO.UserResponseDTO;

public interface UserRestService {
    
    UserResponseDTO sendUser(UserRequestDTO userDTO, String jwtToken);

    String getToken(String username, String name);
}
