package apap.tutorial.bacabaca.DTO.request;

import apap.tutorial.bacabaca.model.Penerbit;
import apap.tutorial.bacabaca.model.Penulis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
@Setter 
public class CreateBukuRequestDTO {

    @NotBlank(message = "Judul buku tidak boleh kosong")
    //@Size(max = 255, message = "Judul buku maksimal 255 karakter")
    private String judul;

    @NotBlank(message =  "Tahun terbit tidak boleh kosong")
   // @Size(max = 4, message = "Tahun terbit maksimal 4 karakter")
    private String tahunTerbit;

   // @NotNull(message = "Harga tidak boleh kosong")
    @DecimalMin(value = "0.0", inclusive = false, message = "Harga tidak boleh kurang dari 0")
    private BigDecimal harga;

    @NotNull(message = "Penerbit tidak boleh kosong")
    private Penerbit penerbit;

    private List<Penulis> listPenulis;
}
