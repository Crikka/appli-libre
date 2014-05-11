package adullact.publicrowdfunding.shared;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Project {
    private UUID m_id;
	private String m_name;
	private String m_description;
	private BigDecimal m_requestedFunding;
	private BigDecimal m_currentFunding;
	private Date m_date;

	public Project(String name, String description, String requestedFunding) {
		this.m_id = UUID.randomUUID();
		this.m_name = name;
		this.m_description = description;
		this.m_requestedFunding = new BigDecimal(requestedFunding);
		this.m_currentFunding = new BigDecimal("0");
		this.m_date = new Date();
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
	
	public Date date() {
		return m_date;
	}

}
