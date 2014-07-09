package adullact.publicrowdfunding.model.server;

import java.util.Date;
import java.util.HashMap;

import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.User;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

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
		Project ecole = new Project("Bataille d'eau","Achat de bombe et de pistolets à eau", "1000", new Date(), new Date(114, 5, 10), new Date(114, 7, 10), new LatLng(43.607605, 3.893000));
		Project lampadaire = new Project("Cours à pieds","Organisation d'une course à pieds, de la maire jusqu'a Palavas", "300", new Date(), new Date(114, 5, 10), new Date(114, 7, 10),new LatLng(43.543364, 3.898044));
		Project parking = new Project("Fête des voisins","Besoin de financement pour organiser une fête des voisins", "500", new Date(), new Date(114, 5, 10), new Date(114, 7, 10), new LatLng(46.9, 0));
		Project laveVaisselle = new Project("Nouveau ballons","Achat de nouveaux ballons de foot pour le club", "600", new Date(), new Date(114, 5, 10), new Date(114, 7, 10),new LatLng(43.591891, 3.846318));
		Project renovation = new Project("Tournois d'échec","Besoin de financement pour organiser un concours d'échec à odysseum", "200", new Date(), new Date(114, 5, 10), new Date(114, 7, 10),new LatLng(43.603477, 3.918862));
		
		
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
