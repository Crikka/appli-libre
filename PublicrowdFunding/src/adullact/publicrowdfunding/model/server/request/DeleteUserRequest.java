package adullact.publicrowdfunding.model.server.request;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.errorHandler.DeleteUserErrorHandler;
import adullact.publicrowdfunding.model.server.event.DeleteUserEvent;

public class DeleteUserRequest extends AuthenticatedRequest<DeleteUserRequest, DeleteUserEvent, DeleteUserErrorHandler> {

    private String m_username;

    public DeleteUserRequest(String username, DeleteUserEvent event) {
        super(event, new DeleteUserErrorHandler());

        this.m_username = username;
    }

    @Override
    public void execute() {
        service().deleteUser(m_username).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, SimpleServerResponse>() {

                    @Override
                    public SimpleServerResponse call(Throwable throwable) {
                        return null;
                    }

                })
                .subscribe(new Action1<SimpleServerResponse>() {

                    @Override
                    public void call(SimpleServerResponse response) {
                        if (response == null) {
                            errorHandler().manageCallback();
                            return;
                        }

                        switch (response.code) {
                            case 0: // Ok!
                                done();
                                event().onDeleteUser(m_username);
                                break;
                            case 1:
                                event().errorUsernameDoesNotExist(m_username);
                                break;
                        }
                    }
                });
    }
}