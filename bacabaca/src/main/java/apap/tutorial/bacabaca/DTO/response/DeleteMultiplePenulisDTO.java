package apap.tutorial.bacabaca.DTO.response;

import java.util.List;

import apap.tutorial.bacabaca.model.Penulis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class DeleteMultiplePenulisDTO {
    private List<Penulis> listPenulis;
}
