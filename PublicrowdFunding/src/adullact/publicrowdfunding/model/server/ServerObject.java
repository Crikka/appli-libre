package adullact.publicrowdfunding.model.server;

import adullact.publicrowdfunding.model.server.errorHandler.ErrorHandler;
import adullact.publicrowdfunding.model.server.event.Event;
import adullact.publicrowdfunding.model.server.request.Request;

/**
 * @author Ferrand and Nelaupe
 */
public class ServerObject
<TRequest extends Request<TRequest, TEvent, TErrorHandler>, 
TEvent extends Event<TRequest, TEvent, TErrorHandler>, 
TErrorHandler extends ErrorHandler<TRequest, TEvent, TErrorHandler>> {

	private TRequest m_request;
	private TEvent m_event;
	private TErrorHandler m_errorHandler;

	public ServerObject() {
		this.m_request = null;
		this.m_event = null;
		this.m_errorHandler = null;
	}

	public ServerObject(TRequest request, TEvent event, TErrorHandler errorHandler) {
		this.m_request = request;
		this.m_event = event;
		this.m_errorHandler = errorHandler;
		
		triumvirat();
	}

	@SuppressWarnings("unchecked")
	public ServerObject(TEvent event, TErrorHandler errorHandler) {
		this.m_request = (TRequest) this;
		this.m_event = event;
		this.m_errorHandler = errorHandler;
		
		triumvirat();
	}

	@SuppressWarnings("unchecked")
	public ServerObject(TRequest request, TErrorHandler errorHandler) {
		this.m_request = request;
		this.m_event = (TEvent) this;
		this.m_errorHandler = errorHandler;
		
		triumvirat();
	}

	@SuppressWarnings("unchecked")
	public ServerObject(TRequest request, TEvent event) {
		this.m_request = request;
		this.m_event = event;
		this.m_errorHandler = (TErrorHandler) this;
		
		triumvirat();
	}

	private void triumvirat() {
		m_request.defineRequest(m_request);
		m_request.defineEvent(m_event);
		m_request.defineErrorHandler(m_errorHandler);

		m_event.defineRequest(m_request);
		m_event.defineEvent(m_event);
		m_event.defineErrorHandler(m_errorHandler);

		m_errorHandler.defineRequest(m_request);
		m_errorHandler.defineEvent(m_event);
		m_errorHandler.defineErrorHandler(m_errorHandler);

	}

	protected TRequest request() {
		return m_request;
	}

	protected TEvent event() {
		return m_event;
	}

	protected TErrorHandler errorHandler() {
		return m_errorHandler;
	}

	protected void defineRequest(TRequest request) {
		m_request = request;
	}

	protected void defineEvent(TEvent event) {
		m_event = event;
	}

	protected void defineErrorHandler(TErrorHandler errorHandler) {
		m_errorHandler = errorHandler;
	}

}
