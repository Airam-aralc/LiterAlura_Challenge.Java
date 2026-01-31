package view;

import modelo.BookData;
import modelo.Results;
import service.APIConsumer;
import service.DataConverter;

import java.util.Scanner;

public class Menu {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private APIConsumer consumer = new APIConsumer();
    private DataConverter converter = new DataConverter();
    private Scanner scanner = new Scanner(System.in);
    private String json;

    private String menu = """
            --------------------------
            Escolha a opção:
            1- Digite o título do livro
            """;

    private void exibeMenu(){
        System.out.println(menu);
    }

    public void run (){
        exibeMenu();
        var opcao = Integer.parseInt(scanner.nextLine());
        switch (opcao){
            case 1:
                exibeLivroPorTitulo();
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }
    }

    private void exibeLivroPorTitulo() {
        System.out.println("Digite o título:");
        String titulo = scanner.nextLine();
        json = consumer.getData(URL_BASE + "?search=" +titulo.replace(" ", "+"));
        //System.out.println(json);
        BookData bookData = getBookData(titulo);
        System.out.println(bookData);
    }

    private BookData getBookData(String titulo) {
        Results data = converter.getData(json, Results.class);
        BookData bookData = data.bookDataList().stream() //vai pegar a lista de informações de livro, depois transformar em uma stream
                .filter(livro -> livro.titulo().toUpperCase().contains(titulo.toUpperCase())) //filtra: para cada livro irá pegar o título, colocar em maiúsculo, verifica se ele contém o título em maiúsculo
                .findFirst().orElse(null); //só pegará o primeiro, se não tiver, retorna null
        return bookData;
    }
}
