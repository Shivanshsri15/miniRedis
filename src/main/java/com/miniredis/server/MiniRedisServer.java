package com.miniredis.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MiniRedisServer {

    static final String VERSION = "0.1.0";
    static final int PORT = 6379;

    public static void main(String[] args) throws IOException {
        System.out.println("MiniRedis " + VERSION + " starting on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connected: " + client.getRemoteSocketAddress());
                handleClient(client);
            }
        }
    }

    private static void handleClient(Socket client) {
        try (client;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(
                     new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = in.readLine()) != null) {
                String command = line.trim();
                String response;
                if (command.equalsIgnoreCase("PING")) {
                    response = "PONG";
                } else {
                    response = "ERR unknown command '" + command + "'";
                }
                out.write(response);
                out.write("\n");
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
        System.out.println("Client disconnected");
    }
}