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
	
	public Client(String user) throws IOException 
	{
		this.user = user;
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
		commandSocket = new Socket("localhost", 9090);

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
		chatSocket = new Socket("localhost", 2020);
		
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
			
		
	}
	
	public void run()
	{
		while(true)
		{
			try 
			{
				commandReader.readLine();
				new ChatSocket(chatSocket);
				
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


