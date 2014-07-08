package adullact.publicrowdfunding.model.server.errorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.event.UsersListingEvent;
import adullact.publicrowdfunding.model.server.request.UsersListingRequest;

public class UsersListingErrorHandler extends AuthenticatedErrorHandler<UsersListingRequest, UsersListingEvent, UsersListingErrorHandler> {

}
