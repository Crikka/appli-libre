package adullact.publicrowdfunding;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import adullact.publicrowdfunding.model.local.ressource.Account;

/**
 * Created by Ferrand on 19/07/2014.
 */
public class PublicrowdFundingApplication extends Application {
    private static Context m_context;
    private static SharedPreferences m_sharedPreferences;

    @Override
    public void onCreate(){
        super.onCreate();
        PublicrowdFundingApplication.m_context = getApplicationContext();
        PublicrowdFundingApplication.m_sharedPreferences = getSharedPreferences("prefs", 0);
        System.setProperty("http.keepAlive", "false");

        Account.autoConnect();
    }

    public static Context context () {
        return PublicrowdFundingApplication.m_context;
    }
    public static SharedPreferences sharedPreferences () {
        return PublicrowdFundingApplication.m_sharedPreferences;
    }

}
