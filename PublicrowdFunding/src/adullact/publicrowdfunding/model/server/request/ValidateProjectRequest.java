package adullact.publicrowdfunding.model.server.request;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import adullact.publicrowdfunding.model.server.ServerInfo;
import adullact.publicrowdfunding.model.server.errorHandler.ValidateProjectErrorHandler;
import adullact.publicrowdfunding.model.server.event.ValidateProjectEvent;
import adullact.publicrowdfunding.shared.Project;

public class ValidateProjectRequest extends AuthenticatedRequest<ValidateProjectRequest, ValidateProjectEvent, ValidateProjectErrorHandler> implements ConcernProject {
	private Project m_project;
    private boolean m_toValidate;

	public ValidateProjectRequest(Project project, boolean toValidate, ValidateProjectEvent event) {
		super(event, new ValidateProjectErrorHandler());
		
		this.m_project = project;
        this.m_toValidate = toValidate;
	}

	@Override
	public void execute() {
        ServerProject serverProject = new ServerProject();
        serverProject.id = m_project.id();
        serverProject.name = m_project.name();
        serverProject.description = m_project.description();
        serverProject.requestedFunding = m_project.requestedFunding();
        serverProject.currentFunding = m_project.currentFunding();
        serverProject.creationDate = m_project.creationDate().toDate();
        serverProject.validate = m_toValidate;

        service().modifyProject(m_project.id(), serverProject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Func1<Throwable, SimpleServerResponse>() {

                    @Override
                    public SimpleServerResponse call(Throwable arg0) {
                        return null;
                    }

                })
                .subscribe(new Action1<SimpleServerResponse>() {

                    @Override
                    public void call(SimpleServerResponse response) {
                        if (response == null) {
                            errorHandler().manageCallback();
                            return;
                        }

                        switch (response.code) {
                            case 0: // Ok!
                                done();
                                m_project.validate();
                                event().onValidateProject(m_project);
                                break;
                            case 1:
                                break;
                            case 2: // ServerError
                                break;
                        }
                    }
                });
    }

	@Override
	public Project project() {
		return m_project;
	}
	
}
