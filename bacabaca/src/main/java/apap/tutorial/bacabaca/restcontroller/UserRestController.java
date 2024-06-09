package apap.tutorial.bacabaca.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apap.tutorial.bacabaca.DTO.UserMapper;
import apap.tutorial.bacabaca.DTO.request.CreateUserRequestDTO;
import apap.tutorial.bacabaca.DTO.response.CreateUserResponseDTO;
import apap.tutorial.bacabaca.model.UserModel;
import apap.tutorial.bacabaca.service.UserService;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @PostMapping(value = "/user/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<?> addUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        try {
            UserModel userModel = userMapper.createUserRequestDTOToUserModel(createUserRequestDTO);
            userModel = userService.addUser(userModel, createUserRequestDTO);

            CreateUserResponseDTO createUserResponseDTO = userMapper.createUserResponseDTOToUserModel(userModel);
            createUserResponseDTO.setRole(userModel.getRole().getRole());
            return new ResponseEntity<>(createUserResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
    }
}
