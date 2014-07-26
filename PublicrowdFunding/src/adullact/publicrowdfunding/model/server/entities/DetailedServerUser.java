package adullact.publicrowdfunding.model.server.entities;

import java.util.ArrayList;

/**
 * Created by Ferrand on 21/07/2014.
 */
public class DetailedServerUser extends ServerUser {
    public ArrayList<ServerProject> projects; // All project
    public ArrayList<ServerFunding> fundedProjects;
    public ArrayList<ServerBookmark> bookmarkedProjects;
    public ArrayList<ServerCommentary> commentaries;
}
