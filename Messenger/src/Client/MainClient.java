package Client;

import java.io.IOException;
import java.net.InetAddress;

public class MainClient 
{
	public static void main(String[] args) throws IOException 
	{		
		FrmClient frmClient = new FrmClient(InetAddress.getLocalHost().toString());
		frmClient.setVisible(true);
		
		FrmClient frmClient2 = new FrmClient("User 2");
		frmClient2.setVisible(true);
		
		
	}

}
