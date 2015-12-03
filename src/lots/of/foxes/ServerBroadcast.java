/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Diego
 */
public class ServerBroadcast implements Runnable{
    private static final String DISCOVER_MESSAGE = "LOF_DISCOVER                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ";
    private static final String RESPONSE_MESSAGE = "LOF_RESPONSE";
    private static final String BROADCAST_IP = "255.255.255.255";
    private static final int BYTE_DATA_SIZE = 1024;
    
    Thread thread;
    private int port;
    private byte[] sendData = new byte[BYTE_DATA_SIZE];
    private byte[] receiveData = new byte[BYTE_DATA_SIZE];
    private InetAddress group;
    private InetAddress myIP;
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;
    private String gameName;
    private String gameVersion;
    private String gameField;
    
    public ServerBroadcast(String gameName, String gameVersion, 
                String gameField, int port){
        
        this.gameName = gameName;
        this.gameVersion = gameVersion;
        this.gameField = gameField;
        this.port = port;
        try{
            group = InetAddress.getByName(BROADCAST_IP);
        }
        catch(UnknownHostException ex){
            System.out.println("Could not resolve Broadcast: " + ex);
        }
        try{
            myIP = InetAddress.getLocalHost();
        }
        catch(UnknownHostException ex){
            System.out.println("Could not find localhost: " + ex);
        }
        sendData = (RESPONSE_MESSAGE + ";" + gameName + ";" + gameVersion + ";" + gameField + ";" + myIP).getBytes();
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        if(this.thread == null){
            this.thread = new Thread(this);
            thread.start();
        }
    }
    
    @Override
    public void run(){
        try(DatagramSocket socket = new DatagramSocket(port)){
            socket.setBroadcast(true);
            while(true){
                System.out.println("Wait for Client.");
                socket.receive(receivePacket);
                String s = new String(receivePacket.getData());
                if(s.equals(DISCOVER_MESSAGE)){
                    System.out.println("Got " + DISCOVER_MESSAGE + " from Client " + receivePacket.getAddress().getHostAddress());
                    sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    socket.send(sendPacket);
                    System.out.println("Sent " + RESPONSE_MESSAGE + " to Client " + receivePacket.getAddress().getHostAddress());
                }
            }
        }
        catch(Exception ex){
            System.out.println("Error while starting socket: " + ex);
        }
    }
    
    public static void main(String[] args) {
        ServerBroadcast server1 = new ServerBroadcast("Homo", "Homo", "Homo", 6969);
    }
    
}
