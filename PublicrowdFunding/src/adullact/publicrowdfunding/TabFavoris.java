package adullact.publicrowdfunding;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TabFavoris extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		// On r�cup�re notre intent et la valeur nomm�e valeur
		String valeur = getIntent().getStringExtra("valeur");

		// On affiche cette cha�ne dans le textview
	//	TextView textView = (TextView) findViewById(R.id.monTextView);
	//	textView.setText(valeur);
	}
}