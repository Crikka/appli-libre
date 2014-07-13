package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import retrofit.client.Response;
import adullact.publicrowdfunding.model.server.event.AuthenticationEvent;
import adullact.publicrowdfunding.model.server.request.AuthenticationRequest;

public class AuthenticationErrorHandler extends AuthenticatedErrorHandler<AuthenticationRequest, AuthenticationEvent, AuthenticationErrorHandler> {

}
