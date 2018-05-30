package BrucknerD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author Dario
 * @version 1.03
 * 
 * Klasse ChatServerSocket wird verwedet um alle Serverfunktionen auszuführen 
 * 
 */


public class ChatServerSocket {
	private ServerSocket server;
	ServerGUI gui = ServerGUI.getGui();
	ArrayList<PrintWriter> list_clientWriter;
	ArrayList<String> list_clients = new ArrayList<String>();
	
	
	
	/**
	 * Standartconstructor
	 */
	public ChatServerSocket() {
		
	}
	
	
	
	/**
	 * 
	 * @param Servergui
	 * 
	 * Constructor um zu überprüfen ob der Server richtig gestartet worden ist
	 * 
	 */
	
	public ChatServerSocket(ServerGUI gui) {
		ChatServerSocket s = new ChatServerSocket();
		gui = new ServerGUI();
		this.gui = gui;	
		
		boolean ser = s.server();
		
		if(ser == true) {
			s.acceptclients();
	  	}else {
	  		System.out.println("Fehler bei dem Server");
	 	}
	}
	
	
	
	
	/**
	 * @param /
	 * @return True: Wenn Server richtig gestartet werden konnte False: Falls es einen Fehler gab
	 * 
	 * Methode zum Starten des Servers und initalisierung der list_clientWriter
	 */
	
	public boolean server() {
		try {
			
			//Server
			server = new ServerSocket(5050);
			
			//ArrayList initalisierung
			list_clientWriter = new ArrayList<PrintWriter>();
			
			
			return true;
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			System.out.println("Server konnte nicht gestartet werden!");
			
			return false;
		}
	}
	
	
	
	/**
	 * @param /
	 * @return void
	 * 
	 * Methode um die Clients die mit dem Server verbunden sind in eine ArrayList zu speichern
	 * und den clientThread zu starten
	 * 
	 */
	
	public void acceptclients() {
		while(true){
			try {
				//Client beim Server accepten
				Socket client = server.accept();

				//Die Printwriter Objekte erstellen und in die Arraylist speichern
				PrintWriter writ = new PrintWriter(client.getOutputStream());
				list_clientWriter.add(writ);
				
				
				//Threat erstellen und Starten
				Thread clientThread = new Thread(new Clienthandler(client));		
				clientThread.start();
				
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @param message
	 * @return void
	 * 
	 * Methode um die von Clienthandler empfangene Nachrichten an alle Clients in der 
	 * list_clientWriter Liste zu senden. 
	 * 
	 */
	
	
	public void sendtoClients(String message) {
		
		
		Iterator<PrintWriter> it = list_clientWriter.iterator();
		while(it.hasNext()) {
			
			//Alle writer durchgehen
			PrintWriter writer = (PrintWriter) it.next();
			writer.println(message);
			
			//Nachricht an den Outputstream geschickt
			writer.flush();
		}
	
	}
	
	
	public void clientFilter(String message) {
		String ret = "";
		int counter = 0;
		for(int i = 0; message.length() > i; i++) {
			char c = message.charAt(i);
			counter++;
			if(c == ':') {
				i = message.length();
				counter--;
			}
		}
		
		for (int ii = 0; ii < counter; ii++) {
			char c = message.charAt(ii);
			ret += c;
		}
		if(!list_clients.contains(ret)) {
			gui.showClients(ret);
			list_clients.add(ret);
		}
	}


	
	
	public void disconnectClients() {
		try {
			server.close();
			server = new ServerSocket(5050);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * 
	 * @author Dario
	 * @version 1.0
	 * 
	 * Threat um die Nachrichten von den Clients zu erhalten und 
	 * diese wieder an alle Clients weiter leiten.
	 *  
	 */
	
	public class Clienthandler implements Runnable{
		Socket client;
		BufferedReader reader;
		
		public Clienthandler(Socket client){
			try {
				
				this.client = client;
				
				//einen Reader mit dem Client outputstream verbinden.
				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

			
			} catch (IOException e) {
				System.out.println("fehler beider Threat Erstellung");
				e.printStackTrace();
			}
		}
		
		
		
		
		@Override
		public void run() {
			String mes;
				
			//auf messages Warten
			try {
				while((mes = reader.readLine()) != null) {
					
					//An alle Clients die erhaltene Nachricht
					if(mes != "EXIT") {
						sendtoClients(mes);
						clientFilter(mes);
						gui.texttogui(mes);
					}else {
						
					}
				}
	
				
			} catch (IOException e) {
			
				e.printStackTrace();
			}		
		}	
	}
}
