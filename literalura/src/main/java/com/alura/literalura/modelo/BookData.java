package com.alura.literalura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) //Irá ignorar tudo que eu não colocar aqui
public record BookData(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorData> autor,
        @JsonAlias("languages") List<String> linguagem,
        @JsonAlias("download_count") Double downloadCount
        ) {
}
