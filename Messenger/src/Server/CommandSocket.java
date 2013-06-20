package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

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
					String[] list = server.getUserNameList();

					bufWriter.write(list.length);
					bufWriter.flush();

					for(int i = 0; i < list.length; i++)
					{
						bufWriter.write(list[i]);
						bufWriter.newLine();
						bufWriter.flush();
					}
					break;
				case "connectTo":
					String requestedUser = bufReader.readLine();
					int userID = bufReader.read();
					
					openChatSocket(userID, requestedUser);
					server.getUserCommandSocket(requestedUser).writeStuff();
					
				}		

			}
			catch (IOException e)
			{
				System.exit(0);
				e.printStackTrace();
			}
		}
	}
	
	private void openChatSocket(int userID, String requestedUser)
	{
		ChatSocket chatSocket = new ChatSocket(userID, requestedUser, server);
	}
	
	private void writeStuff() throws IOException
	{
		bufWriter.write("random Stuff");
		bufWriter.newLine();
		bufWriter.flush();
	}

}
