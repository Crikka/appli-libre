package adullact.publicrowdfunding;

import com.paypal.android.sdk.payments.PaymentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class choisirMontantDialog extends AlertDialog.Builder {

	public choisirMontantDialog(final Context context) {
		super(context);

		LinearLayout linear = new LinearLayout(context);
		linear.setOrientation(1);

		final SeekBar seek = new SeekBar(context);
		final TextView text = new TextView(context);

		text.setText("Participation : 0 €");
		text.setPadding(10, 10, 10, 10);

		linear.addView(text);
		linear.addView(seek);

		seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				progress = progress / 10;
				progress = progress * 10;
				text.setText("Participation : " + progress + " €");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		});

		this.setView(linear);

		this.setTitle("Choisissez le montant");
		this.setIcon(R.drawable.ic_launcher);
		this.setPositiveButton("Je participe",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {

						Intent in = new Intent(context,
								ParticiperActivity.class);
						
						int somme = seek.getProgress() / 10;
						somme = somme * 10;
						
						in.putExtra("participation", somme);
						context.startActivity(in);

					}
				});
	}

}
