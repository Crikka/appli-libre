package adullact.publicrowdfunding.shared;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.google.android.gms.maps.model.LatLng;

public class Project {
	private UUID m_id;
	private String m_name;
	private String m_description;
	private BigDecimal m_requestedFunding;
	private BigDecimal m_currentFunding;
	private DateTime m_creationDate;
	private Interval m_fundingInterval;
	private LatLng m_position;
	private ArrayList<FundingTimePeriod> m_fundingTimePeriods;
	private boolean m_validate;
	private int m_illustration;

	public Project(String name, String description, String requestedFunding, Date creationDate, Date beginDate, Date endDate, LatLng position, int illustration) {
		this.m_id = UUID.randomUUID();
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

	public String id() {
		return m_id.toString();
	}

	public String name() {
		return m_name;
	}

	public String description(){
		return m_description;
	}

    public String requestedFunding(){
        return m_requestedFunding.toString();
    }

    public String currentFunding(){
        return m_currentFunding.toString();
    }

    public DateTime creationDate() {
        return m_creationDate;
    }

    public LatLng position(){
		return m_position;
	}

    public boolean isValidate() {
        return m_validate;
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
	
	public int illustration(){
		return this.m_illustration;
	}

}
