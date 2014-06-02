package adullact.publicrowdfunding;

import java.math.BigDecimal;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentActivity;

import adullact.publicrowdfunding.paypal.Acheter;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class ParticiperActivity extends Activity {

	private SeekBar participation;
	private Button participer;

	private static final String TAG = "paymentExample";

	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
	private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";
	private static final int REQUEST_CODE_PAYMENT = 1;
	private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

	private static PayPalConfiguration config = new PayPalConfiguration()
			.environment(CONFIG_ENVIRONMENT)
			.clientId(CONFIG_CLIENT_ID)
			// The following are only used in PayPalFuturePaymentActivity.
			.merchantName("Hipster Store")
			.merchantPrivacyPolicyUri(
					Uri.parse("https://www.example.com/privacy"))
			.merchantUserAgreementUri(
					Uri.parse("https://www.example.com/legal"));

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.participer);

		participation = (SeekBar) findViewById(R.id.participation);
		participer = (Button) findViewById(R.id.participer);

		participer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				/* Paypal */
				participation.getProgress();

				PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(
						"1.75"), "USD", "hipster jeans", null);
				Intent intent = new Intent(ParticiperActivity.this,
						PaymentActivity.class);

				intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

				startActivityForResult(intent, REQUEST_CODE_PAYMENT);

			}
		});
	}
}
