package br.com.fiap.model.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PokemonDTO(
        @NotBlank
        String nome,
        @NotNull
        @PositiveOrZero
        Double altura,
        @NotNull
        @PositiveOrZero
        Double peso,
        @NotBlank
        String categoria,
        @PastOrPresent
        LocalDate dataDaCaptura,
        String urlImagem
) {
}
