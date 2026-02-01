package com.alura.literalura.view;

import com.alura.literalura.modelo.BookData;
import com.alura.literalura.modelo.Results;
import com.alura.literalura.service.APIConsumer;
import com.alura.literalura.service.DataConverter;
import com.alura.literalura.service.Livro;

import java.util.Scanner;

public class Menu {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private APIConsumer consumer = new APIConsumer();
    private DataConverter converter = new DataConverter();
    private Scanner scanner = new Scanner(System.in);
    private String json;

    public void exibeMenu(){
        var opcao = -1;

        while (opcao != 0) {
            String menu = """
                    ----------------------------------------------------
                    Escolha a opção:
                    1 - Digite o título do livro
                    2 - Listar livros registrados
                    3 - Listar autores registrados 
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros por idioma
                    0 - Sair
                    ---------------------------------------------------
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    exibeLivroPorTitulo();
                    break;
                case 2:
                    exibeLivrosRegistrados();
                    break;
                case 3:
                    exibeAutoresRegistrados();
                    break;
                case 4:
                    exibeAutoresVivosPorAno();
                    break;
                case 5:
                    exibeLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    private void exibeLivroPorTitulo() {
        System.out.println("Digite o título:");
        String titulo = scanner.nextLine();
        json = consumer.getData(URL_BASE + "?search=" +titulo.replace(" ", "+"));

        Livro livroEncontrado = livro(titulo);
        System.out.println(livroEncontrado);
    }

    private Livro livro(String titulo) {
        Results data = converter.getData(json, Results.class);

        BookData bookData = data.bookDataList().stream() //vai pegar a lista de informações de livro, depois transformar em uma stream
                .filter(l -> l.titulo().toUpperCase().contains(titulo.toUpperCase())) //filtra: para cada livro irá pegar o título, colocar em maiúsculo, verifica se ele contém o título em maiúsculo
                .findFirst() //só pegará o primeiro
                .orElse(null); //se não tiver, retorna null

        //Se encontrou, converte para a classe Livro
        if (bookData != null) {
            Livro livro = new Livro();
            livro.setTitulo(bookData.titulo());
            livro.setDownloads(bookData.downloadCount());

            // Tratando as listas para campos simples
            if (!bookData.autor().isEmpty()) {
                livro.setAutor(bookData.autor().get(0).name());
            }
            if (!bookData.linguagem().isEmpty()) {
                livro.setIdioma(bookData.linguagem().get(0));
            }

            return livro;
        }
        return null;
    }

        //OLHE PARA CÁ E VEJA SE NÃO TEM NENHUM ERRO
//    private BookData getBookData(String titulo) {
//        Results data = converter.getData(json, Results.class);
//        BookData bookData = data.bookDataList().stream() //vai pegar a lista de informações de livro, depois transformar em uma stream
//                .filter(livro -> livro.titulo().toUpperCase().contains(titulo.toUpperCase())) //filtra: para cada livro irá pegar o título, colocar em maiúsculo, verifica se ele contém o título em maiúsculo
//                .findFirst().orElse(null); //só pegará o primeiro, se não tiver, retorna null
//        return bookData;
//    }

        private void exibeLivrosRegistrados () {
        }

        private void exibeAutoresRegistrados () {
        }

        private void exibeAutoresVivosPorAno () {
        }

        private void exibeLivrosPorIdioma () {
        }
}
