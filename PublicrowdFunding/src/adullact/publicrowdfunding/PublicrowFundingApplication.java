package adullact.publicrowdfunding;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ferrand on 19/07/2014.
 */
public class PublicrowFundingApplication extends Application {
    private static Context m_context;

    @Override
    public void onCreate(){
        super.onCreate();
        PublicrowFundingApplication.m_context = getApplicationContext();
    }

    public static Context context () {
        return PublicrowFundingApplication.m_context;
    }
}
