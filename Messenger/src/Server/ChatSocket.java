package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatSocket extends Thread
{
	private Server server;

	private Socket connectedUserSocket;
	private Socket requestedUserSocket;

	private BufferedWriter cUWriter;
	private BufferedReader cUReader;
	private BufferedWriter rUWriter;
	private BufferedReader rUReader;

	public ChatSocket(int userID, String pRequestedUser, Server server)
	{
		this.server = server;		
		connectedUserSocket = this.server.getUserSocket(userID);
		requestedUserSocket = this.server.getUserSocket(pRequestedUser);	

		Thread connectedUser = new Thread(this);
		connectedUser.setName("connectedUser");
		connectedUser.start();

		Thread requestedUser = new Thread(this);
		requestedUser.setName("requestedUser");
		requestedUser.start();
	}

	public void run()
	{
		if(Thread.currentThread().getName().equals("connectedUser"))
		{
			try
			{
				//Writer erstellen
				OutputStream out = connectedUserSocket.getOutputStream(); 
				OutputStreamWriter writer = new OutputStreamWriter(out); 
				cUWriter = new BufferedWriter(writer); 

				//Reader erstellen
				InputStream in = connectedUserSocket.getInputStream(); 
				InputStreamReader reader = new InputStreamReader(in); 
				cUReader = new BufferedReader(reader);
				
				Thread.sleep(1000);
				
				while(true)
				{		
					rUWriter.write(cUReader.readLine());
					rUWriter.newLine();
					rUWriter.flush();
				}

			}
			catch (IOException e)
			{
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Automatisch generierter Erfassungsblock
				e.printStackTrace();
			}
		}
		else if(Thread.currentThread().getName().equals("requestedUser"))
		{
			try
			{
				//Writer erstellen
				OutputStream out = requestedUserSocket.getOutputStream(); 
				OutputStreamWriter writer = new OutputStreamWriter(out); 
				rUWriter = new BufferedWriter(writer); 

				//Reader erstellen
				InputStream in = requestedUserSocket.getInputStream(); 
				InputStreamReader reader = new InputStreamReader(in); 
				rUReader = new BufferedReader(reader);
				
				Thread.sleep(1000);
				
				while(true)
				{					
					cUWriter.write(rUReader.readLine());
					cUWriter.newLine();
					cUWriter.flush();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Automatisch generierter Erfassungsblock
				e.printStackTrace();
			}
		}
	}


}
