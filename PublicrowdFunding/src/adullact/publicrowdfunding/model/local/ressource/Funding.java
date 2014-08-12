package adullact.publicrowdfunding.model.local.ressource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import org.joda.time.DateTime;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.server.entities.ServerFunding;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.shared.Utility;
import rx.Observable;

public class Funding extends Resource<Funding, ServerFunding, ServerFunding> {
    private int m_id;
	private Cache<User> m_from;
	private Cache<Project> m_to;
    private String m_transactionId;
	private BigDecimal m_value;
	private DateTime m_date;
	
	public Funding() {
        super();
    }

    public Funding(User from, Project to, String transactionId, String value) {
		super();

		this.m_from = from.getCache();
		this.m_to = to.getCache();
        this.m_transactionId = transactionId;
		this.m_value = new BigDecimal(value);
		this.m_date = DateTime.now();
	}


    /* --- Resource --- */
    @Override
    public String getResourceId() {
        return Integer.toString(m_id);
    }

    @Override
    protected void setResourceId(String id) {
        m_id = Integer.parseInt(id);
    }

    @Override
    public ServerFunding toServerResource() {
        ServerFunding serverFunding = new ServerFunding();
        serverFunding.id = m_id;
        serverFunding.transactionId = m_transactionId;
        serverFunding.username = m_from.getResourceId();
        serverFunding.projectID = m_to.getResourceId();
        serverFunding.value = m_value.toPlainString();
        serverFunding.creationDate = Utility.DateTimeToString(m_date);

        return serverFunding;
    }

    @Override
    public Funding makeCopyFromServer(ServerFunding serverFunding) {
        Funding funding = new Funding();
        funding.m_id = serverFunding.id;
        funding.m_transactionId = serverFunding.transactionId;
        funding.m_from = new User().getCache(serverFunding.username);
        funding.m_to = new Project().getCache(serverFunding.projectID);
        funding.m_value = new BigDecimal(serverFunding.value);
        funding.m_date = Utility.stringToDateTime(serverFunding.creationDate);

        return funding;
    }

    @Override
    public Funding syncFromServer(ServerFunding serverFunding) {
        this.m_id = serverFunding.id;
        this.m_transactionId = serverFunding.transactionId;
        this.m_from = new User().getCache(serverFunding.username);
        this.m_to = new Project().getCache(serverFunding.projectID);
        this.m_value = new BigDecimal(serverFunding.value);
        this.m_date = Utility.stringToDateTime(serverFunding.creationDate);

        return this;
    }

    @Override
    public Observable<ServerFunding> methodGET(Service service) {
        return service.detailFunding(getResourceId());
    }

    @Override
    public Observable<ArrayList<ServerFunding>> methodGETAll(Service service, Map<String, String> filter) {
        return service.listFunding(filter);
    }

    @Override
    public Observable<SimpleServerResponse> methodPUT(Service service) {
        return service.modifyFunding(getResourceId(), toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodPOST(Service service) {
        return service.createFunding(toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodDELETE(Service service) {
        return service.deleteFunding(getResourceId());
    }
    /* ---------------- */
}
