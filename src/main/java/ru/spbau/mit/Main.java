package ru.spbau.mit;

import java.util.Scanner;

/**
 * REPL class for shell
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello!");

        Shell shell = Shell.getInstance();
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
