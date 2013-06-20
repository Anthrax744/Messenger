package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import Server.CommandSocket;

public class ChatSocket extends Thread
{
	private Socket chatSocket = new Socket();
	private BufferedWriter chatWriter;
	private BufferedReader chatReader;
	private FrmChat frmChat = new FrmChat(this);
	
	
	public ChatSocket(Socket chatSocket) throws IOException
	{
		this.chatSocket = chatSocket;

		//Writer erstellen
		OutputStream out = chatSocket.getOutputStream(); 
		OutputStreamWriter writer = new OutputStreamWriter(out); 
		chatWriter = new BufferedWriter(writer); 

		//Reader erstellen
		InputStream in = chatSocket.getInputStream(); 
		InputStreamReader reader = new InputStreamReader(in); 
		chatReader = new BufferedReader(reader);

		Thread read = new Thread(this);
		read.setName("read");
		read.start();		

		Thread write = new Thread(this);
		write.setName("write");		
		write.start();	

		frmChat.setVisible(true);
	}

	public void run()
	{
		try
		{
			if(Thread.currentThread().getName().equals("read"))
			{
				while(true)
				{
					frmChat.addLine(chatReader.readLine());
				}

			}			
			else if(Thread.currentThread().getName().equals("write"))
			{
				while(true)
				{
					String actLine = frmChat.getLine();
					Thread.sleep(1000);
					if(actLine != null)
					{

						chatWriter.write(actLine);
						chatWriter.newLine();
						chatWriter.flush();
					}

				}
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


