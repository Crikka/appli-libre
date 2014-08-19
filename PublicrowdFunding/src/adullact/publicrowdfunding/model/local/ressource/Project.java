package adullact.publicrowdfunding.model.local.ressource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import com.google.android.gms.maps.model.LatLng;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.cache.CacheSet;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.utilities.FundingTimePeriod;
import adullact.publicrowdfunding.model.server.entities.DetailedServerProject;
import adullact.publicrowdfunding.model.server.entities.ServerCommentary;
import adullact.publicrowdfunding.model.server.entities.ServerFunding;
import adullact.publicrowdfunding.model.server.entities.ServerProject;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import adullact.publicrowdfunding.model.server.event.ListerEvent;
import adullact.publicrowdfunding.model.server.request.ListerRequest;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import rx.Observable;

public class Project extends Resource<Project, ServerProject, DetailedServerProject> {

    /* ----- Resource ----- */
    @Override
    public String getResourceId() {
        if(m_id == null) {
            return null;
        }

        return m_id.toString();
    }

    @Override
    public void setResourceId(String id) {
        this.m_id = Integer.parseInt(id);
    }

    @Override
    public ServerProject toServerResource() {
        ServerProject serverProject = new ServerProject();
        serverProject.id = m_id;
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
        res.m_description = serverProject.description;
        res.m_funding = new CacheSet<Funding>();
        res.m_commentaries = new CacheSet<Commentary>();
        res.m_proposedBy = new User().getCache(serverProject.proposedBy);
        res.m_requestedFunding = new BigDecimal(serverProject.requestedFunding);
        res.m_currentFunding = new BigDecimal(serverProject.currentFunding);
        res.m_creationDate = Utility.stringToDateTime(serverProject.creationDate);
        res.m_position = new LatLng(serverProject.latitude, serverProject.longitude);
        res.m_validate = serverProject.validate;
        res.m_illustration = serverProject.illustration;
        res.m_fundingInterval = new Interval(Utility.stringToDateTime(serverProject.beginDate), Utility.stringToDateTime(serverProject.endDate));

        return res;
    }

    @Override
    public Project syncFromServer(DetailedServerProject detailedServerProject) {
        this.m_id = detailedServerProject.id;
        this.m_name = detailedServerProject.name;
        this.m_description = detailedServerProject.description;
        this.m_funding = new CacheSet<Funding>();
        this.m_commentaries = new CacheSet<Commentary>();
        this.m_proposedBy = new User().getCache(detailedServerProject.proposedBy);
        this.m_requestedFunding = new BigDecimal(detailedServerProject.requestedFunding);
        this.m_currentFunding = new BigDecimal(detailedServerProject.currentFunding);
        this.m_creationDate = Utility.stringToDateTime(detailedServerProject.creationDate);
        this.m_position = new LatLng(detailedServerProject.latitude, detailedServerProject.longitude);
        this.m_validate = detailedServerProject.validate;
        this.m_illustration = detailedServerProject.illustration;
        this.m_fundingInterval = new Interval(Utility.stringToDateTime(detailedServerProject.beginDate), Utility.stringToDateTime(detailedServerProject.endDate));
        this.m_fundingTimePeriods = new ArrayList<FundingTimePeriod>();
        this. m_funding = new CacheSet<Funding>();
        this.m_commentaries = new CacheSet<Commentary>();

        for(final ServerFunding serverFunding : detailedServerProject.fundedBy) {
            final Cache<Funding> funding = new Funding().getCache(Integer.toString(serverFunding.id)).declareUpToDate();
            funding.toResource(new HoldToDo<Funding>() {
                @Override
                public void hold(Funding resource) {
                    resource.syncFromServer(serverFunding);
                    m_funding.add(funding);
                }
            });
        }

        for(final ServerCommentary serverCommentary : detailedServerProject.commentedBy) {
            final Cache<Commentary> commentary = new Commentary().getCache(Integer.toString(serverCommentary.id)).declareUpToDate();
            commentary.toResource(new HoldToDo<Commentary>() {
                @Override
                public void hold(Commentary resource) {
                    resource.syncFromServer(serverCommentary);
                    m_commentaries.add(commentary);
                }
            });
        }

        // Now, we calculate 10 periods for graphics
        calculatePeriods();

        return this;
    }

    @Override
    public Observable<DetailedServerProject> methodGET(Service service) {
        return service.detailProject(getResourceId());
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

	private Integer m_id;
	private String m_name;
	private String m_description;
    private Cache<User> m_proposedBy;
    private CacheSet<Funding> m_funding;
    private CacheSet<Commentary> m_commentaries;
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
        this.m_id = null;
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
        calculatePeriods();
	}

    /**
     * Reserved for local database
     */
    public Project(Integer id, String name, String description, boolean validate, String proposedBy, String requestedFunding, String currentFunding, String creationDate, String beginDate, String endDate, Double latitude, Double longitude, Integer illustration) {
        this.m_id = id;
        this.m_name = name;
        this.m_description = description;
        this.m_proposedBy = new User().getCache(proposedBy);
        this.m_requestedFunding = new BigDecimal(requestedFunding);
        this.m_currentFunding = new BigDecimal(currentFunding);
        this.m_creationDate = DateTime.parse(creationDate);
        this.m_fundingInterval = new Interval(DateTime.parse(beginDate), DateTime.parse(endDate));
        this.m_fundingTimePeriods = new ArrayList<FundingTimePeriod>();
        this.m_position = new LatLng(latitude, longitude);
        this.m_validate = validate;
        this.m_illustration = illustration;

        // Now, we calculate 10 periods for graphics
        calculatePeriods();
    }

    private void calculatePeriods() {
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

    public void getUser(WhatToDo<User> userWhatToDo) {
        m_proposedBy.toResource(userWhatToDo);
    }
    
    public ArrayList<FundingTimePeriod> getFundtingTimeGraphData(){
    	return this.m_fundingTimePeriods;
    }

    public void getCommentaries(WhatToDo<Commentary> commentaryWhatToDo) {
        m_commentaries.forEach(commentaryWhatToDo);
    }

    public void validate() {
        m_validate = true;
    }
    
    public long getNumberOfDayToEnd(){
    	return new Duration(m_creationDate,m_fundingInterval.getEnd()).getStandardDays();
    			
    }
    
    public Interval getFundingInterval(){
    	return this.m_fundingInterval;
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
	public void finance(final String value, final CreateEvent<Funding> fundingCreateEvent) throws NoAccountExistsInLocal  {
		m_currentFunding = m_currentFunding.add(new BigDecimal(value));
	}

    public void postCommentary(final String title, final String text, final double mark, final CreateEvent<Commentary> commentaryCreateEvent) throws NoAccountExistsInLocal {
        Account account = Account.getOwn();
        final Project _this = this;
        account.getUser(new HoldToDo<User>() {
            @Override
            public void hold(User resource) {
                new Commentary(resource, _this, title, text, mark).serverCreate(new CreateEvent<Commentary>() {
                    @Override
                    public void errorResourceIdAlreadyUsed() {
                        commentaryCreateEvent.errorResourceIdAlreadyUsed();
                    }

                    @Override
                    public void onCreate(Commentary commentary) {
                        _this.m_commentaries.add(commentary.getCache());
                        commentaryCreateEvent.onCreate(commentary);
                    }

                    @Override
                    public void errorNetwork() {
                        commentaryCreateEvent.errorNetwork();
                    }

                    @Override
                    public void errorAuthenticationRequired() {
                        commentaryCreateEvent.errorAuthenticationRequired();
                    }
                });
            }
        });
    }
}
