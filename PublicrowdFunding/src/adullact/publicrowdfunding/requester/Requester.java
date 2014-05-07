package adullact.publicrowdfunding.requester;

import adullact.publicrowdfunding.reply.Reply;
import adullact.publicrowdfunding.request.Request;

/**
 * 
 * @author Ferrand
 *
 * @param <TRequest>
 */
public abstract class Requester<TRequest extends Request<?>> {
	/**
	 * 
	 * @param request
	 * @return
	 */
	public abstract Reply<TRequest> post(TRequest request);
}
