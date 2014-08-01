package adullact.publicrowdfunding.model.local.utilities;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import adullact.publicrowdfunding.model.local.ressource.Funding;

public class FundingTimePeriod {
	private Interval m_interval;
	private ArrayList<Funding> m_funding;
	
	public FundingTimePeriod(Interval interval) {
		this.m_interval = interval;
		this.m_funding = new ArrayList<Funding>();
	}
	
	public boolean correspondsToPeriod(DateTime dateTime){
		return m_interval.contains(dateTime);
	}
}
