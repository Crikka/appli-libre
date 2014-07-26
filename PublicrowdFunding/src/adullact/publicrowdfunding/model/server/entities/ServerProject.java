package adullact.publicrowdfunding.model.server.entities;

import org.joda.time.DateTime;

/**
 * Created by Ferrand on 21/07/2014.
 */
public class ServerProject {
    public String id;
    public String proposedBy;
    public boolean validate;
    public String name;
    public String description;
    public String currentFunding;
    public String requestedFunding;
    public DateTime creationDate;
    public DateTime beginDate;
    public DateTime endDate;
    public double latitude;
    public double longitude;
    public int illustration;
}
