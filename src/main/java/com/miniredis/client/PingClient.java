package com.miniredis.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class PingClient {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 6379);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
             BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            System.out.println("Connected to " + socket.getRemoteSocketAddress()
                    + " from local port " + socket.getLocalPort());

            String input;
            while ((input = console.readLine()) != null) {
                out.write(input);
                out.write("\n");
                out.flush();

                String reply = in.readLine();
                if (reply == null) {
                    System.out.println("Server closed the connection");
                    break;
                }
                System.out.println(reply);
            }
        }
    }
}