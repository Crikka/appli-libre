package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.server.errorHandler.AuthenticationErrorHandler;
import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;

/**
 * Created by Ferrand on 26/07/2014.
 */
public class AuthenticationRequest extends AuthenticatedRequest<AuthenticationRequest, AuthenticationEvent, AuthenticationErrorHandler> {

    public AuthenticationRequest(String username, String password, AuthenticationEvent authenticationEvent) {
        super(username, password, authenticationEvent, new AuthenticationErrorHandler());

    }

    @Override
    public void execute() {
    	/*
       service().authenticate().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, SimpleServerResponse>() {

                    @Override
                    public SimpleServerResponse call(Throwable throwable) {
                        return null;
                    }

                })
                .subscribe(new Action1<SimpleServerResponse>() {

                    @Override
                    public void call(SimpleServerResponse response) {
                        if(response == null) {
                            errorHandler().manageCallback();
                            return;
                        }

                        Account account = new Account(username(), password(), "");
                        switch(response.code) {
                            case 0:
                                account.setOwn();
                                account.getUser(new HoldToDo<User>() {
                                                    @Override
                                                    public void hold(User resource) {
                                                        event().onAuthentication();
                                                    }
                                                });
                                break;
                            case 1:
                                event().errorUsernamePasswordDoesNotMatch(username(), password());
                                break;
                        }
                    }
                });*/
    }
}
