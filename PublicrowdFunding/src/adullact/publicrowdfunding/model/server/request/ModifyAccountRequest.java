package adullact.publicrowdfunding.model.server.request;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.errorHandler.ModifyAccountErrorHandler;
import adullact.publicrowdfunding.model.server.event.ModifyAccountEvent;
import adullact.publicrowdfunding.shared.Share;

public class ModifyAccountRequest extends AuthenticatedRequest<ModifyAccountRequest, ModifyAccountEvent, ModifyAccountErrorHandler> {
	private ServerUser m_serverUser;

	public ModifyAccountRequest(String newPassWord, String newName, String newFirstName, ModifyAccountEvent event) {
		super(event, new ModifyAccountErrorHandler());
		
		m_serverUser = new ServerUser();
        m_serverUser.password = newPassWord == null ? Share.user.password() : newPassWord;
        m_serverUser.name = newName == null ? Share.user.name() : newName;
		m_serverUser.firstName = newFirstName == null ? Share.user.firstName() : newFirstName;
		
	}

	@Override
	public void execute() {
		service().modifyUser(m_serverUser, Share.user.pseudo()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
		.onErrorReturn(new Func1<Throwable, SimpleServerResponse>() {

			@Override
			public SimpleServerResponse call(Throwable arg0) {
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

				int  code = response.code;
					switch(code){
					case 0: // Created
						done();
						Share.user.defineFields(Share.user.pseudo(), m_serverUser.password, m_serverUser.name, m_serverUser.firstName);
						event().onModifyAccount();
						break;
					case 1: // Error, missing username or password
						break;
					case 2: // User don't exist
						event().errorUsernameDoesNotExist(Share.user.pseudo());
						break;
					case 3: // missing authentication
						event().errorAuthenticationRequired();
						break;
					}
			}
		});
	}

}
