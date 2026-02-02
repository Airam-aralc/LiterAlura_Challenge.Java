package com.alura.literalura.service;

import jakarta.persistence.*;

@Entity
@Table(name="livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //o banco de dados irá gerar um valor único para o ID
    private Long id;
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    private String idioma;
    private double downloads;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public double getDownloads() {
        return downloads;
    }
    public void setDownloads(double downloads) {
        this.downloads = downloads;
    }

    @Override //to string
    public String toString(){
        String nomeAutor = (this.autor != null) ? this.autor.getNome() : "Desconhecido";

        return String. format("""
               ------------------- LIVRO ---------------------
               Titulo:   %s
               Autor:    %s
               Idioma:   %s
               Downloads %.2f
               """,
                this.titulo, nomeAutor, this.idioma, this.downloads
        );
    }
}
