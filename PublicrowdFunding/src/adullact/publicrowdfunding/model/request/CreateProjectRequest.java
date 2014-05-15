package adullact.publicrowdfunding.model.request;

import java.util.Date;

import adullact.publicrowdfunding.model.reply.CreateProjectReply;
import adullact.publicrowdfunding.model.server.ServerEmulator;
import adullact.publicrowdfunding.shared.Share;

public class CreateProjectRequest extends ProjectRequest {

	public CreateProjectRequest(String name, String description, String requestedFunding, Date beginDate, Date endDate) {
		super(name, description, requestedFunding, beginDate, endDate);		
	}

	@Override
	public CreateProjectReply execute() {

		ServerEmulator serverEmulator = ServerEmulator.instance();
		CreateProjectReply reply = new CreateProjectReply();
		if(Share.user.isAuthentified()) {
			serverEmulator.addProject(project());
			reply.declareSucceed();
		}
		else {
			reply.declareAuthentificationFailed();
		}

		return reply;
	}

}
