package BrucknerD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author Dario
 * @version 1.03
 * 
 * Klasse ChatServerSocket wird verwedet um alle Serverfunktionen auszufuehren
 * 
 */


public class ChatServerSocket {
	ServerSocket server;
	Thread r, clientThread;
	static boolean running = true;
	Socket client;
	SimpleChatServer serv;
	ArrayList<PrintWriter> list_clientWriter;
	ArrayList<String> list_clients = new ArrayList<String>();

	
	/**
	 * Standartconstructor
	 */
	public ChatServerSocket() {
		
	}
	
	
	
	/**
	 *
	 * @param serv
	 *
	 * Constructor um zu ueberpruefen ob der Server richtig gestartet worden ist
	 * 
	 */
	
	public ChatServerSocket(SimpleChatServer serv) {
		this.serv = serv;

		if(server()) {

			acceptclients();
	  	}else {
	  		System.exit(0);
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



		r = new Thread() {
			@Override
			public void run() {
				while(running){
					try {
						//Client beim Server accepten
						try {
							client = server.accept();
						}catch(SocketException e){
							return;
						}
						//Die Printwriter Objekte erstellen und in die Arraylist speichern
						PrintWriter writ = new PrintWriter(client.getOutputStream());
						list_clientWriter.add(writ);


						//Threat erstellen und Starten
						clientThread = new Thread(new Clienthandler(client));
						clientThread.start();

					}catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		r.start();


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
	
	
	public String clientFilter(String message) {
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
			list_clients.add(ret);
			serv.showClients(list_clients);
		}

		return ret;
	}

	public void shutdown(){
		running = false;
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


			}
		}
		
		
		
		
		@Override
		public void run() {
			String mes;
			String clientdel;

			//auf messages Warten
			try {
				try {
					while(running) {
						try{
							mes = reader.readLine();
							clientdel = clientFilter(mes);

						}catch (SocketTimeoutException e) {
							continue;
						}
						//An alle Clients die erhaltene Nachricht
						if (!mes.equals(clientdel + ": EXIT")) {
							sendtoClients(mes);
							clientFilter(mes);
							serv.texttogui(mes);
						} else if(mes.equals(clientdel + ": EXIT")) {
							list_clients.remove(clientdel);
							serv.showClients(list_clients);
						}
					}
				}catch(SocketException e){
					return;
				}


			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
}
