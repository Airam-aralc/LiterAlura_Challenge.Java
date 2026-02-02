package com.alura.literalura.view;

import com.alura.literalura.modelo.AutorData;
import com.alura.literalura.modelo.BookData;
import com.alura.literalura.modelo.Results;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.APIConsumer;
import com.alura.literalura.service.Autor;
import com.alura.literalura.service.DataConverter;
import com.alura.literalura.service.Livro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private APIConsumer consumer = new APIConsumer();
    private DataConverter converter = new DataConverter();
    private Scanner scanner = new Scanner(System.in);
    private String json;

    private List<BookData> dadosLivro = new ArrayList<>();

//
    private List<AutorData> dadosAutor = new ArrayList<>();
//

    private LivroRepository repository;
    private AutorRepository autorRepository;

    public Menu(LivroRepository repository, AutorRepository autorRepository) {
        this.repository = repository;
        this.autorRepository = autorRepository;
    }

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

//    private Livro livro(String titulo) {
//        Results data = converter.getData(json, Results.class);
//
//        BookData bookData = data.bookDataList().stream()
//                .filter(l -> l.titulo().toUpperCase().contains(titulo.toUpperCase()))
//                .findFirst()
//                .orElse(null);
//
//        if (bookData != null) {
//            Livro livro = new Livro();
//            livro.setTitulo(bookData.titulo());
//            livro.setDownloads(bookData.downloadCount());
//
//            if (!bookData.linguagem().isEmpty()) {
//                livro.setIdioma(bookData.linguagem().get(0));
//            }
//
//            if (!bookData.autor().isEmpty()) {
//                AutorData ad = bookData.autor().get(0);
//                // Busca o autor no banco para associar ao livro
//                Autor autor = autorRepository.findByNome(ad.name())
//                        .orElseGet(() -> autorRepository.save(new Autor(ad)));
//                livro.setAutor(autor); // Agora passa o OBJETO autor
//            }
//
//            return livro;
//        }
//        return null;
//    }

    private void exibeLivroPorTitulo() {
        System.out.println("Digite o título:");
        String titulo = scanner.nextLine();
        json = consumer.getData(URL_BASE + "?search=" + titulo.replace(" ", "+"));

        Results data = converter.getData(json, Results.class);
        BookData bookData = data.bookDataList().stream()
                .filter(l -> l.titulo().toUpperCase().contains(titulo.toUpperCase()))
                .findFirst()
                .orElse(null);

        if (bookData != null) {
            Livro livroEncontrado = new Livro();
            livroEncontrado.setTitulo(bookData.titulo());
            livroEncontrado.setDownloads(bookData.downloadCount());
            livroEncontrado.setIdioma(bookData.linguagem().isEmpty() ? "en" : bookData.linguagem().get(0));

            if (!bookData.autor().isEmpty()) {
                AutorData ad = bookData.autor().get(0);

                // 1. Tenta buscar o autor no banco ou cria um novo
                Autor autorParaSalvar = autorRepository.findByNome(ad.name())
                        .orElseGet(() -> {
                            Autor novo = new Autor(ad);
                            return autorRepository.save(novo);
                        });

                // 2. AGORA SIM: Atribui o OBJETO Autor ao Livro
                livroEncontrado.setAutor(autorParaSalvar);
            }

            repository.save(livroEncontrado);
            System.out.println(livroEncontrado);
            System.out.println("Livro salvo no banco de dados");
        } else {
            System.out.println("Livro não encontrado");
        }
    }

    private void exibeLivrosRegistrados () {
        List<Livro> livros = repository.findAll();

        if (livros.isEmpty()){
            System.out.println("Nenhum livro cadastrado");
        } else {
            System.out.println("-------LIVROS CADASTRADOS NO BANCO--------");
            livros.forEach(System.out::println);
        }
    }

    private void exibeAutoresRegistrados () {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            System.out.println("\n------- AUTORES REGISTRADOS (DETALHES) --------");
            autores.forEach(System.out::println);
        }
    }

    private void exibeAutoresVivosPorAno () {
        System.out.println("Digite o ano que deseja pesquisar:");
        try {
            var ano = scanner.nextInt();
            scanner.nextLine();

            List<Autor> autoresVivos = autorRepository.buscarAutoresVivosNoAno(ano);

            if (autoresVivos.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo no ano de " + ano);
            } else {
                System.out.println("\n------- AUTORES VIVOS EM " + ano + " -------");
                autoresVivos.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Por favor, digite um ano válido (número).");
            scanner.nextLine();
        }
    }

    private void exibeLivrosPorIdioma () {
        System.out.println("""
            Digite o idioma para busca:
            es - Espanhol
            en - Inglês
            fr - Francês
            pt - Português
            """);
        var idioma = scanner.nextLine().toLowerCase();
        List<Livro> livros = repository.findByIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado nesse idioma.");
        } else {
            livros.forEach(System.out::println);
        }

        System.out.println("Foram encontrados " + livros.size() + " livros em " + idioma.toUpperCase());
    }
}

