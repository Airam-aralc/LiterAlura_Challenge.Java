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

    
}
