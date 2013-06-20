package Client;

import java.io.IOException;

public class MainClient 
{
	public static void main(String[] args) throws IOException 
	{
		FrmClient frmClient = new FrmClient("User 1");
		frmClient.setVisible(true);
		
		FrmClient frmClient2 = new FrmClient("User 2");
		frmClient2.setVisible(true);
		
	}

}
