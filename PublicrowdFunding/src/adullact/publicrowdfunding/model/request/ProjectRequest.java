package adullact.publicrowdfunding.model.request;

import java.util.Date;

import adullact.publicrowdfunding.model.reply.Reply;
import adullact.publicrowdfunding.shared.Project;

public abstract class ProjectRequest extends Request {
	private Project m_project;

	public ProjectRequest(String name, String description, String requestedFunding, Date beginDate, Date endDate) {
		super();
		
		this.m_project = new Project(name, description, requestedFunding, new Date(), beginDate, endDate);
		
	}
	
	public Project project() {
		return m_project;
	}
	
	@Override
	public abstract Reply execute();

}
