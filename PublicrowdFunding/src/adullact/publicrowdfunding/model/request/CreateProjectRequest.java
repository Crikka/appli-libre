package adullact.publicrowdfunding.model.request;

import java.util.Date;

import adullact.publicrowdfunding.model.event.CreateProjectEvent;
import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Share;

public class CreateProjectRequest extends ProjectRequest<CreateProjectRequest, CreateProjectEvent> {

	public CreateProjectRequest(String name, String description, String requestedFunding, Date beginDate, Date endDate) {
		super(name, description, requestedFunding, beginDate, endDate);		
	}

	@Override
	public void execute(CreateProjectEvent event) {

		ServerEmulator serverEmulator = ServerEmulator.instance();
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
		}
	}

}
