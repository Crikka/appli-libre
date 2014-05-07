package adullact.publicrowdfunding;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	private TabHost tabHost;
	private TabSpec tabSpec;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tabHost = getTabHost();
        
        Intent intent = new Intent(this, TabProjets.class);
        intent.putExtra("valeur", "Liste des projets");
        tabSpec = tabHost.newTabSpec("liste projets").setIndicator("Projets").setContent(intent);
        tabHost.addTab(tabSpec);
        
        intent = new Intent(this, TabContributions.class);
        intent.putExtra("valeur", "Fianancement auquels je participe");
        tabSpec = tabHost.newTabSpec("projets financés").setIndicator("Mes contributions").setContent(intent);
        tabHost.addTab(tabSpec);
        
        intent = new Intent(this, TabFavoris.class);
        intent.putExtra("valeur", "Ici les favoris");
        tabSpec = tabHost.newTabSpec("mes favoris").setIndicator("Favoris").setContent(intent);
        tabHost.addTab(tabSpec);
        
        Communicator communicator = new Communicator();
        System.out.println(communicator.authentificateUser("Nelaupe", "Lucas"));
    }
}
