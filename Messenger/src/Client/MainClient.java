package Client;

import java.io.IOException;
import java.net.InetAddress;

public class MainClient 
{
	/**
	 * Erzeugt die grafische Oberfläche des Users
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException 
	{		
		FrmClient frmClient = new FrmClient(InetAddress.getLocalHost().toString());
		frmClient.setVisible(true);		
		
		FrmClient frmClient2 = new FrmClient("User 2");
		frmClient2.setVisible(true);	
	}

}
