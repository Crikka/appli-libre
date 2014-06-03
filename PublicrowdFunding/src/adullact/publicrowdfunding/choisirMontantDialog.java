package adullact.publicrowdfunding;

import com.paypal.android.sdk.payments.PaymentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.SeekBar;

public class choisirMontantDialog extends AlertDialog.Builder {

	public choisirMontantDialog(final Context context) {
		super(context);

		final SeekBar seek = new SeekBar(context);

		seek.setMax(100);
		this.setView(seek);
		this.setTitle("Choisissez le montant");
		this.setIcon(R.drawable.ic_launcher);
		this.setPositiveButton("Je participe",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {

						Intent in = new Intent(context,
								ParticiperActivity.class);
						context.startActivity(in);

					}
				});
	}

}
