package adullact.publicrowdfunding.model.request;

import adullact.publicrowdfunding.model.reply.Reply;

/**
 * 
 * @author Ferrand
 * 
 * @param <TUser>
 */
public abstract class Request {
	
	public Request(){
	}
		
	public abstract Reply execute();
	
}
