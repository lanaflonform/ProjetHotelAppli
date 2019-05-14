package code.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import code.Admin;
import code.model.DAOInterfaces.DAOAdmin;
import code.model.DAOJDBC.DAOAdminJDBC;
import code.view.Panels.ConnectionPanel;
import code.view.Panels.ConnectionPanel.CHAMPS;
import code.view.Vues.Vue;
import code.view.Vues.Vue.PANEL;

public class ControllerConnection extends AbstractController {

	private ConnectionPanel m_panel; 	
	private String m_nomUtilisateur;
	private String m_motDePasse;
	private DAOAdmin daoAdmin;
	
	public ControllerConnection(Vue vue) {
		super(vue);
		m_panel = (ConnectionPanel) m_vue.getPanel(PANEL.CONNECTION);
		daoAdmin = new DAOAdminJDBC();
		initController();
		m_vue.deroulement();
	}

	@Override
	public void initController() {
		JButton validerBouton = m_panel.getBoutons().get(0);
		validerBouton.addActionListener(e -> verifierIdentifiants());
	}
	
	private void verifierIdentifiants()
	{
		m_nomUtilisateur = m_panel.getTextes().get(CHAMPS.NOM_UTILISATEUR.ordinal()).getText();
		m_motDePasse = m_panel.getTextes().get(CHAMPS.MOT_DE_PASSE.ordinal()).getText();

		System.out.println(m_nomUtilisateur);
		System.out.println(m_motDePasse);
		// Verifier
		Admin admin = daoAdmin.findByUsernameAndPassword(m_nomUtilisateur, m_motDePasse);
		if (admin != null) // si les identifiants sont corrects
		{
			System.out.println(admin);
			m_panel.setTermine(true);
			new ControllerAccueil(m_vue, admin);
		}
		else
		{
			JOptionPane.showMessageDialog(m_panel, "Nom d'utilisateur ou mot de passe incorrect.", "Error", JOptionPane.WARNING_MESSAGE);
			m_nomUtilisateur = "";
			m_motDePasse = "";
	}
	}

	public static void main(String[] args) {
		new ControllerConnection(new Vue());
	}
}
