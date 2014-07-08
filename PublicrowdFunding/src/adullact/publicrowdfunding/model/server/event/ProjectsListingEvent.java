package adullact.publicrowdfunding.model.server.event;

import java.util.ArrayList;

import adullact.publicrowdfunding.model.server.errorHandler.ProjectsListingErrorHandler;
import adullact.publicrowdfunding.model.server.request.ProjectsListingRequest;
import adullact.publicrowdfunding.shared.Project;

public abstract class ProjectsListingEvent extends AuthenticatedEvent<ProjectsListingRequest, ProjectsListingEvent, ProjectsListingErrorHandler> {
	public abstract void onProjectsReceived(ArrayList<Project> projects);
}
