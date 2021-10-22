package de.uulm.in.vs.grn.p1a;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class NumberGuessingGameServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5555);
        Socket socket = serverSocket.accept();
        System.out.println("Player connected");


        int guess = 0;
        int number = ThreadLocalRandom.current().nextInt(50);
        int maxTries = 5;

        String output = "Guess the number between 1 and 50 \n";
        String output2 = "Guess now! \n";

        OutputStream out = null;
        OutputStream ot = null;
        try {
            out = socket.getOutputStream();
            ot = socket.getOutputStream();
            out.write(output2.getBytes());
            out.write(output.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream fis = socket.getInputStream()) {
            for (int count = maxTries; count >= 0; count--) {
                System.out.println("Next round");
                System.out.println(number);
                guess = readNextNumber(fis);

                if (guess <= 0 || guess > 50) {
                    output = "number out of range (must be 1-50) \n";
                    count += 1;
                }
                if (number == guess && guess > 0 && guess <= 50) {
                    output = "You won! The number was " + number + " \n";
                    ot.write(output.getBytes());
                    break;
                }
                if (count == 0) {
                    output = "You lost \n";
                    ot.write(output.getBytes());
                    break;
                }
                if (number < guess && guess > 0 && guess <= 50) {
                    output = "Number too big \n";
                }
                if (number > guess && guess > 0 && guess <= 50) {
                    output = "Number too small \n";
                }

                output2 = "Still " + (count) + " tries left \n";
                ot.write(output.getBytes());
                ot.write(output2.getBytes());

            }
            out.write("bye bye\n".getBytes());
            out.flush();
            ot.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
        ot.close();
        serverSocket.close();
        socket.close();
    }

    public static int readNextNumber(InputStream fis) {
        int b = 0;
        String s = "";
        try {
            while ((b = fis.read()) != 10) {
                s += (char) b;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return -1;
        }

        return Integer.parseInt(s);
    }

}
