package adullact.publicrowdfunding.server;

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
		Administrator billGate = new Administrator();
		billGate.defineFields("MisterGate", "Gate", "Bill");
		Administrator lucasX = new Administrator();
		lucasX.defineFields("XFactor", "Nelaupe", "Lucas");
		User anthony = new User();
		anthony.defineFields("Miaou", "Ferrand", "Anthony");
		User lucas = new User();
		lucas.defineFields("Miaou", "Nelaupe", "Lucas");
		Administrator admin = new Administrator();
		admin.defineFields("admin", "Ad", "Min");
		
		usersBase.put(new Pair<String, String>("MisterGate", "azE45WIN"), billGate);
		usersBase.put(new Pair<String, String>("XFactor", "mushroom34"), lucasX);
		usersBase.put(new Pair<String, String>("Miaou", "abjectDominera"), anthony);
		usersBase.put(new Pair<String, String>("lucas", "lucas"), lucas);
		usersBase.put(new Pair<String, String>("admin", "admin"), admin);
		/* ---------- */
		
		/* Projects base */
		projectsBase.put("Ecole publique", new Project("Ecole publique","Construction d'une école primaire", "25000"));
		projectsBase.put("Parking sous terrain", new Project("Parking sous terrain","Parking au centre de Montpellier", "50000"));
		projectsBase.put("Lave vaisselle", new Project("Lave vaisselle","Lave vaisselle pour le restaurent universitaire de Montpellier", "100"));
		projectsBase.put("Renovation Faculté", new Project("Renovation Faculté","Rénovation de la fac des sciences", "165000"));
		projectsBase.put("Nouveaux lampadaires", new Project("Nouveaux lampadaires","Achat de nouveaux lampadaires basse consommation", "5000"));
		projectsBase.put("De la modestie pour fred", new Project("De la modestie pour fred","Ca ne lui ferait pas de mal", "0.10"));
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
