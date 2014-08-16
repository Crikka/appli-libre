package adullact.publicrowdfunding.model.local.utilities;

import com.google.android.gms.maps.model.LatLng;

public final class Calcul {

	public float distance (LatLng a, LatLng b) 
	{
	    double earthRadius = 3958.75;
	    double latDiff = Math.toRadians(b.latitude-a.latitude);
	    double lngDiff = Math.toRadians(b.longitude-a.longitude);
	    double interm = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
	    Math.cos(Math.toRadians(a.latitude)) * Math.cos(Math.toRadians(b.latitude)) *
	    Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
	    double c = 2 * Math.atan2(Math.sqrt(interm), Math.sqrt(1-interm));
	    double distance = earthRadius * c;

	    int meterConversion = 1609;

	    return new Float(distance * meterConversion).floatValue();
	}
}
