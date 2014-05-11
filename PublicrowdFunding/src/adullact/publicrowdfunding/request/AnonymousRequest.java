package adullact.publicrowdfunding.request;

import adullact.publicrowdfunding.exceptions.AdministratorRequiredException;
import adullact.publicrowdfunding.exceptions.AuthentificationRequiredException;

public abstract class AnonymousRequest extends Request {
	
	public AnonymousRequest(){
	}
	
	@Override
	protected final void verifyUserType() throws AuthentificationRequiredException, AdministratorRequiredException {
	}
}
