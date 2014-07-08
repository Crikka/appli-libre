package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.server.errorHandler.ProjectsListingErrorHandler;
import adullact.publicrowdfunding.model.server.event.ProjectsListingEvent;

public class ProjectsListingRequest extends AuthenticatedRequest<ProjectsListingRequest, ProjectsListingEvent, ProjectsListingErrorHandler> {

	public ProjectsListingRequest(ProjectsListingEvent event, ProjectsListingErrorHandler errorHandler) {
		super(event, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
