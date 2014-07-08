package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.request.DeleteUserRequest;
import adullact.publicrowdfunding.model.server.event.DeleteUserEvent;

public class DeleteUserErrorHandler extends AuthenticatedErrorHandler<DeleteUserRequest, DeleteUserEvent, DeleteUserErrorHandler> {

    @Override
    public Throwable handleError(RetrofitError error) {
        System.out.println(error.getMessage());
        return null;
    }
}