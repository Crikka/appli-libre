package adullact.publicrowdfunding;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.Html;
import android.widget.Toast;

public class infoAppDialog extends AlertDialog {

	protected infoAppDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setTitle("A propos");
		String versionName = getVersionName(context);
		this.setMessage(Html.fromHtml("Version : <b>" + versionName + "</b>"));
		this.setIcon(R.drawable.ic_launcher);
		// this.show();
	}

	public String getVersionName(Context context) {
		PackageManager packageManager = null;
		if (context != null) {
			packageManager = context.getPackageManager();
		}
		String name = null;
		if (context != null) {
			name = context.getPackageName();
		}

		try {
			PackageInfo packageInfo;
			packageInfo = null;
			if (packageManager != null) {
				packageInfo = packageManager.getPackageInfo(name, 0);
			}
			if (packageInfo != null) {
				return packageInfo.versionName;
			}
		} catch (NameNotFoundException e) {
			Toast.makeText(context, "Une erreur s'est produite",
					Toast.LENGTH_SHORT).show();
		}
		return null;

	}

}
