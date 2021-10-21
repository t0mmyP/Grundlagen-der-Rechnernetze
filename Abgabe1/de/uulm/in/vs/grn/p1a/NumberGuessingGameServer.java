package de.uulm.in.vs.grn.p1a;

import java.net.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;

public class NumberGuessingGameServer{

    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = new ServerSocket(5555);
        System.out.println("server started");
        Socket server = serverSocket.accept();

        OutputStream out = server.getOutputStream();
        InputStream in = server.getInputStream();

        int counter = 0;
        int number = ThreadLocalRandom.current().nextInt(0,50);
        int playerguess;

        boolean game = true;
        while (game) {

            try {

                int x;
                String input = "";

                while ((x = in.read()) != -1) {

                    if (x == '\n') {

                        out.write(input.getBytes());
                        playerguess = Integer.parseInt(input);
                        System.out.println(playerguess);

                        if (counter == 6) {

                            out.write(" You Lost!".getBytes());
                            game = false;
                            break;

                        } else if (playerguess == number) {

                            out.write(" Congratulation!".getBytes());
                            game = false;
                            break;

                        } else if (playerguess < number) {

                            out.write(" that's too low!".getBytes());
                            counter++;

                        } else if (playerguess > number) {

                            out.write(" that's too high!".getBytes());
                            counter++;
                        }
                        input = "";

                    } else {

                        System.out.println((char) x);
                        input += (char) x;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            out.write("\nThank you".getBytes());
        }
        server.close();
        serverSocket.close();
    }
}