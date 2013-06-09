package Client;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


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

	/**
	* Auto-generated main method to display this JFrame
	*/
		
	public FrmChat() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				jEditorPane1 = new JEditorPane();
				getContentPane().add(jEditorPane1);
				jEditorPane1.setBounds(0, 0, 589, 185);
				jEditorPane1.setEditable(false);
			}
			{
				jEditorPane2 = new JEditorPane();
				getContentPane().add(jEditorPane2);
				jEditorPane2.setBounds(0, 212, 589, 191);
			}
			pack();
			this.setSize(605, 441);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

}
