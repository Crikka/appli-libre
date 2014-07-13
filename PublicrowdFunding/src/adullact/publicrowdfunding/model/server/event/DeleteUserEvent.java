package adullact.publicrowdfunding.model.server.event;

import adullact.publicrowdfunding.model.server.errorHandler.DeleteUserErrorHandler;
import adullact.publicrowdfunding.model.server.request.DeleteUserRequest;

public abstract class DeleteUserEvent extends AuthenticatedEvent<DeleteUserRequest, DeleteUserEvent, DeleteUserErrorHandler>  {

	/* Callback functions */
    public abstract void errorUsernameDoesNotExist(String username);
    public abstract void onDeleteUser(String username);
    /* ----------------- */

}