package adullact.publicrowdfunding.model.request;

import retrofit.RequestInterceptor;
import android.util.Base64;

public class AuthentificationRequestInterceptor implements RequestInterceptor {

	private String username;
	private String password;

	public AuthentificationRequestInterceptor(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void intercept(RequestFacade requestFacade) {
		final String userAndPassword = username + ":" + password;
		final String encodedUserAndPassword = "Basic " + Base64.encodeToString(userAndPassword.getBytes(), 0);
		requestFacade.addHeader("Authorization", encodedUserAndPassword);
	}

}
