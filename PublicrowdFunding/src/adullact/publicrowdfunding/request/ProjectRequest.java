package adullact.publicrowdfunding.request;

import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.User;

/**
 * @author Ferrand
 * @param <TUser>
 */
public class ProjectRequest<TUser extends User> extends Request<TUser> {
	private Project m_project;
	
	public ProjectRequest(TUser user, Project project) {
		super(user);
		this.m_project = project;
	}
	
	public Project project() {
		return m_project;
	}

}
