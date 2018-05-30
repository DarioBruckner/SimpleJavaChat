package BrucknerD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

/**
 * 
 * @author Dario
 * 
 * Main Klasse für den Server. Startet GUI und Funktionen
 * 
 */


public class SimpleChatServer extends JFrame {
	public static void main(String [] args){
		ServerGUI f = new ServerGUI("Server");
		SimpleChatServer p = new SimpleChatServer();
		p.add(f);
		p.setSize(650, 420);
		p.setVisible(true);
		
		//Startet Server
		ChatServerSocket chat = new ChatServerSocket(f);
	}
	
	
	
}
