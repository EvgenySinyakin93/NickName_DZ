package org.example;

import java.util.Random;
import static org.example.Counter.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrom = new Thread(() -> {
           for (String text : texts) {
               if(isPalindrome(text) && !isSomeChar(text)) {
                   incrementCounter(text.length());
               }
           }
        });
        palindrom.start();

        Thread someChar = new Thread(() -> {
            for (String text : texts) {
                if(isSomeChar(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        someChar.start();

        Thread ascendingOrder = new Thread(() -> {
            for (String text : texts) {
                if(isSomeChar(text) && isAscendingOrder(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        ascendingOrder.start();

        someChar.join();
        ascendingOrder.join();
        palindrom.join();

        System.out.println("Красивых слов длиной 3 " + counter3 + " шт");
        System.out.println("Красивых слов длиной 4 " + counter4 + " шт");
        System.out.println("Красивых слов длиной 5 " + counter5 + " шт");

    }
}