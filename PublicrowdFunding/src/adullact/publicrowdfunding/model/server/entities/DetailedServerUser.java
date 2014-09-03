package adullact.publicrowdfunding.model.server.entities;

import java.util.ArrayList;

/**
 * @author Ferrand and Nelaupe
 */
public class DetailedServerUser extends ServerUser {
    public ArrayList<ServerProject> proposedProjects;
    public ArrayList<ServerFunding> fundedProjects;
    public ArrayList<ServerBookmark> bookmarkedProjects;
    public ArrayList<ServerCommentary> commentaries;
}
