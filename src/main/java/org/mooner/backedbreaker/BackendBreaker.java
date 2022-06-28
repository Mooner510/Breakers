package org.mooner.backedbreaker;

import java.util.Scanner;

public class BackendBreaker {
    public static void main(String[] args) {
        AsyncConnection connection = new AsyncConnection();
        new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                connection.createGetThread();
                connection.createPostThread();
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            switch (scanner.nextLine()) {
                case "get" -> System.out.println("Get Count: " + connection.getGetCount());
                case "post" -> System.out.println("Post Count: " + connection.getPostCount());
            }
        }
    }
}
