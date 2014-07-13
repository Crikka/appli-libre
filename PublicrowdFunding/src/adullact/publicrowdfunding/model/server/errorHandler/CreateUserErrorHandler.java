package adullact.publicrowdfunding.model.server.errorHandler;

import android.util.Log;

import java.io.IOException;

import adullact.publicrowdfunding.model.server.event.CreateUserEvent;
import adullact.publicrowdfunding.model.server.request.CreateUserRequest;
import retrofit.RetrofitError;

public class CreateUserErrorHandler extends AnonymousErrorHandler<CreateUserRequest, CreateUserEvent, CreateUserErrorHandler>  {

}
