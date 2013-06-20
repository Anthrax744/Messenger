package Client;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


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
public class FrmChat extends javax.swing.JFrame {
	private JEditorPane jEditorPane1;
	private JEditorPane jEditorPane2;
	private JButton jButton1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane1;
	private boolean newLine = false;
	private ChatSocket chatSocket;
	private String actLine = null;

	/**
	 * Auto-generated main method to display this JFrame
	 */

	public FrmChat(ChatSocket chatSocket) {
		super();
		this.chatSocket = chatSocket;
		initGUI();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("Send");
				jButton1.setBounds(469, 369, 81, 23);
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(0, 0, 589, 178);
				{
					jEditorPane1 = new JEditorPane();
					jScrollPane1.setViewportView(jEditorPane1);
					jEditorPane1.setBounds(0, 311, 589, 21);
					jEditorPane1.setEditable(false);
					jEditorPane1.setPreferredSize(new java.awt.Dimension(541, 160));
				}
			}
			{
				jScrollPane2 = new JScrollPane();
				getContentPane().add(jScrollPane2);
				jScrollPane2.setBounds(0, 198, 589, 159);
				{
					jEditorPane2 = new JEditorPane();
					jScrollPane2.setViewportView(jEditorPane2);
					jEditorPane2.setBounds(0, 344, 589, 15);
					jEditorPane2.setPreferredSize(new java.awt.Dimension(551, 141));
				}
			}
			pack();
			this.setSize(605, 441);
		} catch (Exception e) {
			//add your error handling code here
			e.printStackTrace();
		}
	}

	public void addLine(String line)
	{
		Document doc = jEditorPane1.getDocument();
		try
		{
			doc.insertString(doc.getLength(), line+"\n", null);
		}
		catch(BadLocationException e)
		{
			System.out.println("War wohl nix");
		}
	}

	private void jButton1ActionPerformed(ActionEvent evt) 
	{
		actLine = jEditorPane2.getText();
	}

	public String getLine()
	{
//		System.out.println(actLine);
		
		
		if(actLine != null)
		{
			jEditorPane2.setText("");
			addLine(actLine);
			String temp = actLine;
			actLine = null;
			return temp;
		}
		else
		{
			return null;
		}
	}

}
