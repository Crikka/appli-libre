package adullact.publicrowdfunding.model.server.request;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.server.entities.ServerRegistrationResponse;
import adullact.publicrowdfunding.model.server.errorHandler.RegistrationErrorHandler;
import adullact.publicrowdfunding.model.server.event.RegistrationEvent;

/**
 * Created by Ferrand on 16/08/2014.
 */
public class RegistrationRequest extends Request<RegistrationRequest, RegistrationEvent, RegistrationErrorHandler> {
    private String m_username;
    private String m_password;
    private String m_pseudo;

    public RegistrationRequest(String username, String password, String pseudo, RegistrationEvent registrationEvent) {
        super(registrationEvent, new RegistrationErrorHandler());

        this.m_username = username;
        this.m_password = password;
        this.m_pseudo = pseudo;
    }

    @Override
    public void execute() {
        service().register(m_username, m_password, m_pseudo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ServerRegistrationResponse>() {
                    @Override
                    public void call(ServerRegistrationResponse response) {
                        if(response.accountOK == 0 || response.userOK == 0) {
                            errorHandler().manageCallback();
                            return;
                        }

                        done();
                        new Account(m_username, m_password, m_pseudo).setOwn();
                        event().onRegister();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        errorHandler().manageCallback();
                    }
                });
    }
}
