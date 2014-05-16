package adullact.publicrowdfunding.model.event;

import adullact.publicrowdfunding.model.request.CreateProjectRequest;
import adullact.publicrowdfunding.shared.Project;

public abstract class CreateProjectEvent extends ProjectEvent<CreateProjectEvent, CreateProjectRequest> implements AuthentificationRequired, AdministratorFavour {
	
	/* Callback functions */
	public abstract void onProjectAdded(Project project);
	/* ----------------- */
	
	@Override
	protected void retry() {
		if(!request().isDone()) {
			request().execute(this);
		}
	}
	
}