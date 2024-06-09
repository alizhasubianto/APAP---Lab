package apap.tutorial.bacabaca.DTO.request;

import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.model.Penulis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data 
public class UpdateBukuRequestDTO extends CreateBukuRequestDTO {

    @NotNull(message = "Tidak ditemukan id buku")
    private UUID id;
}
