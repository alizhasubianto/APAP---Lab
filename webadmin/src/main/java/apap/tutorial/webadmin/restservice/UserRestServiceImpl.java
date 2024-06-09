package apap.tutorial.webadmin.restservice;

import apap.tutorial.webadmin.DTO.LoginRequestDTO;
import apap.tutorial.webadmin.DTO.TokenDTO;
import apap.tutorial.webadmin.DTO.UserRequestDTO;
import apap.tutorial.webadmin.DTO.UserResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserRestServiceImpl implements UserRestService {
    private final WebClient webClient;

    public UserRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public String getToken(String username, String name) {
        var body = new LoginRequestDTO(username, name);

        var response = this.webClient
                .post()
                .uri("/api/auth/login-jwt-webadmin")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(TokenDTO.class)
                .block();

        var token = response.getToken();

        return token;
    }

    @Override
    public UserResponseDTO sendUser(UserRequestDTO userRequestDTO, String jwtToken) {
        try {
            var response = this.webClient
                    .post()
                    .uri("/api/user/add")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(userRequestDTO)
                    .retrieve()
                    .bodyToMono(UserResponseDTO.class);
            var userSubmitted = response.block();
            return userSubmitted;
        } catch (Exception e) {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            return userResponseDTO;
        }
    }
}