package adullact.publicrowdfunding.model.server.entities;

import java.util.ArrayList;

/**
 * @author Ferrand and Nelaupe
 */
public class DetailedServerProject extends ServerProject {

    public ArrayList<ServerFunding> fundedBy;
    public ArrayList<ServerBookmark> bookmarkedBy;
    public ArrayList<ServerCommentary> commentedBy;
}