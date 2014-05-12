package adullact.publicrowdfunding.request;

import adullact.publicrowdfunding.exceptions.AdministratorRequiredException;
import adullact.publicrowdfunding.exceptions.AuthentificationRequiredException;
import adullact.publicrowdfunding.reply.Reply;

/**
 * 
 * @author Ferrand
 * 
 * @param <TUser>
 */
public abstract class Request {
	
	public Request(){
	}
		
	protected abstract void verifyUserType() throws AuthentificationRequiredException, AdministratorRequiredException;
	public abstract Reply execute() throws Exception;
	
}
