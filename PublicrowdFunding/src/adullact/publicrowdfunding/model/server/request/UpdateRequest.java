package adullact.publicrowdfunding.model.server.request;

import adullact.publicrowdfunding.model.local.ressource.Resource;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.errorHandler.CreateErrorHandler;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.errorHandler.UpdateErrorHandler;
import adullact.publicrowdfunding.model.server.event.UpdateEvent;
import adullact.publicrowdfunding.shared.Share;

public class UpdateRequest<TResource extends Resource<TResource, TServerResource, TDetailedServerResource>, TServerResource, TDetailedServerResource extends TServerResource>
        extends AuthenticatedRequest<UpdateRequest<TResource, ?, ?>, UpdateEvent<TResource>, UpdateErrorHandler<TResource>>  {
	private TResource m_resource;

	public UpdateRequest(TResource resource, UpdateEvent event) {
		super(event, new UpdateErrorHandler<TResource>());
		
		this.m_resource = resource;
	}

	@Override
	public void execute() {
        m_resource.methodPUT(service()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
						//Share.user.defineFields(Share.user.pseudo(), m_serverUser.name, m_serverUser.firstName);
						event().onModifyAccount();
						break;
					case 1: // Error, missing username or password
						break;
					case 2: // User don't exist
						event().errorUsernameDoesNotExist(Share.user.getPseudo());
						break;
					case 3: // missing authentication
						event().errorAuthenticationRequired();
						break;
					}
			}
		});
	}

}
