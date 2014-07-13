package adullact.publicrowdfunding.model.server.request;

import retrofit.RequestInterceptor;
import android.util.Base64;

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
        if(authenticator) {
            final String userAndPassword = username + ":" + password;
            final String encodedUserAndPassword = "Basic " + Base64.encodeToString(userAndPassword.getBytes(), 0);
            requestFacade.addHeader("Authorization", encodedUserAndPassword);
        }
	}

}
