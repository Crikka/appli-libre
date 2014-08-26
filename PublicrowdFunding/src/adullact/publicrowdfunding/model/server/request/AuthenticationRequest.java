package adullact.publicrowdfunding.model.server.request;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.errorHandler.AuthenticationErrorHandler;
import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;

/**
 * Created by Ferrand on 26/07/2014.
 */
public class AuthenticationRequest extends AuthenticatedRequest<AuthenticationRequest, AuthenticationEvent, AuthenticationErrorHandler> {

	private String m_login;
	private String m_password;
	
    public AuthenticationRequest(String username, String password, AuthenticationEvent authenticationEvent) {
        super(username, password, authenticationEvent, new AuthenticationErrorHandler());

        this.m_login = username;
        this.m_password = password;
    }

    @Override
    public void execute() {
       service().authenticate().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SimpleServerResponse>() {

                    @Override
                    public void call(SimpleServerResponse response) {
                        Account account = new Account(m_login, m_password, m_login);

                        switch (Integer.parseInt(response.code)) {
                            case 2: // Admin
                                account.setAdmin();
                            case 1:
                                account.setOwn();
                                event().onAuthentication();
                                break;
                            case 0: // error
                                event().errorUsernamePasswordDoesNotMatch(m_login, m_password);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        errorHandler().manageCallback();
                    }
                });
    }
}
