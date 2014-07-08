package adullact.publicrowdfunding.model.server.request;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.errorHandler.UsersListingErrorHandler;
import adullact.publicrowdfunding.model.server.event.UsersListingEvent;
import adullact.publicrowdfunding.shared.Project;
import adullact.publicrowdfunding.shared.User;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class UsersListingRequest extends AuthenticatedRequest<UsersListingRequest, UsersListingEvent, UsersListingErrorHandler> {
	private String m_projectId;

	public UsersListingRequest(Project project, UsersListingEvent event) {
		super(event, new UsersListingErrorHandler());

		m_projectId = project == null ? null : project.getId();
	}


	@Override
	public void execute() {
		if(m_projectId == null) {
			service().listUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
			.onErrorReturn(new Func1<Throwable, ArrayList<ServerInfo.ServerUser>>() {

				@Override
				public ArrayList<ServerInfo.ServerUser> call(Throwable arg0) {
					return null;
				}

			})
			.subscribe(new Action1<ArrayList<ServerInfo.ServerUser>>() {

				@Override
				public void call(ArrayList<ServerInfo.ServerUser> response) {
					done();
					ArrayList<User> users = new ArrayList<User>();
					for(ServerInfo.ServerUser user : response) {
						User buffer = new User();
						buffer.defineFields(user.username, null, user.name, user.firstName);
						users.add(buffer);
					}

					event().onUsersReceived(users);
				}
			});
		}
		else {
			service().listUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
			.onErrorReturn(new Func1<Throwable, ArrayList<ServerInfo.ServerUser>>() {

				@Override
				public ArrayList<ServerInfo.ServerUser> call(Throwable arg0) {
					return null;
				}

			})
			.subscribe(new Action1<ArrayList<ServerInfo.ServerUser>>() {

				@Override
				public void call(ArrayList<ServerInfo.ServerUser> response) {
					done();
					ArrayList<User> users = new ArrayList<User>();
					for(ServerInfo.ServerUser user : response) {
						User buffer = new User();
						buffer.defineFields(user.username, null, user.name, user.firstName);
						users.add(buffer);
					}

					event().onUsersReceived(users);					
				}
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
