package adullact.publicrowdfunding.model.request;

import retrofit.http.Body;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.errorHandle.ModifyAccountErrorHandler;
import adullact.publicrowdfunding.model.event.ModifyAccountEvent;
import adullact.publicrowdfunding.shared.Share;

public class ModifyAccountRequest extends AuthentificatedRequest<ModifyAccountRequest, ModifyAccountEvent, ModifyAccountErrorHandler> {
	private ServerUser m_serverUser;

	public ModifyAccountRequest(String newPassword, String newName, String newFirstName, ModifyAccountEvent event) {
		super(event, new ModifyAccountErrorHandler());
		errorHandler().defineEvent(event);
		errorHandler().defineRequest(this);
		
		m_serverUser = new ServerUser();
		m_serverUser.command = "modify";
		m_serverUser.password = newPassword == null ? Share.user.password() : newPassword;
		m_serverUser.name = newName == null ? Share.user.name() : newName;
		m_serverUser.firstName = newFirstName == null ? Share.user.firstName() : newFirstName;
		

		m_service = m_restAdapter.create(ModifyAccountService.class);
	}
	
	/* Communication interface */
	private final ModifyAccountService m_service;
	@SuppressWarnings("unused")
	private class ServerUser {
		public String command;
		public String password;
		public String name;
		public String firstName;
	}
	private class ResponseServer {
		public Integer returnCode; 
	}
	private interface ModifyAccountService {
		@PUT("/user/{username}")
		Observable<ResponseServer> modify(@Body ServerUser serverUser, @Path(value = "username") String username);
	}
	/* --------- */

	@Override
	public void execute() {
		m_service.modify(m_serverUser, m_username).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
		.onErrorReturn(new Func1<Throwable, ResponseServer>() {

			@Override
			public ResponseServer call(Throwable arg0) {
				ResponseServer res = new ResponseServer();
				res.returnCode = 3;
				return res;
			}
			
		})
		.subscribe(new Action1<ResponseServer>() {
			
			@Override
			public void call(ResponseServer response) {
				int  returnCode = response.returnCode;
				if(errorHandler().isOk()){
					switch(returnCode){
					case 0: // Created
						done();
						Share.user.defineFields(Share.user.pseudo(), m_serverUser.password, m_serverUser.name, m_serverUser.firstName);
						event().onModifyAccount();
						break;
					case 1: // Error, missing username or password
						break;
					case 2: // User don't exist
						event().errorUsernameDoesNotExist(m_username);
						break;
					case 3: // missing authentification
						event().errorAuthentificationRequired();
						break;
					}
				}
			};
		});
	}

}
