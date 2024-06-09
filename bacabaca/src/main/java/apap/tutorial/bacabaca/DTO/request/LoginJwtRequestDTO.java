package apap.tutorial.bacabaca.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginJwtRequestDTO {
    private String username;
    private String name;
    private String password;
}
