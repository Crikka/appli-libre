package adullact.publicrowdfunding.requester;

import java.util.HashMap;

import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.User;


/**
 * @author Ferrand
 * @brief 'Server' for local testing.
 */
public class ServerEmulator {
	/* Singleton */
	private static ServerEmulator m_instance = null;
	public static ServerEmulator instance() { if(m_instance == null) {m_instance = new ServerEmulator();} return m_instance; }
	/* --------- */
	
	private HashMap<String, User> usersBase; // name of user use as key
	private HashMap<String, Project> projectsBase; // name of project use as key
	
	private ServerEmulator() {
		this.usersBase = new HashMap<String, User>();
		this.projectsBase = new HashMap<String, Project>();
		initialize();
	}
	
	/**
	 * @brief fill base with values.
	 */
	private void initialize() {
		/* Users base */
		usersBase.put("Gate", new Administrator("Gate", "Bill"));
		usersBase.put("Nelaupe", new Administrator("Nelaupe", "Lucas"));
		usersBase.put("Ferrand", new User("Ferrand", "Anthony"));
		/* ---------- */
		
		/* Projects base */
		projectsBase.put("La tour Eiffel", new Project("La tour Eiffel"));
		projectsBase.put("Arc de triomphe", new Project("Arc de triomphe"));
		/* ------------- */
	}
	
	public boolean userExist(String name) {
		return usersBase.containsKey(name);
	}
	
	public boolean isAdministrator(String name) {
		boolean res = userExist(name);
		if(res){
			res = usersBase.get(name) instanceof Administrator; // In database, we will have a boolean in user table.
		}
		return res;
	}
	
	public boolean authentificateUser(String name, String firstName) {
		User user = usersBase.get(name);
		if(user == null){
			return false;
		}
		return ((user.name() == name) && (user.firstName() == firstName));
	}
	
	public boolean projectExist(String name) {
		return projectsBase.containsKey(name);
	}
	
	public HashMap<String, Project> getAllProjets(){
		return this.projectsBase;
	}
	
}
