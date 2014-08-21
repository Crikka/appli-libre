package adullact.publicrowdfunding.model.local.utilities;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import adullact.publicrowdfunding.model.local.ressource.Funding;

public class FundingInterval {
	private Interval m_interval;
	private ArrayList<Funding> m_funding;
	
	public FundingInterval(Interval interval) {
		this.m_interval = interval;
		this.m_funding = new ArrayList<Funding>();
	}

    public void addFunding(Funding funding) {
        m_funding.add(funding);
    }

    public long getTotal() {
        BigDecimal result = BigDecimal.ZERO;

        for(Funding funding : m_funding) {
            result = result.add(funding.getValue());
        }

        return result.longValue();
    }
}
