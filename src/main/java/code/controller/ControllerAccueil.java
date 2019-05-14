package code.controller;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;

import code.Admin;
import code.model.DAOInterfaces.DAOAdmin;
import code.model.DAOJDBC.DAOAdminJDBC;
import code.view.Panels.AccueilPanel;
import code.view.Panels.AccueilPanel.ADMINISTRATEURS;
import code.view.Vues.Vue;
import code.view.Vues.Vue.PANEL;

public class ControllerAccueil extends AbstractController {

	private ArrayList <JButton> m_boutons = new ArrayList <JButton> ();
	private AccueilPanel  m_panel;
	private Admin m_Admin;
	private boolean DROIT_SUPREME;
	private boolean DROIT_FACTURATION;
	private boolean DROIT_RESERVATION;
	private boolean DROIT_CLIENTELE;
	//peut-être inutile
	private DAOAdmin daoAdmin;
	
	public ControllerAccueil(Vue vue, Admin admin) {
		super(vue);
		System.out.println("jfk");
		m_Admin = admin;
		initController();
		daoAdmin = new DAOAdminJDBC();
		
	}
	
	public void recupererDroits() // ToDo
	{
		DROIT_SUPREME = m_Admin.getDroits().get("Supreme");
		DROIT_FACTURATION = m_Admin.getDroits().get("Facturation");
		DROIT_RESERVATION = m_Admin.getDroits().get("Reservation");
		DROIT_CLIENTELE = m_Admin.getDroits().get("Clientele");
	}

	@Override
	public void initController() 
	{
		m_panel = (AccueilPanel) m_vue.getPanel(PANEL.ACCUEIL);
		m_boutons = m_panel.getBoutons();
		
		for (JButton bouton : m_boutons)
			bouton.addActionListener(e -> creerProchainControleur(bouton));
		recupererDroits();
		verifierDroits();
	}
	
	public void creerProchainControleur(JButton bouton)
	{
		String nomPanel = bouton.getText().substring("Admin ".length());
		switch (nomPanel)
		{
			case "Clientele" :
				m_panel.setProchainPanel(PANEL.CLIENTELE);
				new ControllerClientele(m_vue);
				break;
			case "Facturation" :
				m_panel.setProchainPanel(PANEL.FACTURATION);
				new ControllerFacturation(m_vue);
				break;
			case "Supreme" :
				m_panel.setProchainPanel(PANEL.SUPREME);
				new ControllerSupreme(m_vue);
				break;
			case "Reservation" :
				m_panel.setProchainPanel(PANEL.RESERVATION);
				new ControllerReservation(m_vue);
				break;
			default :
				System.err.println("Eohrror while deciding panel.");
		}
		m_panel.setTermine(true);
	}
	
	private void verifierDroits () 
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

}
