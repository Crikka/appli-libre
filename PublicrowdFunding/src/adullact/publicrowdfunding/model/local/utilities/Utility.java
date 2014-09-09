package adullact.publicrowdfunding.model.local.utilities;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.ressource.Project;

/**
 * @author Ferrand and Nelaupe
 */
public class Utility {
	private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
			.appendYear(4, 4).appendLiteral('-').appendMonthOfYear(2)
			.appendLiteral('-').appendDayOfMonth(2)
			// "2014-05-06"
			.appendLiteral(' ').appendHourOfDay(2).appendLiteral(':')
			.appendMinuteOfHour(2).appendLiteral(':').appendSecondOfMinute(2) // "19:56:21"
			.toFormatter(); // "2014-05-06 19:56:21"

	public static DateTime stringToDateTime(String date) {
		return DateTime.parse(date, dateTimeFormatter);
	}

	public static String DateTimeToString(DateTime date) {
		return date.toString(dateTimeFormatter);
	}

    public static ArrayList<Project> restrictToUsefulProjects(ArrayList<Project> projects) {
        ArrayList<Project> res = new ArrayList<Project>();

        for(Project project : projects) {
            if(project.isValidate() && project.isActive()) {
                res.add(project);
            }
        }

        return res;
    }

    public static int [] drawables = {  R.drawable.ic_launcher, R.drawable.roi,  R.drawable.basketball,  R.drawable.plante, R.drawable.fete};
    
    public static int getDrawable(int numero) {
		return drawables[numero];
	}
    
    public static int getDrawableSize(){
    	return drawables.length;
    }
    
}
