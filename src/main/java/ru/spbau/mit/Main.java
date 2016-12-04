package ru.spbau.mit;

import ru.spbau.mit.command.Cat;
import ru.spbau.mit.command.Grep;
import ru.spbau.mit.command.Wc;

import java.util.Scanner;

/**
 * REPL class for shell
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello!");

        Shell shell = new Shell.Builder()
                .command("cat", new Cat())
                .command("wc", new Wc())
                .command("echo", input -> input)
                .command("pwd", (input -> System.getProperty("user.dir")))
                .command("grep", new Grep())
                .init();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();

            if ("exit".equals(input.trim())) {
                System.exit(0);
            }

            if (!input.isEmpty()) {
                System.out.println(shell.execute(input));
            }
        }
    }
}
