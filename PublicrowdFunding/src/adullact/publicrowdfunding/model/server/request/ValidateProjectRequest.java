package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.server.errorHandler.ValidateProjectErrorHandler;
import adullact.publicrowdfunding.model.server.event.ValidateProjectEvent;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.Share;

public class ValidateProjectRequest extends AuthenticatedRequest<ValidateProjectRequest, ValidateProjectEvent, ValidateProjectErrorHandler> implements ConcernProject {
	private Project m_project;

	public ValidateProjectRequest(Project project, ValidateProjectEvent event) {
		super(event, new ValidateProjectErrorHandler());
		
		this.m_project = project;
	}

	@Override
	public void execute() {
		if(Share.user.isAdmin()) {
			m_project.validate();
			done();
			//event.onValidateProject(m_project);
		}
		else {
			//event.errorAdministratorRequired();
		}
	}

	@Override
	public Project project() {
		return m_project;
	}
	
}
