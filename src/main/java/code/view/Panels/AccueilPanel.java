package code.view.Panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import code.view.Vues.Vue.PANEL;

public class AccueilPanel extends HotelPanel {

	public enum ADMINISTRATEURS { FACTURATION, SUPREME, RESERVATION, CLIENTELE } ;
	private PANEL m_prochainPanel;
	
	public AccueilPanel() {
		super();	
		m_mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 50));
		m_mainPanel.setPreferredSize(new Dimension(LONGUEUR, LARGEUR));
		construireBoutons();
	}
	
	private void construireBoutons()
	{
		JButton boutonSupreme, boutonFacturation, boutonReservation, boutonClientele;
		
		boutonFacturation = new JButton("Admin Facturation");
		boutonFacturation.setEnabled(false);
		m_boutons.add(boutonFacturation);
		m_mainPanel.add(boutonFacturation);
		
		
		boutonSupreme = new JButton("Admin Supreme");
		boutonSupreme.setEnabled(false);
		m_boutons.add(boutonSupreme);
		m_mainPanel.add(boutonSupreme);
		
		boutonReservation = new JButton("Admin Reservation");
		boutonReservation.setEnabled(false);
		m_boutons.add(boutonReservation);
		m_mainPanel.add(boutonReservation);
		
		boutonClientele = new JButton("Admin Clientele");
		boutonClientele.setEnabled(false);
		m_boutons.add(boutonClientele);
		m_mainPanel.add(boutonClientele);
	}
	
	public PANEL getProchainPanel()
	{
		if (m_prochainPanel != null)
			return m_prochainPanel;
		System.err.println("Error while deciding panel");
		return null;
	}
	
	public void setProchainPanel (PANEL prochainPanel)
	{
		m_prochainPanel = prochainPanel;
	}

	@Override
	public boolean fonctionne() {
		return !m_termine;
	}
}
