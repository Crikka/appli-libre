package adullact.publicrowdfunding.model.server.request;

import android.util.Base64;

import retrofit.RequestInterceptor;

/**
 * @author Ferrand and Nelaupe
 */
public class SecurityRequestInterceptor implements RequestInterceptor {

	private String username;
	private String password;
    private boolean authenticator;

    public SecurityRequestInterceptor() {
        this.username = null;
        this.password = null;
        authenticator = false;
    }

	public void defineAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
        authenticator = true;
    }

	@Override
	public void intercept(RequestFacade requestFacade) {
        requestFacade.addHeader("apiKey", "azerty");
        requestFacade.addHeader("Connection", "close");
        if(authenticator) {
            final String userAndPassword = username + ":" + password;
            final String encodedUserAndPassword = "Basic " + Base64.encodeToString(userAndPassword.getBytes(), 0);
            requestFacade.addHeader("Authorization", encodedUserAndPassword);
            requestFacade.addHeader("User", username);
            requestFacade.addHeader("Password", password);
        }
	}

}
