package adullact.publicrowdfunding.model.request;

import adullact.publicrowdfunding.model.errorHandle.ValidateProjectErrorHandler;
import adullact.publicrowdfunding.model.event.ValidateProjectEvent;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.Share;

public class ValidateProjectRequest extends AuthentificatedRequest<ValidateProjectRequest, ValidateProjectEvent, ValidateProjectErrorHandler> implements ConcernProject {
	private Project m_project;

	public ValidateProjectRequest(Project project, ValidateProjectEvent event) {
		super(event, new ValidateProjectErrorHandler());
		errorHandler().defineEvent(event);
		errorHandler().defineRequest(this);
		
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
