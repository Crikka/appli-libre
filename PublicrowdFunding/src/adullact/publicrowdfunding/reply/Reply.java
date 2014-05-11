package adullact.publicrowdfunding.reply;

import adullact.publicrowdfunding.request.Request;

/**
 * @author Ferrand
 * @param <TRequest>
 */
public class Reply<TRequest extends Request> {
	private TRequest m_request;
	private boolean m_ok;
	
	/**
	 * @param request
	 * @brief Constructor with true for ok
	 */
	public Reply(TRequest request){
		this.m_request = request;
		this.m_ok = true;
	}
	
	public Reply(TRequest request, boolean ok){
		this.m_request = request;
		this.m_ok = ok;
	}
	
	public TRequest request() {
		return m_request;
	}
	
	public boolean ok() {
		return m_ok;
	}
	
}
