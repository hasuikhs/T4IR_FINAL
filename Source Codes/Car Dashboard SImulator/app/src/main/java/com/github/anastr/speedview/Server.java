package com.github.anastr.speedview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Server implements Runnable {
    public static final String SERVERIP = "70.12.225.139";
    public static final int SERVERPORT = 7779;
    private Handler handler;
    public Server(Handler handler){
        this.handler = handler;
    }


    @Override
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);

            Log.d("UDPMsg", "S: Connecting...");
            /* Create new UDP-Socket */
            DatagramSocket socket = new DatagramSocket(SERVERPORT,serverAddr);

            /* By magic we know, how much data will be waiting for us */
            byte[] buf = new byte[65536];
            /* Prepare a UDP-Packet that can
             * contain the data we want to receive */
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            Log.d("UDPMsg", "S: Receiving...");
            while(true) {
                /* Receive the UDP-Packet */
                socket.receive(packet);
                String msg =new String(packet.getData(),"UTF-8");
                if(!(msg.contains("null"))) {
                    String[] str = msg.split("\\.");
                    if(str[1].length()>1) {
                        str[1] = str[1].substring(0, 2);

                        Log.d("HAHOHIHE1",str[1]);
                    }else {
                        str[1] = str[1].substring(0, 1);
                    }
                    String result = str[0] + "." + str[1];

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("result",result);
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                }

            }
        } catch (Exception e) {
            Log.e("UDPMsg", "S: Error", e);
        }
    }
}
