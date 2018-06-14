package BrucknerD;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.swing.JFrame;


/**
 * @author Dario
 *
 * ChatClientSocket enthaelt alle Methoden furr das Senden und Empfangen der Nachrichten fuer Client
 *
 */
public class ChatClientSocket {
	Socket client;
	PrintWriter writer;
	BufferedReader reader;
	SimpleChatClient i;
	Thread t;
	static boolean running = true;

	/**
	 * Standartkonstruktor
	 */
	public ChatClientSocket() {
		
	}

	/**
	 * @param i Reference zu SimpleChatClient
	 *
	 * Startet den Client
	 *
	 */

	public ChatClientSocket(SimpleChatClient i) {
		//cli = new ChatClientSocket();
		this.i = i;

		startClient();
	}
	
	/**
	 * 
	 * oeffnet die GUI und Startet den MessageListener Thread
	 * 
	 */
	
	public void startClient(){

		if(!createClient()){
			System.exit(0);
		}
		//Thread erstellen & Starten
		t = new Thread(new MessagefromServerListener());
		t.start();
	
	}
	
	/**
	 * 
	 * @return gibt den Status zur?ck ob der Server Gestartet werden konnte.
	 * 
	 * Erstellt den ClientSocket und verbindet ihn mit dem Server
	 */
	
	public boolean createClient() {
		try {
			//ClientSocket
			client = new Socket("localhost", 5050);
			client.setSoTimeout(5);
			
			//MessageReader
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			//MessageWriter
			writer = new PrintWriter(client.getOutputStream());
			

			return true;
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Host konnte nicht gefunden werden");
			e.printStackTrace();
			return false;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IOException");
			return false;
		}
	}	
	
	/**
	 * 
	 * @param input Nachricht des Clients
	 * @param name  Name des Clients
	 * 
	 * Sendet Nachricht und Name an den Server
	 */
	
	public void sendtoserver(String input,String name) {

		writer.write(name +": " + input + "\n");
		writer.flush();
		
	}

	/**
	 * @param name name des Clients um ihn aus der ClientListe zu entfernen
	 *
	 * Beendet die MessageListner Thread und beendet die Gui;
	 */

	public void shutdown(String name){
		sendtoserver("EXIT",name);
		running = false;
	}

	/**
	 * Wird aufgrufen wenn der Server sich schlie?t um die Gui zu beenden
	 */


	public void shutdownServer(){

		running = false;
		i.shutGUI();
	}


	/**
	 * 
	 * @author Dario
	 * 
	 * 
	 * Thread um Nachrichten vom Server zu bekommen und diese auf der GUI anzuzeige
	 */

	public class MessagefromServerListener implements Runnable{

		@Override
		public void run() {
			String mes;


			try {
				while(running) {
					try{
						mes = reader.readLine();
					}catch (SocketTimeoutException e) {
						continue;
					}
					if(mes.equals("EXIT")){
						shutdownServer();
					}else {
						//Zeigt die Nachricht an
						i.showtext(mes);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				i.showtext("FEHLER");

			}
			
		}
		
	}
}
