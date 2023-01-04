package com.github.professorSam.goveeLight2;

public class GoveeLight {

    public GoveeLight() {
        final int deviceDiscoveryPort = 4001;
        final int deviceResponsePort = 4002;
        final String multicastAddress = "239.255.255.250";
        new UdpDebugListener(multicastAddress, deviceDiscoveryPort).start();
        sleep(1000);
        UdpClient request = new UdpClient(multicastAddress, deviceDiscoveryPort);
        UdpServer server = new UdpServer(deviceResponsePort);
        server.listen();
        sleep(2000);
        boolean discoverySuccess = request.sendDiscoverRequest();
        if(!discoverySuccess){
            System.err.println("Error while sending discovery message!");
            System.exit(-1);
        }
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        new GoveeLight();
    }
}
