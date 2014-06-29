package adullact.publicrowdfunding.model.server.errorHandler;

import retrofit.RetrofitError;
import adullact.publicrowdfunding.model.server.event.ProjectsListingEvent;
import adullact.publicrowdfunding.model.server.request.ProjectsListingRequest;

public class ProjectsListingErrorHandler extends AuthentificatedErrorHandler<ProjectsListingRequest, ProjectsListingEvent, ProjectsListingErrorHandler>{

	@Override
	public Throwable handleError(RetrofitError error) {
		// TODO Auto-generated method stub
		return null;
	}

}
