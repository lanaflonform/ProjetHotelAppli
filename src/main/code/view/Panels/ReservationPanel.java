package Panels;

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

public class ReservationPanel extends HotelPanel {

	private enum CHAMPS { RECHERCHE, SERVICE_CLIENT, SERVICE_HOTEL, HOTEL };
	JFrame m_historiqueFacture;
	Object [][] m_donnees = {			{ "Formule 2", "02/02/08", "08/02/08", "Payee" }, 
								   		{ "Campanil", "15/12/06", "28/12/06", "Payee" }, 
								   		{ "Hilton", "01/04/2019", "---", "Non payee"},
							};
	
	String [] m_enTete = {"Hotel", "Date Debut", "Date Fin", "Facture"};
	@Override
	public boolean fonctionne() {
		// TODO Auto-generated method stub
		return !m_termine;
	}
	
	public ReservationPanel() 
	{
		super();
		m_mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING, LONGUEUR / 4, 10));
		m_mainPanel.setPreferredSize(new Dimension(LONGUEUR, LARGEUR));
		
		construireChampsTextes();
		construireReservationsBouton();
		
		m_mainPanel.add(m_textes.get(CHAMPS.RECHERCHE.ordinal()));
		m_mainPanel.add(m_boutons.get(CHAMPS.RECHERCHE.ordinal()));
		
		m_mainPanel.add(m_textes.get(CHAMPS.SERVICE_CLIENT.ordinal()));
		m_mainPanel.add(m_textes.get(CHAMPS.SERVICE_HOTEL.ordinal()));
		m_mainPanel.add(m_boutons.get(CHAMPS.SERVICE_CLIENT.ordinal()));
		
		m_mainPanel.add(m_textes.get(CHAMPS.HOTEL.ordinal()));
		m_mainPanel.add(m_boutons.get(CHAMPS.HOTEL.ordinal()));
		
	}

	private void construireReservationsBouton() {
		JButton boutonRecherche = new JButton("Valider");
		boutonRecherche.setPreferredSize(new Dimension(75, 20));
		boutonRecherche.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String text = m_textes.get(0).getText();
				if (rechercherHistorique())
					montrerHistorique();
				else
					JOptionPane.showMessageDialog(arg0.getComponent(), "Cet ID ne fait référence a aucun client. \n\n ID : " + text, "Error", JOptionPane.WARNING_MESSAGE);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		m_boutons.add(boutonRecherche);
		
		JButton boutonService = new JButton("Valider");
		boutonService.setPreferredSize(new Dimension(75, 20));
		boutonService.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String text = m_textes.get(0).getText();
				if (rechercherClient())
					ajouterService();
				else
					JOptionPane.showMessageDialog(arg0.getComponent(), "Cet ID ne fait référence a aucun client. \n\n ID : " + text, "Error", JOptionPane.WARNING_MESSAGE);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		m_boutons.add(boutonService);
		
		m_boutons.add(new JButton());
		
		JButton boutonHotel = new JButton("Valider");
		boutonHotel.setPreferredSize(new Dimension(75, 20));
		boutonHotel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String text = m_textes.get(0).getText();
				if (rechercherHotel())
					afficherResidents();
				else
					JOptionPane.showMessageDialog(arg0.getComponent(), "Cet ID ne fait référence a aucun client. \n\n ID : " + text, "Error", JOptionPane.WARNING_MESSAGE);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		m_boutons.add(boutonHotel);
	}

	private void construireChampsTextes() {
		m_textes.add(new JTextField("ID Recherche"));
		m_textes.add(new JTextField("ID Service"));
		m_textes.add(new JTextField("ID Hotel"));;
		m_textes.add(new JTextField("ID Hotel"));
		for (JTextField texte : m_textes)
		{
			texte.addMouseListener(new MouseListener() {
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
			texte.setColumns(18);
		}
	}
	
	private void construireVueHistorique() {
		
		m_historiqueFacture = new JFrame ("Historique Client");
		m_historiqueFacture.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		JTable tableau = new JTable(m_donnees, m_enTete);
		m_historiqueFacture.add(new JScrollPane(tableau), BorderLayout.CENTER);
		m_historiqueFacture.setVisible(true);
		m_historiqueFacture.pack();
			
	}
	
	////////// INTERARGIT AVEC DATABASE \\\\\\\\\\
	
	private void afficherResidents() {}
	
	private void montrerHistorique() {
			//m_donnees.set();
			//m_enTete.set();
			construireVueHistorique();
	}
	
	private void ajouterService() {
		// TODO Auto-generated method stub
		
	}

	private boolean rechercherClient() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean rechercherHistorique() {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean rechercherHotel() {
		// TODO Auto-generated method stub
		return false;
	}
}
