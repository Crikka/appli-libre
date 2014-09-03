package adullact.publicrowdfunding.model.local.database;

import android.provider.BaseColumns;

/**
 * @author Ferrand and Nelaupe
 */
public class ProjectsTable implements BaseColumns {
    private ProjectsTable() {}

    /* ---- Meta ---- */
    public static final String TABLE_NAME = "Project";
    /* -------------- */

    /* ---- Fields ---- */
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_ACTIVE = "active";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_VALIDATE = "validate";
    public static final String COLUMN_NAME_PROPOSED_BY = "proposedBy";
    public static final String COLUMN_NAME_REQUESTED_FUNDING = "requestedFunding";
    public static final String COLUMN_NAME_CURRENT_FUNDING = "currentFunding";
    public static final String COLUMN_NAME_CREATION_DATE = "creationDate";
    public static final String COLUMN_NAME_BEGIN_DATE = "beginDate";
    public static final String COLUMN_NAME_END_DATE = "endDate";
    public static final String COLUMN_NAME_LATITUDE = "latitude";
    public static final String COLUMN_NAME_LONGITUDE = "longitude";
    public static final String COLUMN_NAME_ILLUSTRATION = "illustration";
    public static final String COLUMN_NAME_CONTACT_ADDRESS = "contactAddress";
    public static final String COLUMN_NAME_CONTACT_PHONE = "contactPhone";
    public static final String COLUMN_NAME_CONTACT_WEBSITE = "contactWebsite";
    /* ---------------- */

    /* ---- Type ---- */
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String NUMERIC_TYPE = " NUMERIC";
    public static final String TEXT_TYPE = " TEXT";
        /* -------------- */

    /* -- Attributes/separator -- */
    public static final String COMMA = ",";
    public static final String NOT_NULL = " NOT NULL";
        /* ----------------------- */

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_ID + INTEGER_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_ACTIVE + INTEGER_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_TITLE + TEXT_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA +
                    COLUMN_NAME_VALIDATE + INTEGER_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_PROPOSED_BY + TEXT_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_REQUESTED_FUNDING + NUMERIC_TYPE + COMMA +
                    COLUMN_NAME_CURRENT_FUNDING + NUMERIC_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_CREATION_DATE + TEXT_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_BEGIN_DATE + TEXT_TYPE + COMMA +
                    COLUMN_NAME_END_DATE + TEXT_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_LATITUDE + NUMERIC_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_LONGITUDE + NUMERIC_TYPE + COMMA +
                    COLUMN_NAME_ILLUSTRATION + INTEGER_TYPE + NOT_NULL + COMMA +
                    COLUMN_NAME_CONTACT_ADDRESS + TEXT_TYPE  + COMMA +
                    COLUMN_NAME_CONTACT_PHONE + TEXT_TYPE + COMMA +
                    COLUMN_NAME_CONTACT_WEBSITE + TEXT_TYPE +
                    ")";

    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
