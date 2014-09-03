package adullact.publicrowdfunding.model.local.utilities;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Ferrand and Nelaupe
 */
public class Share {

	public static LatLng position;
	public static boolean displayPosition;

	public static String formatString(String str) {
        String res;
        if(str.length() > 1) {
            res = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
        else {
            if(str.length() == 1) {
                res = str.toUpperCase();
            }
            else {
                res = "";
            }
        }

		return res;
	}

}
