package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.NothingToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.errorHandler.AuthenticationErrorHandler;
import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ferrand on 26/07/2014.
 */
public class AuthenticationRequest extends AuthenticatedRequest<AuthenticationRequest, AuthenticationEvent, AuthenticationErrorHandler> {

    public AuthenticationRequest(String username, String password, AuthenticationEvent authenticationEvent) {
        super(username, password, authenticationEvent, new AuthenticationErrorHandler());

    }

    @Override
    public void execute() {
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
                });
    }
}
