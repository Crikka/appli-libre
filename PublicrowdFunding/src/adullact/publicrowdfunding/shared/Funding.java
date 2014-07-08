package adullact.publicrowdfunding.shared;

import java.math.BigDecimal;

import org.joda.time.DateTime;

public class Funding {
	private User m_from;
	private Project m_to;
	private BigDecimal m_value;
	private DateTime m_date;
	
	public Funding(User from, Project to, String value, DateTime date) {
		super();
		
		this.m_from = from;
		this.m_to = to;
		this.m_value = new BigDecimal(value);
		this.m_date = date;
	}
	
	
}
