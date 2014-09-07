package adullact.publicrowdfunding.model.local.utilities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.ArrayList;

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

    public static int getDrawable(int numero) {
		switch (numero) {
		case 1:
			return R.drawable.roi;
		case 2:
			return R.drawable.basketball;
		case 3:
			return R.drawable.plante;
		case 4:
			return R.drawable.fete;

		default:
			return R.drawable.ic_launcher;
		}
	}
}
