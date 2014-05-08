package adullact.publicrowdfunding.requester;

import java.util.HashMap;

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
	public static ServerEmulator instance() { if(m_instance == null) {m_instance = new ServerEmulator();} return m_instance; }
	/* --------- */
	
	private HashMap<Pair<String, String>, User> usersBase; // name of user use as key
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
		usersBase.put(new Pair<String, String>("MisterGate", "azE45WIN"), new Administrator("MisterGate", "Gate", "Bill"));
		usersBase.put(new Pair<String, String>("XFactor", "mushroom34"), new Administrator("XFactor", "Nelaupe", "Lucas"));
		usersBase.put(new Pair<String, String>("Miaou", "abjectDominera"), new User("Miaou", "Ferrand", "Anthony"));
		/* ---------- */
		
		/* Projects base */
		projectsBase.put("Ecole publique", new Project("Construction d'une école primaire"));
		projectsBase.put("Parking sous terrain", new Project("Parking au centre de Montpellier"));
		projectsBase.put("Lave vaisselle", new Project("Lave vaisselle pour le restaurent universitaire de Montpellier"));
		projectsBase.put("Renovation Faculté", new Project("Rénovation de la fac des sciences"));
		projectsBase.put("Nouveaux lampadaires", new Project("Achat de nouveaux lampadaires basse consommation"));
		projectsBase.put("De la modestie pour fred", new Project("Ca ne lui ferait pas de mal"));
		/* ------------- */
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
	
	public boolean projectExist(String name) {
		return projectsBase.containsKey(name);
	}
	
	public HashMap<String, Project> getAllProjets(){
		return this.projectsBase;
	}
	
}
