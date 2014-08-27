package adullact.publicrowdfunding.model.local.utilities;

import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;

public class Share {

	public static LatLng position;

	public static String formatString(String str){
		return str.substring(0,1).toUpperCase(Locale.getDefault()) + str.substring(1).toLowerCase(Locale.getDefault());
		
	}

}
