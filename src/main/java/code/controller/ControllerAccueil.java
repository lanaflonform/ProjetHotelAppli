package code.controller;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;

import code.Admin;
import code.TypeAcces;
import code.model.DAOInterfaces.DAOAdmin;
import code.model.DAOJDBC.DAOAdminJDBC;
import code.view.Panels.AccueilPanel;
import code.view.Vues.Vue;
import code.view.Vues.Vue.PANEL;

public class ControllerAccueil extends AbstractController {

	private ArrayList <JButton> m_boutons = new ArrayList <JButton> ();
	private AccueilPanel  m_panel;
	private Admin m_Admin;
	private DAOAdmin daoAdmin;
	
	private final String CLIENTELE = "Clientele";
	private final String RESERVATION = "Reservation";
	private final String FACTURATION = "Facturation";
	private final String SUPREME = "SUPREME";
	
	public ControllerAccueil(Vue vue, Admin admin) {
		super(vue);
		m_Admin = admin;
		initController();
		daoAdmin = new DAOAdminJDBC();
	}
	
	public void initPanel()
	{
		ArrayList <String> nomsBoutons = new ArrayList <String> ();
		for (TypeAcces acces : DAOAdminJDBC.getTypesAcces())
			nomsBoutons.add(acces.getTypeAcces());	
		m_panel.construireBoutons(nomsBoutons);
		m_boutons = m_panel.getBoutons();
		for (int i = 0; i < m_boutons.size(); i++)
			m_boutons.get(i).addActionListener(e -> creerProchainControleur((JButton) e.getSource()));
	}
	@Override
	public void initController() 
	{
		m_panel = (AccueilPanel) m_vue.getPanel(PANEL.ACCUEIL);
		initPanel();
		verifierDroits();
	}
	
	public void creerProchainControleur(JButton bouton)
	{
		String nomPanel = bouton.getText();
		switch (nomPanel)
		{
			case CLIENTELE :
				m_panel.setProchainPanel(PANEL.CLIENTELE);
				new ControllerClientele(m_vue);
				break;
			case FACTURATION :
				m_panel.setProchainPanel(PANEL.FACTURATION);
				new ControllerFacturation(m_vue);
				break;
			case SUPREME :
				m_panel.setProchainPanel(PANEL.SUPREME);
				new ControllerSupreme(m_vue);
				break;
			case RESERVATION :
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
		if (m_Admin.getDroits().get("Supreme"))
		{
			for (JButton bouton : m_boutons)
				bouton.setEnabled(true);
		}
		else
		{
			if (!m_Admin.getDroits().get(FACTURATION))
				m_panel.getBouton(FACTURATION).setEnabled(true);
			if (m_Admin.getDroits().get(RESERVATION))
				m_panel.getBouton(RESERVATION).setEnabled(true);
			if (m_Admin.getDroits().get(CLIENTELE))
				m_panel.getBouton(CLIENTELE).setEnabled(true);
		}
	}

}
