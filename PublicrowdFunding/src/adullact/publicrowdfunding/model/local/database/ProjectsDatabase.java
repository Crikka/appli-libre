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
        SQLiteDatabase db = m_helper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
          /*      FeedEntry._ID,
                FeedEntry.COLUMN_NAME_TITLE,
                FeedEntry.COLUMN_NAME_UPDATED,
        ...*/
        };

      /*  Cursor c = db.query(
                FeedEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );*/

        /*cursor.moveToFirst();
        long itemId = cursor.getLong(
                cursor.getColumnIndexOrThrow(FeedEntry._ID)
        );*/

        return new ArrayList<Project>();

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
