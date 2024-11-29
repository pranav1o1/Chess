import java.util.*;
import java.util.Scanner;
//package lp5;

public class Ring {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the no of nodes: ");
        int n = sc.nextInt();

        int token = 0;

        for (int i = 0; i < n; i++) {
            System.out.println(" " + i);
        }
        System.out.println(" " + 0);
        try {
            while (true) {
                System.out.println("Enter the sender: ");
                int s = sc.nextInt();
                System.out.println("Enter the receiver: ");
                int r = sc.nextInt();
                System.out.println("Enter the Data: ");
                String d = sc.next();

                System.out.println("token passing: ");

                for (int i = token; (i % n) != s; i = (i + 1) % n) {
                    System.out.println(" " + i + "->");
                }

                System.out.println(" " + s);
                System.out.println("sender " + s + "sending data: " + d);

                for (int i = (s + 1) % n; i != r; i = (i + 1) % n) {
                    System.out.println("Data " + d + " forworded by " + i + "->" + (i + 1));
                }
                System.out.println("receiver " + r + " received data: " + d);

                token = s;

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
