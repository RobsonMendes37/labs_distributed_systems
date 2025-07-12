package org.example;

import java.net.*;

class clienteUDP {
    public static void main(String args[]) throws Exception {

        //BufferedReader doUsuario = new BufferedReader(new InputStreamReader(System.in));

        DatagramSocket socketCliente = new DatagramSocket();

        InetAddress IPAddress = InetAddress.getByName("172.25.203.65");
        System.out.println("IP local: "+IPAddress.getHostAddress());

        byte[] dadosEnvio = new byte[1024];
        byte[] dadosRecebidos = new byte[1024];

        //String frase = doUsuario.readLine();
        //dadosEnvio = frase.getBytes();

        dadosEnvio = "sistemas distribuidos do robson".getBytes();
        System.out.println("Mensagem original: "+new String(dadosEnvio));

        DatagramPacket pacoteEnviado = new DatagramPacket(dadosEnvio,
                dadosEnvio.length, IPAddress, 9999);

        socketCliente.send(pacoteEnviado);

        DatagramPacket pacoteRecebido = new DatagramPacket(
                dadosRecebidos, dadosRecebidos.length);

        socketCliente.receive(pacoteRecebido);

        String fraseModificada = new String(pacoteRecebido.getData());

        System.out.println("Do Servidor: " + fraseModificada);
        socketCliente.close();
    }
}