package com.github.professorSam.goveeLight2;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.*;

public class UdpClient {
    private final InetAddress INET_ADDRESS;
    private final int PORT;
    public UdpClient(String address, int port){
        try {
            INET_ADDRESS = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.PORT = port;
    }

    public boolean sendDiscoverRequest(){
        final byte[] PAYLOAD;
        JSONObject message = new JSONObject();
        JSONObject innerMessage = new JSONObject();
        innerMessage.put("cmd", "scan");
        JSONObject data = new JSONObject();
        data.put("account_topic", "reserve");
        innerMessage.put("data", data);
        message.put("msg", innerMessage);
        System.out.println("[Client] Sending Discover Message: " + message.toJSONString());
        PAYLOAD = message.toJSONString().getBytes();
        try(DatagramSocket socket = new DatagramSocket()){
            socket.setBroadcast(true);
            DatagramPacket packet = new DatagramPacket(PAYLOAD, PAYLOAD.length, INET_ADDRESS, PORT);
            socket.send(packet);
            System.out.println("[Client] Discovery Packet send from " + socket.getLocalAddress().getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
