package ru.spbau.mit;

import ru.spbau.mit.command.Cat;
import ru.spbau.mit.command.Wc;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello!");

        Shell shell = new Shell.Builder()
                .command("cat", new Cat())
                .command("wc", new Wc())
                .command("echo", input -> input)
                .command("pwd", (input -> System.getProperty("user.dir")))
                .command("exit", input -> {
                    System.exit(0);
                    return "";
                })
                .init();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();
            System.out.println(shell.execute(input));
        }
    }
}