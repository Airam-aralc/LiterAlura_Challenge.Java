package view;

import service.APIConsumer;
import service.DataConverter;

import java.util.Scanner;

public class Menu {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private APIConsumer consumer = new APIConsumer();
    private DataConverter converter = new DataConverter();
    private Scanner scanner = new Scanner(System.in);

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
    }
}
