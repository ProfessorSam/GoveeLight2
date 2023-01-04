package com.github.professorSam.goveeLight2;

import java.io.IOException;
import java.net.*;

public class UdpDebugListener {

    private final InetAddress address;
    private final int port;

    public UdpDebugListener(String multicastAddress, int listeningPort){
        this.port = listeningPort;
        try {
            this.address = InetAddress.getByName(multicastAddress);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    public void start(){
        new Thread(() -> {
            try(MulticastSocket socket = new MulticastSocket(port)) {
                socket.joinGroup(address);
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                System.out.println("[Debug] Listening to: " + port + " on " + socket.getNetworkInterface().getName());
                while (true){
                    socket.receive(packet);
                    System.out.println("[DEBUG] recived: " + new String(packet.getData(), 0, packet.getLength()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
