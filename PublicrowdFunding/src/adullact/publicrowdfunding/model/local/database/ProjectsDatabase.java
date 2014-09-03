package adullact.publicrowdfunding.model.local.database;

import java.util.ArrayList;

import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Ferrand and Nelaupe
 */
public class ProjectsDatabase {
    /* --- Singleton --- */
    private static ProjectsDatabase m_instance = null;
    public static ProjectsDatabase getInstance() {
        if(m_instance == null) {
            m_instance = new ProjectsDatabase();
        }

        return m_instance;
    }
    private ProjectsDatabase() {
        m_helper = new ProjectsDatabaseHelper();
    }
    /* ----------------- */

    private ProjectsDatabaseHelper m_helper;

    public ArrayList<Project> get() {
        ArrayList<Project> res = new ArrayList<Project>();
        SQLiteDatabase db = m_helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ProjectsTable.COLUMN_NAME_ID,
                ProjectsTable.COLUMN_NAME_ACTIVE,
                ProjectsTable.COLUMN_NAME_TITLE,
                ProjectsTable.COLUMN_NAME_DESCRIPTION,
                ProjectsTable.COLUMN_NAME_VALIDATE,
                ProjectsTable.COLUMN_NAME_PROPOSED_BY,
                ProjectsTable.COLUMN_NAME_REQUESTED_FUNDING,
                ProjectsTable.COLUMN_NAME_CURRENT_FUNDING,
                ProjectsTable.COLUMN_NAME_CREATION_DATE,
                ProjectsTable.COLUMN_NAME_BEGIN_DATE,
                ProjectsTable.COLUMN_NAME_END_DATE,
                ProjectsTable.COLUMN_NAME_LATITUDE,
                ProjectsTable.COLUMN_NAME_LONGITUDE,
                ProjectsTable.COLUMN_NAME_ILLUSTRATION,
                ProjectsTable.COLUMN_NAME_CONTACT_ADDRESS,
                ProjectsTable.COLUMN_NAME_CONTACT_PHONE,
                ProjectsTable.COLUMN_NAME_CONTACT_WEBSITE
        };

        Cursor cursor = db.query(
                ProjectsTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        while (cursor.moveToNext()) {
            Project project = new Project(
                    cursor.getInt(0), // id
                    (cursor.getInt(1) == 1), // validate
                    cursor.getString(2), // title
                    cursor.getString(3), // description
                    (cursor.getInt(4) == 1), // validate
                    cursor.getString(5), // proposed by
                    cursor.getString(6), // requested funding
                    cursor.getString(7), // current funding
                    cursor.getString(8), // creation date
                    cursor.getString(9), // begin date
                    cursor.getString(10), // end date
                    cursor.getDouble(11), // latitude
                    cursor.getDouble(12), // longitude
                    cursor.getInt(13), // illustration
                    cursor.getString(14), // email
                    cursor.getString(15), // phone
                    cursor.getString(16) // website
            );

            res.add(project);
        }

        cursor.close();

        return res;

    }

    public void put(Project project) {
        SQLiteDatabase db = m_helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProjectsTable.COLUMN_NAME_ID, project.getResourceId());
        values.put(ProjectsTable.COLUMN_NAME_ACTIVE, project.isActive() ? 1 : 0);
        values.put(ProjectsTable.COLUMN_NAME_TITLE, project.getName());
        values.put(ProjectsTable.COLUMN_NAME_DESCRIPTION, project.getDescription());
        values.put(ProjectsTable.COLUMN_NAME_VALIDATE, project.isValidate() ? 1 : 0);
        values.put(ProjectsTable.COLUMN_NAME_PROPOSED_BY, project.getUser().getResourceId());
        values.put(ProjectsTable.COLUMN_NAME_REQUESTED_FUNDING, project.getRequestedFunding());
        values.put(ProjectsTable.COLUMN_NAME_CURRENT_FUNDING, project.getCurrentFunding());
        values.put(ProjectsTable.COLUMN_NAME_CREATION_DATE, Utility.DateTimeToString(project.getCreationDate()));
        values.put(ProjectsTable.COLUMN_NAME_BEGIN_DATE, Utility.DateTimeToString(project.getFundingInterval().getStart()));
        values.put(ProjectsTable.COLUMN_NAME_END_DATE, Utility.DateTimeToString(project.getFundingInterval().getEnd()));
        values.put(ProjectsTable.COLUMN_NAME_LATITUDE, project.getPosition().latitude);
        values.put(ProjectsTable.COLUMN_NAME_LONGITUDE, project.getPosition().longitude);
        values.put(ProjectsTable.COLUMN_NAME_ILLUSTRATION, project.getIllustration());
        values.put(ProjectsTable.COLUMN_NAME_CONTACT_ADDRESS, project.getEmail());
        values.put(ProjectsTable.COLUMN_NAME_CONTACT_PHONE, project.getPhone());
        values.put(ProjectsTable.COLUMN_NAME_CONTACT_WEBSITE, project.getWebsite());

        db.insert(ProjectsTable.TABLE_NAME, null, values);
    }

    public void update(Project project) {
        SQLiteDatabase db = m_helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProjectsTable.COLUMN_NAME_ACTIVE, !project.isActive() ? 0 : 1);
        values.put(ProjectsTable.COLUMN_NAME_TITLE, project.getName());
        values.put(ProjectsTable.COLUMN_NAME_DESCRIPTION, project.getDescription());
        values.put(ProjectsTable.COLUMN_NAME_VALIDATE, !project.isValidate() ? 0 : 1);
        values.put(ProjectsTable.COLUMN_NAME_PROPOSED_BY, project.getUser().getResourceId());
        values.put(ProjectsTable.COLUMN_NAME_REQUESTED_FUNDING, project.getRequestedFunding());
        values.put(ProjectsTable.COLUMN_NAME_CURRENT_FUNDING, project.getCurrentFunding());
        values.put(ProjectsTable.COLUMN_NAME_CREATION_DATE, Utility.DateTimeToString(project.getCreationDate()));
        values.put(ProjectsTable.COLUMN_NAME_BEGIN_DATE, Utility.DateTimeToString(project.getFundingInterval().getStart()));
        values.put(ProjectsTable.COLUMN_NAME_END_DATE, Utility.DateTimeToString(project.getFundingInterval().getEnd()));
        values.put(ProjectsTable.COLUMN_NAME_LATITUDE, project.getPosition().latitude);
        values.put(ProjectsTable.COLUMN_NAME_LONGITUDE, project.getPosition().longitude);
        values.put(ProjectsTable.COLUMN_NAME_ILLUSTRATION, project.getIllustration());
        values.put(ProjectsTable.COLUMN_NAME_CONTACT_ADDRESS, project.getEmail());
        values.put(ProjectsTable.COLUMN_NAME_CONTACT_PHONE, project.getPhone());
        values.put(ProjectsTable.COLUMN_NAME_CONTACT_WEBSITE, project.getWebsite());

        String selection = ProjectsTable.COLUMN_NAME_ID;
        String[] selectionArgs = {project.getResourceId()};

        db.update(ProjectsTable.TABLE_NAME, values, selection, selectionArgs);
    }

    public void delete(Project project) {
        SQLiteDatabase db = m_helper.getWritableDatabase();

        String selection = ProjectsTable.COLUMN_NAME_ID;
        String[] selectionArgs = {project.getResourceId()};

        db.delete(ProjectsTable.TABLE_NAME, selection, selectionArgs);
    }

}
