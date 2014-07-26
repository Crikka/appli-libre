package adullact.publicrowdfunding.model.local.ressource;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class FundingTimePeriod {
	private Interval m_interval;
	private ArrayList<Funding> m_fundings;
	
	public FundingTimePeriod(Interval interval) {
		this.m_interval = interval;
		this.m_fundings = new ArrayList<Funding>();
	}
	
	public boolean correspondsToPeriod(DateTime dateTime){
		return m_interval.contains(dateTime);
	}
}
