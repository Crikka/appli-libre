package adullact.publicrowdfunding.model.local.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import adullact.publicrowdfunding.model.local.ressource.Project;

/**
 * Created by Ferrand on 20/07/2014.
 */
public class ProjectsDatabase {
    /* --- Singleton --- */
    private static ProjectsDatabase m_instance = null;
    private static ProjectsDatabase getInstance() {
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
                ProjectsTable.COLUMN_NAME_TITLE,
                ProjectsTable.COLUMN_NAME_DESCRIPTION,
                ProjectsTable.COLUMN_NAME_VALIDATE,
                ProjectsTable.COLUMN_NAME_PROPOSED_BY,
                ProjectsTable.COLUMN_NAME_REQUESTED_FUNDING,
                ProjectsTable.COLUMN_NAME_CURRENT_FUNDING,
                ProjectsTable.COLUMN_NAME_CREATION_DATE,
                ProjectsTable.COLUMN_NAME_DESCRIPTION,
                ProjectsTable.COLUMN_NAME_BEGIN_DATE,
                ProjectsTable.COLUMN_NAME_END_DATE,
                ProjectsTable.COLUMN_NAME_LATITUDE,
                ProjectsTable.COLUMN_NAME_LONGITUDE,
                ProjectsTable.COLUMN_NAME_CONTACT_ADDRESS,
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
                    cursor.getString(0), // id
                    cursor.getString(1), // title
                    cursor.getString(2), // description
                    cursor.getInt(3) == 0 ? false : true, // validate
                    cursor.getString(4), // proposed by
                    cursor.getString(5), // requested funding
                    cursor.getString(6), // current funding
                    cursor.getString(7), // creation date
                    cursor.getString(8), // begin date
                    cursor.getString(9), // end date
                    cursor.getDouble(10), // latitude
                    cursor.getDouble(11), // longitude
                    cursor.getInt(12) // illustration
            );

            res.add(project);
        }

        cursor.close();

        return res;

    }

    public void put(Project project) {
        SQLiteDatabase db = m_helper.getWritableDatabase();

        ContentValues values = new ContentValues();
       /* values.put(ProjectsTable.COLUMN_NAME_ENTRY_ID, id);
        values.put(ProjectsTable.COLUMN_NAME_TITLE, title);
        values.put(ProjectsTable.COLUMN_NAME_CONTENT, content);*/

        db.insert(ProjectsTable.TABLE_NAME, null, values);
    }

    public void update(Project project) {
        /*SQLiteDatabase db = m_helper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);

// Which row to update, based on the ID
        String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(rowId) };

        int count = db.update(
                FeedReaderDbHelper.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);*/
    }

    public void delete(Project project) {
        /*// Define 'where' part of query.
        String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(rowId)};
// Issue SQL statement.
        db.delete(table_name, selection, selectionArgs);*/
    }

}
