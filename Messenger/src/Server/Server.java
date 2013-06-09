package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread
{
	private ServerSocket commandServerSocket;
	private Socket commandSocket;
	
	private ServerSocket chatServerSocket;
	
	private BufferedReader bufReader;

	private ArrayList<User> userList= new ArrayList<User>();
	private int userCount = 0;


	public Server() throws IOException
	{
		commandServerSocket = new ServerSocket(9090);
		
		Thread commandThread = new Thread(this);
		commandThread.setName("command");
		commandThread.start();		
		
		
		chatServerSocket = new ServerSocket(2020);
		
		Thread chatThread = new Thread(this);
		chatThread.setName("chat");		
		chatThread.start();
		
	}	
	
	public void addToList(User user)
	{
		userList.add(user);
	}
	
	
	public String[] getUserNameList()
	{
		String[] nameList = new String[userList.size()];
		
		for(int i = 0; i< userList.size(); i++)
		{
			nameList[i] = userList.get(i).getName();
		}
		
		return nameList;
	}	

	@Override
	public void run()
	{
		try
		{
			if(Thread.currentThread().getName().equals("command"))
			{
				while(true)
				{
					commandSocket = commandServerSocket.accept();					
					CommandSocket commandClass = new CommandSocket(commandSocket, userCount, this);
					userCount++;
				}

			}			
			else if(Thread.currentThread().getName().equals("chat"))
			{
				while(true)
				{
					Socket readyForConnection = new Socket();
					readyForConnection = chatServerSocket.accept();
					//Reader erstellen
					InputStream in = readyForConnection.getInputStream(); 
					InputStreamReader reader = new InputStreamReader(in); 
					bufReader = new BufferedReader(reader);
										
					int userID = bufReader.read();
					
					userList.get(userID).setSocket(readyForConnection);
				}

			}
			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public Socket getUserSocket(String username)
	{
		for(User u : userList)
		{
			if(u.getName().equals(username))
			{
				return u.getSocket();
			}
		}
		return null;
	}
	
	public Socket getUserSocket(int userID)
	{
		return userList.get(userID).getSocket();
	}


}
