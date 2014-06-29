package adullact.publicrowdfunding.model.server.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.errorHandler.UsersListingErrorHandler;
import adullact.publicrowdfunding.model.server.event.UsersListingEvent;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.User;

public class UsersListingRequest extends AuthentificatedRequest<UsersListingRequest, UsersListingEvent, UsersListingErrorHandler>  {
	private String m_projectId;

	public UsersListingRequest(Project project, UsersListingEvent event) {
		super(event, new UsersListingErrorHandler());

		m_service = m_restAdapter.create(UsersListingService.class);
		m_projectId = project == null ? null : project.getId();
	}

	/* Communication interface */
	private final UsersListingService m_service;
	private class UserServer {
		public String username;
		public String name;
		public String firstName;
		public String administrator;
	}
	private interface UsersListingService {
		@GET("/user/")
		Observable<ArrayList<UserServer>> listUsers();
		@GET("/user/")
		Observable<ArrayList<UserServer>> listUsers(@Query("projectId") String projectId);
	}
	/* --------- */


	@Override
	public void execute() {
		if(m_projectId == null) {
			m_service.listUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
			.onErrorReturn(new Func1<Throwable, ArrayList<UserServer>>() {

				@Override
				public ArrayList<UserServer> call(Throwable arg0) {
					return null;
				}

			})
			.subscribe(new Action1<ArrayList<UserServer>>() {

				@Override
				public void call(ArrayList<UserServer> response) {
					done();
					ArrayList<User> users = new ArrayList<User>();
					for(UserServer user : response) {
						User buffer = new User();
						buffer.defineFields(user.username, null, user.name, user.firstName);
						users.add(buffer);
					}

					event().onUsersReceived(users);
				};
			});
		}
		else {
			m_service.listUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
			.onErrorReturn(new Func1<Throwable, ArrayList<UserServer>>() {

				@Override
				public ArrayList<UserServer> call(Throwable arg0) {
					return null;
				}

			})
			.subscribe(new Action1<ArrayList<UserServer>>() {

				@Override
				public void call(ArrayList<UserServer> response) {
					done();
					ArrayList<User> users = new ArrayList<User>();
					for(UserServer user : response) {
						User buffer = new User();
						buffer.defineFields(user.username, null, user.name, user.firstName);
						users.add(buffer);
					}

					event().onUsersReceived(users);					
				};
			});
		}
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
