package adullact.publicrowdfunding.model.server.request;

import java.util.Date;
import java.util.UUID;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.errorHandler.CreateProjectErrorHandler;
import adullact.publicrowdfunding.model.server.event.CreateProjectEvent;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.Share;

import com.google.android.gms.maps.model.LatLng;

public class CreateProjectRequest extends AuthenticatedRequest<CreateProjectRequest, CreateProjectEvent, CreateProjectErrorHandler> {
	private ServerInfo.ServerProject m_project;
	private Project m_cacheProject;

	public CreateProjectRequest(String name, String description, String requestedFunding, Date beginDate, Date endDate, LatLng position, CreateProjectEvent event) {
		super(event, new CreateProjectErrorHandler());

		m_cacheProject = new Project(name, description, requestedFunding, new Date(), beginDate, endDate, position);
        m_project = new ServerInfo.ServerProject();
        m_project.id = UUID.randomUUID().toString();
        m_project.proposedBy = Share.user.pseudo();
        m_project.name = name;
		m_project.description = description;
        m_project.currentFunding = "0";
        m_project.requestedFunding = requestedFunding;
        m_project.creationDate = new Date();
        m_project.beginDate = beginDate;
		m_project.endDate = endDate;
		m_project.position = position;
	}

	@Override
	public void execute() {
		service().createProject(m_project).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
		.onErrorReturn(new Func1<Throwable, ServerInfo.SimpleServerResponse>() {

			@Override
			public ServerInfo.SimpleServerResponse call(Throwable arg0) {
				return null;
			}

		})
		.subscribe(new Action1<ServerInfo.SimpleServerResponse>() {

			@Override
			public void call(ServerInfo.SimpleServerResponse response) {
				if(errorHandler().isOk()){
					done();
					switch(response.code) {
					case 0: // Ok!
						event().onProjectAdded(m_cacheProject);
						break;
					case 1:
						event().errorAuthenticationRequired();
						break;
					case 2: // ServerError
						break;
					}
				}
			};
		});
	}
}
