package adullact.publicrowdfunding.request;

import adullact.publicrowdfunding.exceptions.AdministratorRequiredException;
import adullact.publicrowdfunding.exceptions.AuthentificationRequiredException;
import adullact.publicrowdfunding.shared.Share;

public abstract class AuthentificatedRequest extends Request {
	
	public AuthentificatedRequest(){
	}
	
	@Override
	protected final void verifyUserType() throws AuthentificationRequiredException, AdministratorRequiredException {
		if(!Share.user.isAuthentified()) {
			throw new AuthentificationRequiredException();
		}
	}
}
