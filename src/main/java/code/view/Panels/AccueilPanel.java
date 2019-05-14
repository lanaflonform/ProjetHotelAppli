package code.view.Panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import code.view.Vues.Vue.PANEL;

public class AccueilPanel extends HotelPanel {

	private PANEL m_prochainPanel;
	
	public AccueilPanel() {
		super();	
		m_mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 50));
		m_mainPanel.setPreferredSize(new Dimension(LONGUEUR, LARGEUR));
	}
	
	public void construireBoutons(ArrayList<String> nomBoutons)
	{
		for (String nomBouton : nomBoutons)
		{
			JButton bouton = new JButton(nomBouton);
			bouton.setEnabled(false);
			m_boutons.add(bouton);
			m_mainPanel.add(bouton);
		}
	}
	
	public JButton getBouton(String nom)
	{
		for (JButton bouton : m_boutons)
		{
			if (bouton.getText().equals(nom))
				return bouton;
		}
		return null;
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
