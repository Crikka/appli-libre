package adullact.publicrowdfunding.model.server.entities;

import java.util.ArrayList;

/**
 * Created by Ferrand on 21/07/2014.
 */
public class DetailedServerProject extends ServerProject {

    public ArrayList<ServerFunding> fundedBy;
    public ArrayList<ServerBookmark> bookmarkedBy;
    public ArrayList<ServerCommentary> commentedBy;
}