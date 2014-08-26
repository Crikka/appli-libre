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
    	System.out.println("execution");
       service().authenticate(m_login, m_password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, SimpleServerResponse>() {

                    @Override
                    public SimpleServerResponse call(Throwable throwable) {
                    	   event().errorUsernamePasswordDoesNotMatch(m_login, m_password);
						return null;
                    }

                })
                .subscribe(new Action1<SimpleServerResponse>() {

                    @Override
                    public void call(SimpleServerResponse response) {
                        if(response == null) {
                        	   event().errorUsernamePasswordDoesNotMatch(m_login, m_password);
                            return;
                        }

                        Account account = new Account(m_login, m_password, m_login);
                       
                        int code = 0;
                        try{
                        	code = Integer.parseInt(response.code);
                        }catch(Exception e){
                        	e.printStackTrace();
                        }
                        
                       
                        switch(code) {
                            case 0:
                                account.setOwn();
                                account.getUser(new HoldToDo<User>() {
                                                    @Override
                                                    public void hold(User resource) {
                                                    	done();
                                                        event().onAuthentication();
                                                       
                                                        
                                                    }
                                                });
                                break;
                            case 1:
                            	// Admin
                            	 account.setOwn();
                            	 account.setAdmin();
                            	 
                                 account.getUser(new HoldToDo<User>() {
                                                     @Override
                                                     public void hold(User resource) {
                                                    	 done();
                                                         event().onAuthentication();
                                                        
                                                     }
                                                 });
                                break;
                        }
                    }
                });
    }
}
