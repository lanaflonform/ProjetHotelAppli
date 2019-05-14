package code.view.Vues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import code.view.Panels.AccueilPanel;
import code.view.Panels.ClientelePanel;
import code.view.Panels.ConnectionPanel;
import code.view.Panels.FacturationPanel;
import code.view.Panels.HotelPanel;
import code.view.Panels.ReservationPanel;
import code.view.Panels.SupremePanel;


public class Vue extends JFrame {
	
	public enum PANEL { CONNECTION, ACCUEIL, CLIENTELE, FACTURATION, RESERVATION, SUPREME, RETOUR };
	public enum ETAT_CONNECTION { UNDEFINED, CONNECTED, DISCONNECTED };
	
	private ArrayList <HotelPanel> m_panels = new ArrayList <HotelPanel> ();
	private JMenu m_menu;
	private HotelPanel m_panelCourant;
	private ETAT_CONNECTION m_connecte = ETAT_CONNECTION.UNDEFINED;
	private boolean m_retour = false;
	
	
	public Vue ()
	{
		construirePanels();
		construireMenuBarre();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);	
	}
	
	public void deroulement()
	{
		m_panelCourant = getPanel(PANEL.CONNECTION);
		this.add(m_panelCourant);
		pack();
		while (true)
		{
			while (m_panelCourant.fonctionne())
			{
				if ( m_connecte == ETAT_CONNECTION.DISCONNECTED || m_retour)
					break;
			}
			this.remove(m_panelCourant);
			int retourTransition = transition();
			m_panelCourant = m_panels.get(retourTransition);
			this.add(m_panelCourant);
			pack();
		}
	}
	
	public int transition()
	{
		int returnValue = -1;
		m_panelCourant.setTermine(false);
		if (m_connecte == ETAT_CONNECTION.DISCONNECTED)
		{
			m_menu.setEnabled(false);
			returnValue = PANEL.ACCUEIL.ordinal();
		}
		else if (m_panelCourant.equals(getPanel(PANEL.CONNECTION)))
		{
			m_connecte = ETAT_CONNECTION.CONNECTED;	
			returnValue = PANEL.ACCUEIL.ordinal();
			m_menu.setEnabled(true);
		}
		else if (m_panelCourant.equals(getPanel(PANEL.ACCUEIL)))
		{
			if (m_retour)
			{
				m_menu.setEnabled(false);
				returnValue = PANEL.ACCUEIL.ordinal();
			}
			else
				returnValue =  ((AccueilPanel) m_panelCourant).getProchainPanel().ordinal();
		}
		else if (m_panelCourant.equals(getPanel(PANEL.FACTURATION)))
		{
			if (m_retour)
				returnValue = PANEL.ACCUEIL.ordinal();
			else
				System.err.println("Error in transition : return value is FACTURATION");
		}
		else if (m_panelCourant.equals(getPanel(PANEL.RESERVATION)))
		{
			if (m_retour)
				returnValue = PANEL.ACCUEIL.ordinal();
			else
				System.err.println("Error in transition : return value is RESERVATION");
		}
		m_retour = false;
		if (returnValue == -1)
			System.err.println("Error in transition");
		return returnValue;
	}

	private void construirePanels ()
	{
		m_panels.add(new ConnectionPanel());
		m_panels.add(new AccueilPanel());
		m_panels.add(new ClientelePanel());
		m_panels.add(new FacturationPanel());
		m_panels.add(new ReservationPanel());
		m_panels.add(new SupremePanel());
	}
	
	private void construireMenuBarre()
	{
		m_menu = new JMenu ("Session");
		JMenuItem deconnectionItem = new JMenuItem("Deconnection");
		JMenuItem retourItem = new JMenuItem("Retour");
		final Vue thisFrame = this;
		deconnectionItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.m_connecte = ETAT_CONNECTION.DISCONNECTED;
				System.out.println("Disconnect");
			}
		});
		retourItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.m_retour = true;
			}
		});
		
		JMenuBar barre = new JMenuBar();
		m_menu.add(deconnectionItem);
		m_menu.add(retourItem);
		barre.add(m_menu);
		setJMenuBar(barre);
	}
	
	public HotelPanel getPanelCourant()
	{
		return m_panelCourant;
	}
	
	public HotelPanel getPanel(PANEL typePanel)
	{
		return m_panels.get(typePanel.ordinal());
	}
	
}
