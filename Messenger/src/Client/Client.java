package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Server.CommandSocket;

public class Client extends Thread 
{
	private String username; //PC-Name/IP. Wird später automatisch zugewiesen.
	private FrmClient frmClient; //Übergeben zum Methodenaufruf.
	private int clientID;

	private BufferedWriter commandWriter;
	private BufferedReader commandReader;
	private BufferedWriter chatWriter;
	private BufferedReader chatReader;

	private Socket commandSocket;
	private Socket chatSocket;



	/**
	 * Öffnet als erstes die CommandSocket und sendet den Befehlt "connect". Öffnet anschließend die ChatSocket.
	 * @param user
	 * @param frmClient
	 * @throws IOException
	 */
	public Client(String username, FrmClient frmClient) throws IOException 
	{
		this.username = username;
		this.frmClient = frmClient;

		openCommandSocket(); //zuerst muss die Verbindug zum Server über die CommandSocket aufgebaut werden. Anschließend können Befehle gesendet werden.
		logIn();			//Nun "loggt" sich der User auf dem Server ein und empfängt seine ID


		//		openChatSocket();
	}

	public void openCommandSocket() throws IOException
	{
		commandSocket = new Socket("localhost", 9090); //Statt localhost später evt. Clemens-Laptop.fritz.box

		//Writer erstellen
		OutputStream out = commandSocket.getOutputStream(); 
		OutputStreamWriter writer = new OutputStreamWriter(out); 
		commandWriter = new BufferedWriter(writer); 

		//Reader erstellen
		InputStream in = commandSocket.getInputStream(); 
		InputStreamReader reader = new InputStreamReader(in); 
		commandReader = new BufferedReader(reader);	
	}

	public void logIn() throws UnknownHostException, IOException
	{
		//Zuerst wird der Befehl "login" gesendet.
		commandWriter.write("login");
		commandWriter.newLine();
		commandWriter.flush();

		//Anschließend wird der Username übergeben
		commandWriter.write(username);
		commandWriter.newLine();
		commandWriter.flush();

		//Und die zugewiesene ID wird empfangen.
		clientID = commandReader.read();
		
		int nameCount = commandReader.read();
		String[] list = new String[nameCount];

		for(int i = 0; i<nameCount; i++)
		{
			list[i] = commandReader.readLine();
		}
		
		frmClient.updateList(list);
		
		
		startListening();

		commandWriter.write("startedListening");
		commandWriter.newLine();
		commandWriter.flush();
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * Verbindet sich mit dem ChatServer und gibt die Client ID durch.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void openChatSocket() throws UnknownHostException, IOException
	{
		chatSocket = new Socket("Clemens-Laptop.fritz.box", 2020);

		//Writer erstellen
		OutputStream out = chatSocket.getOutputStream(); 
		OutputStreamWriter writer = new OutputStreamWriter(out); 
		chatWriter = new BufferedWriter(writer); 

		//Reader erstellen
		InputStream in = chatSocket.getInputStream(); 
		InputStreamReader reader = new InputStreamReader(in); 
		chatReader = new BufferedReader(reader);

		chatWriter.write(clientID);
		chatWriter.flush();	
	}

	public void connectToChatPartner(String username) throws IOException
	{
		commandWriter.write("connectTo");
		commandWriter.newLine();
		commandWriter.flush();

		commandWriter.write(username);
		commandWriter.newLine();
		commandWriter.flush();

		commandWriter.write(clientID);
		commandWriter.flush();

		ChatSocket chatSocketClass = new ChatSocket(chatSocket);

		openChatSocket();


	}

	public void run()
	{
		while(true)
		{
			try 
			{
				switch(commandReader.readLine())
				{
				case "GetConnected" : new ChatSocket(chatSocket);
				openChatSocket();
				break;
				case "new User": frmClient.addUser(commandReader.readLine());
				break;
				}




			} catch (IOException e) 
			{
				e.printStackTrace();
			} 

		}
	}

	public void startListening()
	{
		new Thread(this).start();
	}



}


