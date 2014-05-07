package adullact.publicrowdfunding.request;

import adullact.publicrowdfunding.shared.User;

/**
 * @author Ferrand
 * @brief Use this request when a user needs to be authenticate.
 */
public class AuthentificationRequest extends Request<User> {

	public AuthentificationRequest(User user) {
		super(user);
	}

}
