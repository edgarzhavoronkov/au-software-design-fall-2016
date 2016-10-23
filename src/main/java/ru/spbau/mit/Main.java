package ru.spbau.mit;

import ru.spbau.mit.command.Cat;
import ru.spbau.mit.command.Cd;
import ru.spbau.mit.command.Ls;
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
                .command("cd", new Cd())
                .command("ls", new Ls())
                .command("echo", input -> input)
                .command("pwd", (input -> System.getProperty("user.dir")))
                .init();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (input.trim().equals("exit")) {
                System.exit(0);
            }
            System.out.println(shell.execute(input));
        }
    }
}
