package adullact.publicrowdfunding.controlleur.detailProjet;

import java.math.BigDecimal;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class ParticiperPaypalActivity extends Activity {

	private int participation;

	private static final String TAG = "paymentExample";

	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
	private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";
	private static final int REQUEST_CODE_PAYMENT = 1;
	private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

	private static PayPalConfiguration config = new PayPalConfiguration()
			.environment(CONFIG_ENVIRONMENT).clientId(CONFIG_CLIENT_ID)
			.merchantName("PublicrowdFunding Store")
			.merchantPrivacyPolicyUri(Uri.parse(""))
			.merchantUserAgreementUri(Uri.parse(""));

	public void configurationPaypal() {
		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		configurationPaypal();

		participation = getIntent().getExtras().getInt("participation", 0);
		if (participation == 0) {
			Toast.makeText(getApplicationContext(),
					"Une Erreur s'est produite", Toast.LENGTH_SHORT).show();
			finish();
		}

		/* Paypal */

		PayPalPayment thingToBuy = getSommeParticipations(PayPalPayment.PAYMENT_INTENT_SALE);
		Intent intent = new Intent(ParticiperPaypalActivity.this,
				PaymentActivity.class);
		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
		startActivityForResult(intent, REQUEST_CODE_PAYMENT);

	}

	private PayPalPayment getSommeParticipations(String paymentIntent) {
		return new PayPalPayment(new BigDecimal(participation), "EUR",
				"Participer au projet", paymentIntent);
	}

	public void onFuturePaymentPressed(View pressed) {
		Intent intent = new Intent(ParticiperPaypalActivity.this,
				PayPalFuturePaymentActivity.class);

		startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data
						.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {
						Log.i(TAG, confirm.toJSONObject().toString(4));
						Log.i(TAG, confirm.getPayment().toJSONObject()
								.toString(4));
						/**
						 * TODO: send 'confirm' (and possibly
						 * confirm.getPayment() to your server for verification
						 * or consent completion. See
						 * https://developer.paypal.com
						 * /webapps/developer/docs/integration
						 * /mobile/verify-mobile-payment/ for more details.
						 * 
						 * For sample mobile backend interactions, see
						 * https://github
						 * .com/paypal/rest-api-sdk-python/tree/master
						 * /samples/mobile_backend
						 */
						Toast.makeText(
								getApplicationContext(),
								"PaymentConfirmation info received from PayPal",
								Toast.LENGTH_LONG).show();

					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(),
								"Une erreur s'est produite", Toast.LENGTH_SHORT)
								.show();
						finish();
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),
						"Vous avez annulé le payement", Toast.LENGTH_SHORT)
						.show();
				finish();
			} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
				Toast.makeText(getApplicationContext(),
						"Une erreur s'est produite", Toast.LENGTH_SHORT).show();
				finish();
			}

		} else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PayPalAuthorization auth = data
						.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
				if (auth != null) {
					try {
						Log.i("FuturePaymentExample", auth.toJSONObject()
								.toString(4));

						String authorization_code = auth.getAuthorizationCode();
						Log.i("FuturePaymentExample", authorization_code);

						sendAuthorizationToServer(auth);
						Toast.makeText(getApplicationContext(),
								"Future Payment code received from PayPal",
								Toast.LENGTH_LONG).show();

					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(),
								"Une erreur s'est produite", Toast.LENGTH_SHORT)
								.show();
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),
						"Vous avez annulé le payement", Toast.LENGTH_SHORT)
						.show();
				finish();
			} else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
				Toast.makeText(getApplicationContext(),
						"Une erreur s'est produite", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	private void sendAuthorizationToServer(PayPalAuthorization authorization) {

		/**
		 * TODO: Send the authorization response to your server, where it can
		 * exchange the authorization code for OAuth access and refresh tokens.
		 * 
		 * Your server must then store these tokens, so that your server code
		 * can execute payments for this user in the future.
		 * 
		 * A more complete example that includes the required app-server to
		 * PayPal-server integration is available from
		 * https://github.com/paypal/
		 * rest-api-sdk-python/tree/master/samples/mobile_backend
		 */

	}

	public void onFuturePaymentPurchasePressed(View pressed) {
		// Get the Application Correlation ID from the SDK
		String correlationId = PayPalConfiguration
				.getApplicationCorrelationId(this);

		Log.i("FuturePaymentExample", "Application Correlation ID: "
				+ correlationId);

		Toast.makeText(getApplicationContext(),
				"App Correlation ID received from SDK", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onDestroy() {
		// Stop service when done
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}
}
