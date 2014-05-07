package adullact.publicrowdfunding.requester;

import adullact.publicrowdfunding.reply.Reply;
import adullact.publicrowdfunding.request.AuthentificationRequest;

public class AuthentificationRequester extends Requester<AuthentificationRequest> {

	@Override
	public Reply<AuthentificationRequest> post(AuthentificationRequest request) {
		/*
		 * TODO
		 * Simulacre
		 */
		ServerEmulator serverEmulator = ServerEmulator.instance();
		Reply<AuthentificationRequest> res;
		if(serverEmulator.authentificateUser(request.user().name(), request.user().firstName())){
			res = new Reply<AuthentificationRequest>(request);
			request.user().authenticate();
		}
		else{
			res = new Reply<AuthentificationRequest>(request, false);
		}
		
		return res;
	}

}
