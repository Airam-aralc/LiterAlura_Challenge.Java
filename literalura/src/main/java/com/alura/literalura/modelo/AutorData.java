package com.alura.literalura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorData(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") String AnoNascimento,
        @JsonAlias("death_year") String AnoFalecimento
) {
}
