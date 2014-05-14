package adullact.publicrowdfunding.shared;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

public class Project {
	private UUID m_id;
	private String m_name;
	private String m_description;
	private BigDecimal m_requestedFunding;
	private BigDecimal m_currentFunding;
	private DateTime m_creationDate;
	private Interval m_fundingInterval;
	private ArrayList<FundingTimePeriod> m_fundingTimePeriods;

	public Project(String name, String description, String requestedFunding, Date creationDate, Date beginDate, Date endDate) {
		this.m_id = UUID.randomUUID();
		this.m_name = name;
		this.m_description = description;
		this.m_requestedFunding = new BigDecimal(requestedFunding);
		this.m_currentFunding = new BigDecimal("0");
		this.m_creationDate = new DateTime(creationDate.getTime());
		this.m_fundingInterval = new Interval(new DateTime(beginDate.getTime()), new DateTime(endDate.getTime()));
		this.m_fundingTimePeriods = new ArrayList<FundingTimePeriod>();
		
		// Now, we calculate 10 periods for graphics
		int numberOfPeriod = 10;
		DateTime startDateTime = m_fundingInterval.getStart();
		DateTime endDateTime = m_fundingInterval.getEnd();
		int numberOfDayBetweenStartAndEnd = (int) m_fundingInterval.toDuration().getStandardDays();
		int dayByPeriod = numberOfDayBetweenStartAndEnd/numberOfPeriod;
		
		System.out.println("Creation de tranche de " + dayByPeriod + " jour");
		for(int i = 0; i < (numberOfPeriod-1); i++){
			m_fundingTimePeriods.add(new FundingTimePeriod(new Interval(startDateTime, startDateTime.plusDays(dayByPeriod))));
			startDateTime = startDateTime.plusDays(dayByPeriod);
		}
		m_fundingTimePeriods.add(new FundingTimePeriod(new Interval(startDateTime, endDateTime)));
	}

	public String id() {
		return m_id.toString();
	}

	public String getName() {
		return m_name;
	}

	public String getDescription(){
		return m_description;
	}

	/**
	 * @return percent of achievement, may be upper than 100.
	 */
	public int percentOfAchievement() {
		return ((m_currentFunding.divide(m_requestedFunding)).multiply(BigDecimal.TEN).multiply(BigDecimal.TEN)).intValue();
	}

	/**
	 * @param value
	 * @brief Add value to current funding.
	 */
	public void finance(String value) {
		m_currentFunding = m_currentFunding.add(new BigDecimal(value));
	}

	public DateTime date() {
		return m_creationDate;
	}

}
