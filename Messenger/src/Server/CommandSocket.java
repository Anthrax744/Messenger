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

	private boolean connected = true;

	//In dieser neuen Instanz wird ein neuer Thread gestartet, da es beliebig viele CommandSockets geben können muss.
	public CommandSocket(Socket socket, int userID, Server server)
	{
		this.clientCommandSocket = socket;
		this.userID = userID;		
		this.server = server;
		new Thread(this).start();
	}

	public void run()
	{
		while(connected)
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
				//Wenn der Befehlt "login" kommt
				case "login": 
					synchronized(this) //Solange ein User dabei ist sich einzuloggen, darf kein anderer sich einloggen.
					{
						//Wird der username empfangen
						String username = bufReader.readLine();
						//und ein neuer User mit diesem Namen und der ClientID angelegt.
						User u = new User(username, userID, this);

						//Dem User wird dann seine ID mitgeteilt.			
						bufWriter.write(userID);
						bufWriter.flush();
						
						//Er bekommt eine Liste aller eingeloggten User
						ArrayList<String> UserList = server.getUserNameList();
						bufWriter.write(UserList.size());
						bufWriter.flush();

						for(int i = 0; i < UserList.size(); i++)
						{
							bufWriter.write(UserList.get(i));
							bufWriter.newLine();
							bufWriter.flush();
						}
						
						//Und wird danach selbst zu dieser Liste hinzugefügt.
						server.addToList(u);
						
						//Anschließend sender der User"startedListening" um sicherzugehen, dass zukünftige Befehle auch ankommen.
						bufReader.readLine(); 
						
						//Die anderen User werden über den neuen User benachrichtigt.
						server.userLoggedIn(userID, username);
						break;
					}
				case "connectTo":
					String requestedUser = bufReader.readLine();
					int userID = bufReader.read();

					openChatSocket(userID, requestedUser);
					server.getUserCommandSocket(requestedUser).giveCommand("GetConnected");

				}		

			}
			catch (IOException e)
			{
				connected = false;
				e.printStackTrace();
			}
		}
	}

	public void userJoined(String username) throws IOException
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
