package de.uulm.in.vs.grn.p1a;

import java.net.*;
import java.io.*;

public class NumberGuessingGameServer{

    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = new ServerSocket(5555);
        System.out.println("server started");
        Socket server = serverSocket.accept();

    }
}