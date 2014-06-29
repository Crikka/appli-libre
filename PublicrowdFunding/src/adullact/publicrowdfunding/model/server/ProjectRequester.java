package adullact.publicrowdfunding.model.server;

import java.util.Date;
import adullact.publicrowdfunding.model.server.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.server.event.ProjectsListingEvent;
import adullact.publicrowdfunding.model.server.event.ValidateProjectEvent;
import adullact.publicrowdfunding.model.server.request.CreateProjectRequest;
import adullact.publicrowdfunding.model.server.request.ValidateProjectRequest;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.User;

import com.google.android.gms.maps.model.LatLng;

public class ProjectRequester {

	public static void createProject(String name, String description, String requestedFunding, Date beginOfProject, Date endOfProject, CreateProjectEvent createProjectEvent, LatLng position){
		new CreateProjectRequest(name, description, requestedFunding, beginOfProject, endOfProject, position, createProjectEvent).execute();
	}

	public static void validateProject(Project project, ValidateProjectEvent validateProjectEvent) {
		new ValidateProjectRequest(project, validateProjectEvent).execute();
	}
	
	// TODO
	public static void listOfProject(ProjectsListingEvent projectsListingEvent) {
		
	}
	
	// TODO
	public static void listOfProjectProposedByUser(User user, ProjectsListingEvent projectsListingEvent) {
		
	}
}
