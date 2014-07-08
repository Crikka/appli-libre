package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.event.ProjectsListingEvent;
import adullact.publicrowdfunding.model.server.request.ProjectsListingRequest;

public class ProjectsListingErrorHandler extends AuthenticatedErrorHandler<ProjectsListingRequest, ProjectsListingEvent, ProjectsListingErrorHandler> {

}
