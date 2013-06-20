package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class CommandSocket extends Thread
{
	private Socket clientCommandSocket;
	private BufferedWriter bufWriter;
	private BufferedReader bufReader;
	private Server server;
	private int userID;

	//In dieser neuen Instanz wird ein neuer Thread gestartet, da es beliebig viele CommandSockets geben können muss.
	public CommandSocket(Socket socket, int userCount, Server server)
	{
		this.userID = userCount;
		this.clientCommandSocket = socket;
		this.server = server;
		new Thread(this).start();
	}

	public void run()
	{
		while(true)
		{
			try
			{
				//Writer erstellen
				OutputStream out = clientCommandSocket.getOutputStream(); 
				OutputStreamWriter writer = new OutputStreamWriter(out); 
				bufWriter = new BufferedWriter(writer); 

				//Reader erstellen
				InputStream in = clientCommandSocket.getInputStream(); 
				InputStreamReader reader = new InputStreamReader(in); 
				bufReader = new BufferedReader(reader);


				String command = bufReader.readLine();

				switch(command)
				{
				case "connect": 
					server.addToList(new User(bufReader.readLine(), userID, this));
					
					bufWriter.write(userID);
					bufWriter.flush();
					
					break;
				case "update":
					ArrayList<String> list = server.getUserNameList();
					list.remove(userID);

					bufWriter.write(list.size());
					bufWriter.flush();

					for(int i = 0; i < list.size(); i++)
					{
						bufWriter.write(list.get(i));
						bufWriter.newLine();
						bufWriter.flush();
					}
					
					server.clientJoined(userID);
					break;
				case "connectTo":
					String requestedUser = bufReader.readLine();
					int userID = bufReader.read();
					
					openChatSocket(userID, requestedUser);
					server.getUserCommandSocket(requestedUser).giveCommand("GetConnected");
					
				}		

			}
			catch (IOException e)
			{
				System.exit(0);
				e.printStackTrace();
			}
		}
	}
	
	public void clientJoined(String username) throws IOException
	{
		giveCommand("new User");
		giveCommand(username);
	}
	
	private void openChatSocket(int userID, String requestedUser)
	{
		ChatSocket chatSocket = new ChatSocket(userID, requestedUser, server);
	}
	
	private void giveCommand(String command) throws IOException
	{
		bufWriter.write(command);
		bufWriter.newLine();
		bufWriter.flush();
	}

}
