package adullact.publicrowdfunding;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class sortByDialog extends AlertDialog {

	public sortByDialog(final Context context) {
		super(context);

		this.setContentView(R.layout.listeview);
		
		ListView list = (ListView) findViewById(R.id.liste);

		String names[] ={"A","B","C","D"};
		
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.id.liste,names);
		 list.setAdapter(adapter);
	
		this.setTitle("Sort by");
		this.setIcon(R.drawable.ic_launcher);
	}

}
