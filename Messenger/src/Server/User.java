package Server;

import java.net.Socket;

public class User 
{
	private Socket readyForConnection;
	private String name;
	private CommandSocket commandSocket;
	private int userID;
	
	public User(String name, int userID, CommandSocket commandSocket)
	{
		this.name = name;
		this.userID = userID;
		this.commandSocket = commandSocket;
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
	
	public CommandSocket getCommandSocket()
	{
		return commandSocket;
	}
	
	public int getID()
	{
		return userID;
	}

}
