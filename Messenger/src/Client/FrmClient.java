package Client;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JList;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class FrmClient extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane1;
	private JList<String> jList1;
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	private Client client;

	/**
	 * Erzeugt einen neuen Client und erzeugt die grafische Oberfläche
	 * @throws IOException 
	 */

	public FrmClient(String username) throws IOException {

		super();
		initGUI(username);
		
		client = new Client(username, this);
	}

	private void initGUI(String username) {
		try {
			this.setTitle(username);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(299, 22, 245, 272);
				{
					jList1 = new JList<String>(dlm);
					jScrollPane1.setViewportView(jList1);
					jList1.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							try {
								jList1MouseClicked(evt);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
			pack();
			this.setSize(580, 388);
		} catch (Exception e) {
			//add your error handling code here
			e.printStackTrace();
		}
	}

	public void updateList(String[] list)
	{
		for (int i = 0; i < list.length; i++) 
		{
			dlm.addElement(list[i]);
		}
		
		jList1.setModel(dlm);
	}
	
	private void jList1MouseClicked(MouseEvent evt) throws IOException 
	{
		if(evt.getClickCount() == 2)
		{
			client.connectToChatPartner(jList1.getSelectedValue());
		}
	}
	
	public void addUser(String username)
	{
		DefaultListModel<String> listModel = (DefaultListModel<String>) jList1.getModel();
		listModel.addElement(username);
		jList1.setModel(listModel);
	}
}


