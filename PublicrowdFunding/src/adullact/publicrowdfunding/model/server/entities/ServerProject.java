package adullact.publicrowdfunding.model.server.entities;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Ferrand on 21/07/2014.
 */
public class ServerProject {
    public int id;
    public boolean active;
    public String proposedBy;
    public boolean validate;
    public String name;
    public String description;
    public String currentFunding;
    public String requestedFunding;
    public String creationDate;
    public String beginDate;
    public String endDate;
    public double latitude;
    public double longitude;
    public int illustration;
    public String email;
    public String website;
    public String phone;
}
