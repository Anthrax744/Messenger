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
	private BufferedWriter commandWriter;
	private BufferedReader commandReader;
	private BufferedWriter chatWriter;
	private BufferedReader chatReader;
	private int clientID;
	private Socket commandSocket;
	private Socket chatSocket;
	private String user;
	private FrmClient frmClient;

	public Client(String user, FrmClient frmClient) throws IOException 
	{
		this.user = user;
		this.frmClient = frmClient;
		openCommandSocket();
		connect();
		openChatSocket();
	}



	public void connect() throws UnknownHostException, IOException
	{
		commandWriter.write("connect");
		commandWriter.newLine();
		commandWriter.flush();

		commandWriter.write(user);
		commandWriter.newLine();
		commandWriter.flush();

		clientID = commandReader.read();
	}


	public String[] updateUsers() throws UnknownHostException, IOException
	{				
		commandWriter.write("update");
		commandWriter.newLine();
		commandWriter.flush();


		int nameCount = commandReader.read();
		String[] list = new String[nameCount];

		for(int i = 0; i<nameCount; i++)
		{
			list[i] = commandReader.readLine();
		}

		return list;
	}


	public void openCommandSocket() throws IOException
	{
		commandSocket = new Socket("Clemens-Laptop.fritz.box", 9090);

		//Writer erstellen
		OutputStream out = commandSocket.getOutputStream(); 
		OutputStreamWriter writer = new OutputStreamWriter(out); 
		commandWriter = new BufferedWriter(writer); 

		//Reader erstellen
		InputStream in = commandSocket.getInputStream(); 
		InputStreamReader reader = new InputStreamReader(in); 
		commandReader = new BufferedReader(reader);	
	}

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


