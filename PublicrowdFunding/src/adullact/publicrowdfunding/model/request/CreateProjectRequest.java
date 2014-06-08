package adullact.publicrowdfunding.model.request;

import java.util.Date;
import com.google.android.gms.maps.model.LatLng;
import adullact.publicrowdfunding.model.errorHandle.CreateProjectErrorHandler;
import adullact.publicrowdfunding.model.event.CreateProjectEvent;
import adullact.publicrowdfunding.shared.Project;

public class CreateProjectRequest extends AuthentificatedRequest<CreateProjectRequest, CreateProjectEvent, CreateProjectErrorHandler> implements ConcernProject {
	private Project m_project;
	
	public CreateProjectRequest(String name, String description, String requestedFunding, Date beginDate, Date endDate, LatLng position, CreateProjectEvent event) {
		super(event, new CreateProjectErrorHandler());
		errorHandler().defineEvent(event);
		errorHandler().defineRequest(this);
		
		this.m_project = new Project(name, description, requestedFunding, new Date(), beginDate, endDate, position);
	}

	@Override
	public void execute() {

		/*ServerEmulator serverEmulator = ServerEmulator.instance();
		if(Share.user.isAuthentified()) {
			serverEmulator.addProject(project());
			done();
			event.onProjectAdded(project());
			if(Share.user.isAdmin()) {
				event.ifUserIsAdministrator();
			}
		}
		else {
			event.errorAuthentificationRequired();
		}*/
	}

	@Override
	public Project project() {
		return m_project;
	}

}
