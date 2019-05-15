package code.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import code.Client;
import code.Reservation;
import code.controller.ControllerVue.PANEL;
import code.model.DAOInterfaces.DAOClient;
import code.model.DAOJDBC.DAOClientJDBC;
import code.view.Panels.ClientelePanel;
import code.view.Panels.ClientelePanel.CHAMPS_CLIENTELE;
import code.view.Vues.Vue;

public class ControllerClientele extends AbstractController {

	private DAOClient daoClient = new DAOClientJDBC();
	
	private ClientelePanel m_panel;
	public ControllerClientele(Vue vue) {
		super(vue);
		initController();
	}

	@Override
	public void initController() {
		m_panel = (ClientelePanel) ControllerVue.getPanel(PANEL.CLIENTELE);
		JButton rechercherBouton = m_panel.getBoutons().get(CHAMPS_CLIENTELE.RECHERCHE.ordinal());
		rechercherBouton.addActionListener(e -> trouverHistoriqueClient());
		JButton ajouterServiceBouton = m_panel.getBoutons().get(CHAMPS_CLIENTELE.SERVICE_CLIENT.ordinal());
		ajouterServiceBouton.addActionListener(e -> ajouterServiceClient());
		JButton afficherPresentsBouton = m_panel.getBoutons().get(CHAMPS_CLIENTELE.HOTEL.ordinal());
		afficherPresentsBouton.addActionListener(e -> afficherClientsPresents());

	}	
	// Recupere la liste des clients pr?sents dans l'hotel/les hotels ?
	private void afficherClientsPresents() {
		Object [][] donnees = 
		{
				{ "Jean", "Bon", "18/05/2019", "Non Payee" }, 
		   		{ "Marc", "Ise", "25/05/2019", "Non Payee" }, 
		   		{ "Joyce", "Lyne", "22/05/2019", "Non payee"},
		};

		//il me faut les hôtels que gèrent l'admin !
		Map<Client, Reservation> clientsPresents = daoClient.findByHotel(13);
		int i = 0;
		for (Map.Entry<Client, Reservation> entry : clientsPresents.entrySet()) {
			donnees[i][0] = entry.getKey().getPrenom();
			donnees[i][1] = entry.getKey().getNom();
			donnees[i][2] = entry.getValue().getDateDepart().toString();
			donnees[i][3] = "Non Payée";
			++i;
		}

		String [] enTete = {"Prenom", "Nom", "Date Depart", "Etat Facture"};
		m_panel.setTableauClients(donnees, enTete); 
		return;
	}

	private void trouverHistoriqueClient() { // verifier ID client ici (quel client ??)
		String text = m_panel.getTextes().get(CHAMPS_CLIENTELE.RECHERCHE.ordinal()).getText();
		int idClient = Integer.parseInt(text);
		Client client = daoClient.getById(idClient);
		if (client != null)
			montrerHistorique(client);
		else
			JOptionPane.showMessageDialog(m_panel, "Cet ID ne fait référence a aucun client. \n\n ID : " + text, "Error", JOptionPane.WARNING_MESSAGE);
	}
	// recuperer historique ici
	private void montrerHistorique(Client client)
	{
		Object [][] donnees = 
		{
				{ "Formule 2", "02/02/08", "08/02/08", "Payee" }, 
		   		{ "Campanil", "15/12/06", "28/12/06", "Payee" }, 
		   		{ "Hilton", "01/04/2019", "---", "Non payee"},
		};

		String [] enTete = {"Hotel", "Date Debut", "Date Fin", "Facture"};
		m_panel.setTableauHistorique(donnees, enTete); 
	}
	
	// afficher les reservations
	private void ajouterServiceClient()
	{
		String text = m_panel.getTextes().get(CHAMPS_CLIENTELE.SERVICE_CLIENT.ordinal()).getText();
		// Verifier ici l'id client
		if (true)
			montrerReservations(); // afficher ici les reservations
		else
			JOptionPane.showMessageDialog(m_panel, "Cet ID ne fait r?f?rence a aucun client. \n\n ID : " + text, "Error", JOptionPane.WARNING_MESSAGE);
			
	}

	// recuperer les services d'un hotel ici
	private void montrerReservations() 
	{
		Object [][] donnees = 
		{
				{ "Formule 2", "02/02/08", "08/02/08", "Payee" }, 
		   		{ "Campanil", "15/12/06", "28/12/06", "Payee" }, 
		   		{ "Hilton", "01/04/2019", "---", "Non payee"},
		};

		String [] enTete = {"Hotel", "Date Debut", "Date Fin", "Facture"};
		JTable table = m_panel.setTableauReservations(donnees, enTete);
		table.addMouseListener(new MouseAdapter() {
			  public void mousePressed(MouseEvent e) {
				    if (e.getClickCount() == 2) 
				    {
				      JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      int column = target.getSelectedColumn();
				
				      // Recuperer l'id reservation, l'hotel et les services propos?s dans cet hotel..
				      
				      ArrayList <String> serviceProposes = new ArrayList <String> ();
				      serviceProposes.add("Boissons");
				      serviceProposes.add("Groom Service");
				      serviceProposes.add("Menage");
				      JButton boutonValider = m_panel.setChoixService(serviceProposes);
				      boutonValider.addActionListener(e1 -> validerAjoutServices());
				    }
			  }

			private void validerAjoutServices() {
				ArrayList <String> servicesAjoutes = new ArrayList <String> ();
				for (JCheckBox box : m_panel.getBoxes())
				{
					if (box.isSelected())
						servicesAjoutes.add(box.getText());
				}
				System.out.println(servicesAjoutes.toString());
				// Ajouter les services
			}
		});
	}

}
