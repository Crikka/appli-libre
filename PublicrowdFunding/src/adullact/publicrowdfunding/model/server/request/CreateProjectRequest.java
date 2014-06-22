package adullact.publicrowdfunding.model.server.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.UUID;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import com.google.android.gms.maps.model.LatLng;

import adullact.publicrowdfunding.model.server.errorHandler.CreateProjectErrorHandler;
import adullact.publicrowdfunding.model.server.event.CreateProjectEvent;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.Share;
import android.app.Application.OnProvideAssistDataListener;

public class CreateProjectRequest extends AuthentificatedRequest<CreateProjectRequest, CreateProjectEvent, CreateProjectErrorHandler> {
	private ServerProject m_project;
	private Project m_cacheProject;

	public CreateProjectRequest(String name, String description, String requestedFunding, Date beginDate, Date endDate, LatLng position, CreateProjectEvent event) {
		super(event, new CreateProjectErrorHandler());

		m_service = m_restAdapter.create(CreateProjectService.class);

		m_cacheProject = new Project(name, description, requestedFunding, new Date(), beginDate, endDate, position);
		this.m_project = new ServerProject();
		m_project.id = UUID.randomUUID().toString();
		m_project.name = name;
		m_project.description = description;
		m_project.requestedFunding = requestedFunding;
		m_project.beginDate = beginDate;
		m_project.endDate = endDate;
		m_project.position = position;
	}

	/* Communication interface */
	private final CreateProjectService m_service;
	private class ServerProject {
		public String id;
		public String name;
		public String description;
		public String requestedFunding;
		public Date beginDate;
		public Date endDate;
		public LatLng position;
	}
	private class ResponseServer {
		public Integer returnCode; 
	}
	private interface CreateProjectService {
		@POST("/user/{username}")
		Observable<ResponseServer> createProject(@Body ServerProject serverProject, @Path(value = "username") String username);
	}
	/* --------- */


	@Override
	public void execute() {
		m_service.createProject(m_project, Share.user.pseudo()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
		.onErrorReturn(new Func1<Throwable, ResponseServer>() {

			@Override
			public ResponseServer call(Throwable arg0) {
				return null;
			}

		})
		.subscribe(new Action1<ResponseServer>() {

			@Override
			public void call(ResponseServer response) {
				if(errorHandler().isOk()){
					done();
					switch(response.returnCode) {
					case 0: // Ok!
						event().onProjectAdded(m_cacheProject);
						break;
					case 1:
						event().errorAuthentificationRequired();
						break;
					case 2: // ServerError
						break;
					}
				}
			};
		});
	}

	private String streamToString(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

}
