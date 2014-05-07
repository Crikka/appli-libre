package adullact.publicrowdfunding.request;

import adullact.publicrowdfunding.shared.User;

/**
 * 
 * @author Ferrand
 * 
 * @param <TUser>
 */
public abstract class Request<TUser extends User> {
	private TUser m_user;
	
	public Request(TUser user){
		this.m_user = user;
	}
	
	public TUser user(){
		return m_user;
	}
}
