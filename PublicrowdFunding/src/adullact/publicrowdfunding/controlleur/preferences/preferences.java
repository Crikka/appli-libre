package adullact.publicrowdfunding.controlleur.preferences;

import adullact.publicrowdfunding.R;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) 
public class preferences extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.layout.preferences);
        
        SwitchPreference genre = (SwitchPreference) findPreference("genre");
        genre.setSwitchTextOn("Femme");
        genre.setSwitchTextOff("Homme");
        
    }
}