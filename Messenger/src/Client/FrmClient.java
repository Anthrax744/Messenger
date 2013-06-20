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
	private DefaultListModel dlm = new DefaultListModel();
	private Client client;

	/**
	 * Auto-generated main method to display this JFrame
	 * @throws IOException 
	 */

	public FrmClient(String user) throws IOException {

		super();
		client = new Client(user, this);
		initGUI(user);
	}

	private void initGUI(String user) {
		try {
			this.setTitle(user);
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			getContentPane().setLayout(null);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					try {
						thisWindowClosing(evt);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				public void windowOpened(WindowEvent evt) {
					try {
						thisWindowOpened(evt);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(299, 22, 245, 272);
				{
					jList1 = new JList<String>();
					jScrollPane1.setViewportView(jList1);
					jList1.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							try {
								jList1MouseClicked(evt);
							} catch (IOException e) {
								// TODO Automatisch generierter Erfassungsblock
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

	private void thisWindowOpened(WindowEvent evt) throws UnknownHostException, IOException 
	{
		updateList(client.updateUsers());	
		client.startListening();
	}

	public void updateList(String[] list)
	{
		for (int i = 0; i < list.length; i++) 
		{
			dlm.addElement(list[i]);
		}
		
		jList1.setModel(dlm);
	}
	
	private void thisWindowClosing(WindowEvent evt) throws UnknownHostException, IOException 
	{
		
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
		DefaultListModel listModel = (DefaultListModel) jList1.getModel();
		listModel.addElement(username);
		jList1.setModel(listModel);
	}
}


