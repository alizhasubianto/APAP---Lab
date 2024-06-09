package apap.tutorial.bacabaca.DTO.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class UpdatePenerbitRequestDTO extends CreatePenerbitRequestDTO{

    @NotNull(message = "Tidak ditemukan id buku")
    private long idPenerbit;
}
