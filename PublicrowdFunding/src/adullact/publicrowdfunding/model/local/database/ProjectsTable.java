package adullact.publicrowdfunding.model.local.database;

import android.provider.BaseColumns;

/**
 * Created by Ferrand on 20/07/2014.
 */
public class ProjectsTable implements BaseColumns {
    private ProjectsTable() {}

    /* ---- Meta ---- */
    public static final String TABLE_NAME = "Project";
    /* -------------- */

    /* ---- Fields ---- */
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
        /* ---------------- */

    /* ---- Type ---- */
    public static final String CHAR50_TYPE = " CHAR(50)";
    public static final String CHAR30_TYPE = " CHAR(30)";
    public static final String DECIMAL_TYPE = " DECIMAL";
    public static final String TEXT_TYPE = " TEXT";
    public static final String DATE_TYPE = " DATE";
    public static final String BOOLEAN_TYPE = " BOOLEAN";
        /* -------------- */

    /* -- Attributes/separator -- */
    public static final String COMMA = ",";
    public static final String NOT_NULL = " NOT NULL";
        /* ----------------------- */

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_ID + CHAR50_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_TITLE + CHAR50_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_DESCRIPTION + TEXT_TYPE +
                    ")";

    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
