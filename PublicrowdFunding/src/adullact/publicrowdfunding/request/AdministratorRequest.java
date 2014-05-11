package adullact.publicrowdfunding.request;

import adullact.publicrowdfunding.exceptions.AdministratorRequiredException;
import adullact.publicrowdfunding.exceptions.AuthentificationRequiredException;
import adullact.publicrowdfunding.shared.Administrator;
import adullact.publicrowdfunding.shared.Share;

public abstract class AdministratorRequest extends Request {
	
	public AdministratorRequest(){
	}
	
	@Override
	protected final void verifyUserType() throws AuthentificationRequiredException, AdministratorRequiredException {
		if(!Share.user.isAuthentified()) {
			throw new AuthentificationRequiredException();
		}
		if(!(Share.user instanceof Administrator)){
			throw new AdministratorRequiredException();
		}
	}
}
