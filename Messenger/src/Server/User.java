package Server;

import java.net.Socket;

public class User 
{
	private Socket readyForConnection;
	private String name;
	private int userID;
	
	public User(String name, int userID)
	{
		this.name = name;
		this.userID = userID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Socket getSocket()
	{
		return readyForConnection;
	}
	
	public void setSocket(Socket socket)
	{
		this.readyForConnection = socket;
	}

}
