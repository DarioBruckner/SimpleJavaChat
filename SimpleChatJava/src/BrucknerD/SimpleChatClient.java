package BrucknerD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;

import javax.swing.JFrame;

/**
 * 
 * @author Dario
 *
 *	Main Klasse für Client. Startet GUI und Funktionen
 *
 */



public class SimpleChatClient{
	String input = null;
	PrintWriter writer;
	OutputStream out;
	Socket client;
	
	public static void main(String [] args){
		
		//Startet einen Client
		ChatClientSocket  a = new ChatClientSocket(1);
	}
	
	/**
	 * 
	 * @author Dario
	 * 
	 * Öffnet einen Socket und setzt den outputstream
	 * 
	 * 
	 */
	
	
	public SimpleChatClient(){
		try {
			
			//Client
			client = new Socket("localhost", 5050);
			out = client.getOutputStream();
			
			
			
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}	
	
	/**
	 * @author Dario
	 * @param input des Clients auf der GUI
	 * 
	 * Sendet die Nachricht an den Outputstream 
	 */
	
	public void sendmes(String input){
		writer = new PrintWriter(out);
		writer.write(input + "\n");
		writer.flush();
		
	}
}
