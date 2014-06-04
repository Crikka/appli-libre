package adullact.publicrowdfunding.model.server;

import java.util.Date;
import java.util.HashMap;

import com.google.android.gms.maps.model.LatLng;

import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.User;
import android.util.Pair;

/**
 * @author Ferrand
 * @brief 'Server' for local testing.
 */
public class ServerEmulator {
	/* Singleton */
	private static ServerEmulator m_instance = null;

	public static ServerEmulator instance() {
		if (m_instance == null) {
			m_instance = new ServerEmulator();
		}
		return m_instance;
	}

	/* --------- */

	private HashMap<Pair<String, String>, User> usersBase; // name of user use
															// as key
	private HashMap<String, Project> projectsBase; // name of project use as key

	private ServerEmulator() {
		this.usersBase = new HashMap<Pair<String, String>, User>();
		this.projectsBase = new HashMap<String, Project>();
		initialize();
	}

	/**
	 * @brief fill base with values.
	 */
	private void initialize() {
		/* Users base */
		Administrator billGate = new Administrator();
		billGate.defineFields("MisterGate", "azE45WIN", "Gate", "Bill");
		Administrator lucasX = new Administrator();
		lucasX.defineFields("XFactor", "mushroom34", "Nelaupe", "Lucas");
		User anthony = new User();
		anthony.defineFields("Miaou", "abjectDominera", "Ferrand", "Anthony");
		User lucas = new User();
		lucas.defineFields("Miaou", "lucas", "Nelaupe", "Lucas");
		Administrator admin = new Administrator();
		admin.defineFields("admin", "admin", "Ad", "Min");

		usersBase.put(new Pair<String, String>("MisterGate", "azE45WIN"), billGate);
		usersBase.put(new Pair<String, String>("XFactor", "mushroom34"), lucasX);
		usersBase.put(new Pair<String, String>("Miaou", "abjectDominera"), anthony);
		usersBase.put(new Pair<String, String>("lucas", "lucas"), lucas);
		usersBase.put(new Pair<String, String>("admin", "admin"), admin);
		/* ---------- */

		/* Projects base */
		Project ecole = new Project("Ecole publique","Construction d'une école primaire", "25000", new Date(), new Date(114, 5, 10), new Date(114, 7, 10), new LatLng(43.6, 2.7));
		Project parking = new Project("Parking sous terrain","Parking au centre de Montpellier", "50000", new Date(), new Date(114, 5, 10), new Date(114, 7, 10), new LatLng(46.9, 0));
		Project laveVaisselle = new Project("Lave vaisselle","Lave vaisselle pour le restaurant universitaire de Montpellier", "100", new Date(), new Date(114, 5, 10), new Date(114, 7, 10),new LatLng(47, 4.5));
		Project renovation = new Project("Renovation Faculté","Rénovation de la fac des sciences", "165000", new Date(), new Date(114, 5, 10), new Date(114, 7, 10),new LatLng(48, 1.5));
		Project lampadaire = new Project("Lampadaires écolo","Achat de lampadaires basse consommation", "5000", new Date(), new Date(114, 5, 10), new Date(114, 7, 10),new LatLng(44, 4.5));
		
		ecole.validate();
		parking.validate();
		laveVaisselle.validate();
		renovation.validate();
		lampadaire.validate();

		projectsBase.put(ecole.id(), ecole);
		projectsBase.put(parking.id(), parking);
		projectsBase.put(laveVaisselle.id(), laveVaisselle);
		projectsBase.put(renovation.id(), renovation);
		projectsBase.put(lampadaire.id(), lampadaire);
		/* ------------- */
	}

	public void replaceUser(String oldPseudo, String oldPassword, User user) {
		usersBase.remove(new Pair<String, String>(oldPseudo, oldPassword));
		usersBase.put(new Pair<String, String>(user.pseudo(), user.password()),
				user);
	}

	/**
	 * @param username
	 * @param password
	 * @return User if he exists, else return null.
	 */
	public User authentificateUser(String username, String password) {
		Pair<String, String> clef = new Pair<String, String>(username, password);

		return usersBase.get(clef);
	}

	public boolean projectExist(Project project) {
		return projectsBase.containsKey(project.id());
	}

	public void addProject(Project project) {
		if (!projectExist(project)) {
			projectsBase.put(project.id(), project);
		}
	}

	public HashMap<String, Project> getAllProjets() {
		return this.projectsBase;
	}

}
