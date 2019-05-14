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
	private HotelPanel panelCourant;
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
		panelCourant = getPanel(PANEL.CONNECTION);
		while (true)
		{
			if (panelCourant.equals(getPanel(PANEL.CONNECTION)))
				m_menu.setEnabled(false);
			else
				m_menu.setEnabled(true);
			this.add(panelCourant);
			pack();
			while (panelCourant.fonctionne())
			{
				if (m_connecte == ETAT_CONNECTION.DISCONNECTED)
				{
					redemarrer();
					return;
				}
				if (m_retour)
					break;
			}
			this.remove(panelCourant);
			int retourTransition = transition();
			if (retourTransition == - 1)
				return;
			panelCourant = m_panels.get(retourTransition);
		}
	}
	
	public int transition()
	{
		if (m_connecte == ETAT_CONNECTION.DISCONNECTED)
		{
			redemarrer();
			return -1;
		}
		else if (panelCourant.equals(getPanel(PANEL.CONNECTION)))
		{
			m_connecte = ETAT_CONNECTION.CONNECTED;			
			return PANEL.ACCUEIL.ordinal();
		}
		else if (panelCourant.equals(getPanel(PANEL.ACCUEIL)))
		{
			if (m_retour)
			{
				redemarrer();
				return -1;
			}
			return ((AccueilPanel) panelCourant).getProchainPanel().ordinal();
		}
		else if (panelCourant.equals(getPanel(PANEL.FACTURATION)))
		{
			if (m_retour)
				return PANEL.ACCUEIL.ordinal();
		}
		m_retour = false;
		System.err.println("Error in transition");
		return 0;
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
	
	public void redemarrer()
	{
		for (int i = 0; i < m_panels.size(); i++)
			m_panels.remove(i);
		m_connecte = ETAT_CONNECTION.UNDEFINED;
		m_retour = false;
		construirePanels();
		deroulement();
	}
	
	public HotelPanel getPanelCourant()
	{
		return panelCourant;
	}
	
	public HotelPanel getPanel(PANEL typePanel)
	{
		return m_panels.get(typePanel.ordinal());
	}
	
}
