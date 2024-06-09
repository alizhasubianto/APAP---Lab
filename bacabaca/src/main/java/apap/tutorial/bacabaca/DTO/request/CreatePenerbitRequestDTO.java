package apap.tutorial.bacabaca.DTO.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
public class CreatePenerbitRequestDTO {
    private String namaPenerbit;
    private String alamat;
    private String email;
}
