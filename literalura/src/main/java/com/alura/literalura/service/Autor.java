package com.alura.literalura.service;

import com.alura.literalura.modelo.AutorData;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}

    public Autor(AutorData dadosAutor) {
        this.nome = dadosAutor.name();
        this.anoNascimento = tryParseInt(dadosAutor.AnoNascimento());
        this.anoFalecimento = tryParseInt(dadosAutor.AnoFalecimento());
    }

    private Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getNome(){
        return nome;
    }

    @Override
    public String toString() {
        return "---------------- Autor ----------------" +
                "\nNome: " + nome +
                "\nNascimento: " + (anoNascimento != null ? anoNascimento : "Desconhecido") +
                "\nFalecimento: " + (anoFalecimento != null ? anoFalecimento : "Vivo / Desconhecido") +
                "\n---------------------------------------";
    }
}