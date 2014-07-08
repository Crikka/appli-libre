package adullact.publicrowdfunding.model.server.errorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.event.UsersListingEvent;
import adullact.publicrowdfunding.model.server.request.UsersListingRequest;

public class UsersListingErrorHandler extends AuthenticatedErrorHandler<UsersListingRequest, UsersListingEvent, UsersListingErrorHandler> {

	@Override
	public Throwable handleError(RetrofitError error) {
		System.out.println("souci 2 ");
		try {
			System.out.println(streamToString(error.getResponse().getBody().in()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(error.getMessage());
		return null;
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
