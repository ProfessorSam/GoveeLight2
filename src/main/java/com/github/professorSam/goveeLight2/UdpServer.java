package com.github.professorSam.goveeLight2;

import java.io.IOException;
import java.net.*;

public class UdpServer {

    private final int PORT;
    private final Thread LISTENING_THREAD;

    public UdpServer(int port) {
        this.PORT = port;
        LISTENING_THREAD = new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket(PORT)) {
                System.out.println("[Server] Listening to: " + PORT + " on " + socket.getLocalAddress().getHostAddress());
                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("[Server] recived: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void listen() {
        LISTENING_THREAD.start();
    }
}
