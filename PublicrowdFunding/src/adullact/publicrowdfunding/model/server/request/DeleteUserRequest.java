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
                .onErrorReturn(new Func1<Throwable, ServerInfo.SimpleServerResponse>() {

                    @Override
                    public ServerInfo.SimpleServerResponse call(Throwable throwable) {
                        return null;
                    }

                })
                .subscribe(new Action1<ServerInfo.SimpleServerResponse>() {

                    @Override
                    public void call(ServerInfo.SimpleServerResponse response) {
                        if (errorHandler().isOk()) {
                            done();
                        } else {

                        }
                    }

                    ;
                });
    }
}