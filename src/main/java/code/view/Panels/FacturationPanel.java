package code.view.Panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class FacturationPanel extends HotelPanel {

	JFrame m_vueFacture;
	Object [][] m_donnees = {			{ "Reservation chambre", "150€", "3 jours et 2 personnes" }, 
								   		{ "Repas restaurant", "68€", "" }, 
								   		{ "Menage chambre", "15€", "Une fois"},
							};
	
	String [] m_enTete = {"Type Depense", "Prix", "Details"};
	
	public FacturationPanel() 
	{
		super();
		m_mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING, LONGUEUR / 4, 10));
		m_mainPanel.setPreferredSize(new Dimension(LONGUEUR, LARGEUR));
		
		construireChampsTextes();
		construireFacturationBouton();
	}

	private void construireVueFacture() {
		
		m_vueFacture = new JFrame ("Facture Client");
		m_vueFacture.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		JTable tableau = new JTable(m_donnees, m_enTete);
		m_vueFacture.add(new JScrollPane(tableau), BorderLayout.CENTER);
		m_vueFacture.setVisible(true);
		m_vueFacture.pack();
			
	}

	private void construireChampsTextes() {
		JTextField IDClient = new JTextField("ID Client");
		IDClient.setColumns(18);
		IDClient.addMouseListener(new MouseListener() {
			private boolean cliqued = false;
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!cliqued)
				{
					((JTextField) arg0.getSource()).setText("");
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!cliqued)
				{
					((JTextField) e.getSource()).setText("");
				}
			}
		} );
		
		m_textes.add(IDClient);
		m_mainPanel.add(IDClient);
	}

	private void construireFacturationBouton() {
		JButton validerButton = new JButton("Valider");
		validerButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String text = m_textes.get(0).getText();
				if (rechercherClients())
					faireFacture();
				else
					JOptionPane.showMessageDialog(arg0.getComponent(), "Cet ID ne fait référence a aucun client. \n\n ID : " + text, "Error", JOptionPane.WARNING_MESSAGE);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		} );
		validerButton.setPreferredSize(new Dimension(75, 20));
		m_boutons.add(validerButton);
		m_mainPanel.add(validerButton);
		
	}
	
	// INTERARGIT AVEC DATABASE
	private boolean rechercherClients() {
		if (m_textes.get(0).getText().length() > 0)
			return true;
		return false;
	}
	
	// INTERARGIT AVEC DATABASE
	private void faireFacture()
	{
		//m_donnees.set();
		//m_enTete.set();
		construireVueFacture();
	}

	@Override
	public boolean fonctionne() {
		return !m_termine;
	}
	
}
