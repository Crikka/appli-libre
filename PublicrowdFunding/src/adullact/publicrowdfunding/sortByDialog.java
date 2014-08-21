package adullact.publicrowdfunding;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controlleur.detailProjet.ParticiperPaypalActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class sortByDialog extends AlertDialog {

	public sortByDialog(final Context context) {
		super(context);

		this.setContentView(R.layout.simple_listeview);
		
		ListView list = (ListView) findViewById(R.id.liste);

		String names[] ={"A","B","C","D"};
		
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.id.liste,names);
		 list.setAdapter(adapter);
	
		this.setTitle("Sort by");
		this.setIcon(R.drawable.ic_launcher);
	}

}
