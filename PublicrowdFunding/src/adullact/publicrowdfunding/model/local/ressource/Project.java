package adullact.publicrowdfunding.model.local.ressource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.android.gms.maps.model.LatLng;

import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.utilities.FundingTimePeriod;
import adullact.publicrowdfunding.model.server.entities.DetailedServerProject;
import adullact.publicrowdfunding.model.server.entities.ServerProject;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.event.ListerEvent;
import adullact.publicrowdfunding.model.server.request.ListerRequest;
import rx.Observable;

public class Project extends Resource<Project, ServerProject, DetailedServerProject> {

    /* ----- Resource ----- */
    @Override
    public String getResourceId() {
        return m_id.toString();
    }

    @Override
    public ServerProject toServerResource() {
        ServerProject serverProject = new ServerProject();
        serverProject.id = getResourceId();
        serverProject.name = m_name;
        serverProject.description = m_description;
        serverProject.proposedBy = m_proposedBy.getResourceId();
        serverProject.requestedFunding = m_requestedFunding.toPlainString();
        serverProject.currentFunding = m_currentFunding.toPlainString();
        serverProject.creationDate = m_creationDate.toString();
        serverProject.latitude = m_position.latitude;
        serverProject.longitude = m_position.longitude;
        serverProject.validate = m_validate;
        serverProject.illustration = m_illustration;
        serverProject.beginDate = m_fundingInterval.getStart().toString();
        serverProject.endDate = m_fundingInterval.getEnd().toString();
        return serverProject;
    }

    @Override
    public Project makeCopyFromServer(ServerProject serverProject) {
        Project res = new Project();
        res.m_id = serverProject.id;
        res.m_name = serverProject.name;
        res.m_description = serverProject.description = m_description;
        //serverProject.proposedBy = m_proposedBy.getResourceId();
        res.m_requestedFunding = new BigDecimal(serverProject.requestedFunding);
        res.m_currentFunding = new BigDecimal(serverProject.currentFunding);
        /*serverProject.creationDate = m_creationDate;
        serverProject.latitude = m_position.latitude;
        serverProject.longitude = m_position.longitude;
        serverProject.validate = m_validate;
        serverProject.illustration = m_illustration;
        serverProject.beginDate = m_fundingInterval.getStart();
        serverProject.endDate = m_fundingInterval.getEnd();*/

        return res;
    }

    @Override
    public Project syncFromServer(DetailedServerProject detailedServerProject) {
        return null;
    }

    @Override
    public Observable<DetailedServerProject> methodGET(Service service) {
        return service.detailProject();
    }

    @Override
    public Observable<ArrayList<ServerProject>> methodGETAll(Service service, Map<String, String> filter) {
        return service.listProjects(filter);
    }

    @Override
    public Observable<SimpleServerResponse> methodPUT(Service service) {
        return service.modifyProject(getResourceId(), toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodPOST(Service service) {
        return service.createProject(toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodDELETE(Service service) {
        return service.deleteProject(getResourceId());
    }

    public void serverListerToSync(ListerEvent<Project> listerEvent, DateTime lastSync) {
        HashMap<String, String> filter = new HashMap<String, String>();
        filter.put("lastSync", lastSync.toString());
        (new ListerRequest<Project, ServerProject, DetailedServerProject>(this, filter, listerEvent)).execute();
    }
    /* -------------------- */

	private String m_id;
	private String m_name;
	private String m_description;
    private Cache<User> m_proposedBy;
	private BigDecimal m_requestedFunding;
	private BigDecimal m_currentFunding;
	private DateTime m_creationDate;
	private Interval m_fundingInterval;
	private LatLng m_position;
	private ArrayList<FundingTimePeriod> m_fundingTimePeriods;
	private boolean m_validate;
	private int m_illustration;

    public Project() {
        this.m_id = null;
        this.m_name = null;
        this.m_description = null;
        this.m_requestedFunding = null;
        this.m_currentFunding = null;
        this.m_creationDate = null;
        this.m_fundingInterval = null;
        this.m_fundingTimePeriods = null;
        this.m_position = null;
        this.m_validate = false;
        this.m_illustration = -1;
    }

	public Project(String name, String description, String requestedFunding, Date creationDate, Date beginDate, Date endDate, LatLng position, int illustration) {
		this.m_id = UUID.randomUUID().toString();
		this.m_name = name;
		this.m_description = description;
		this.m_requestedFunding = new BigDecimal(requestedFunding);
		this.m_currentFunding = new BigDecimal("0");
		this.m_creationDate = new DateTime(creationDate.getTime());
		this.m_fundingInterval = new Interval(new DateTime(beginDate.getTime()), new DateTime(endDate.getTime()));
		this.m_fundingTimePeriods = new ArrayList<FundingTimePeriod>();
		this.m_position = position;
		this.m_validate = false;
		this.m_illustration = illustration;
		
		// Now, we calculate 10 periods for graphics
		int numberOfPeriod = 10;
		DateTime startDateTime = m_fundingInterval.getStart();
		DateTime endDateTime = m_fundingInterval.getEnd();
		long numberOfDayBetweenStartAndEnd = m_fundingInterval.toDuration().getStandardDays();
		long dayByPeriod = numberOfDayBetweenStartAndEnd/numberOfPeriod;
		
		for(int i = 0; i < (numberOfPeriod-1); i++){
			m_fundingTimePeriods.add(new FundingTimePeriod(new Interval(startDateTime, startDateTime.plusDays((int) dayByPeriod))));
			startDateTime = startDateTime.plusDays((int) dayByPeriod);
		}
		m_fundingTimePeriods.add(new FundingTimePeriod(new Interval(startDateTime, endDateTime)));
	}

	public String getId() {
		return m_id;
	}

	public String getName() {
		return m_name;
	}

	public String getDescription(){
		return m_description;
	}

    public String getRequestedFunding(){
        return m_requestedFunding.toString();
    }

    public String getCurrentFunding(){
        return m_currentFunding.toString();
    }

    public DateTime getCreationDate() {
        return m_creationDate;
    }

    public LatLng getPosition(){
		return m_position;
	}

    public boolean isValidate() {
        return m_validate;
    }

    public int getIllustration(){
        return this.m_illustration;
    }

    public void validate() {
        m_validate = true;
    }

	/**
	 * @return percent of achievement, may be upper than 100.
	 */
	public int getPercentOfAchievement() {
		return ((m_currentFunding.divide(m_requestedFunding)).multiply(BigDecimal.TEN).multiply(BigDecimal.TEN)).intValue();
	}

	/**
	 * @param value
	 * @brief Add value to current funding.
	 */
	public void finance(String value) {
		m_currentFunding = m_currentFunding.add(new BigDecimal(value));
	}
}
