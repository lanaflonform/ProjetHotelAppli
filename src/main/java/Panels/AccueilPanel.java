package Panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import Vues.Vue.PANEL;

public class AccueilPanel extends HotelPanel {

	public enum ADMINISTRATEURS { FACTURATION, SUPREME, RESERVATION, CLIENTELE } ;
	
	// INTERARGIT PAR DATABASE
	private boolean DROIT_SUPREME = true;
	private boolean DROIT_FACTURATION = false;
	private boolean DROIT_RESERVATION = false;
	private boolean DROIT_CLIENTELE = false;
	private PANEL prochainPanel = null;
	
	public AccueilPanel() {
		super();	
		m_mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 50));
		m_mainPanel.setPreferredSize(new Dimension(LONGUEUR, LARGEUR));
		construireBoutons();
	}
	
	private void verifierDroit () 
	{
		if (DROIT_SUPREME)
		{
			for (JButton bouton : m_boutons)
				bouton.setEnabled(true);
		}
		else
		{
			if (DROIT_FACTURATION)
				m_boutons.get(ADMINISTRATEURS.FACTURATION.ordinal()).setEnabled(true);
			if (DROIT_RESERVATION)
				m_boutons.get(ADMINISTRATEURS.RESERVATION.ordinal()).setEnabled(true);
			if (DROIT_CLIENTELE)
				m_boutons.get(ADMINISTRATEURS.CLIENTELE.ordinal()).setEnabled(true);
		}
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
		
		for (JButton bouton : m_boutons)
		{
			bouton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					deciderPanel((JButton) arg0.getSource());
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					
				}
				@Override
				public void mouseEntered(MouseEvent e) {

					
				}
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			} );
		}	
		verifierDroit();
	}
	
	public void deciderPanel (JButton boutonPressed)
	{
		String nomPanel = boutonPressed.getText().substring("Admin ".length());
		switch (nomPanel)
		{
			case "Clientele" :
				prochainPanel = PANEL.CLIENTELE;
				break;
			case "Facturation" :
				prochainPanel = PANEL.FACTURATION;
				break;
			case "Supreme" :
				prochainPanel = PANEL.SUPREME;
				break;
			case "Reservation" :
				prochainPanel = PANEL.RESERVATION;
				break;
			default :
				System.err.println("Error while deciding panel.");
		}
		m_termine = true;
		
	}
	
	public PANEL getProchainPanel()
	{
		if (prochainPanel != null)
			return prochainPanel;
		System.err.println("Error while deciding panel");
		return null;
	}

	@Override
	public boolean fonctionne() {
		return !m_termine;
	}
}
