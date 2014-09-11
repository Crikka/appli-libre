package adullact.publicrowdfunding.controller.project.add;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import org.joda.time.DateTime;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Utility;

/**
 * @author Ferrand and Nelaupe
 */
public class addProjectFragment extends Fragment {

	private EditText m_titre;
	private EditText m_Description;
	private EditText m_edit_text_somme;
	private EditText m_email;
	private EditText m_phone;
	private EditText m_website;

	private TextView m_user_pseudo;
	private TextView m_user_ville;

	private DatePicker m_dateFin;

	private Button m_valider;

	private Context context;

	private ImageView avatar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_add_project, container,
				false);

		context = this.getActivity().getApplicationContext();

		BreadCrumbView breadCrumb = (BreadCrumbView) view
				.findViewById(R.id.breadcrumb);
		breadCrumb.setPosition(1);

		m_titre = (EditText) view.findViewById(R.id.titre);
		m_Description = (EditText) view.findViewById(R.id.description);
		m_dateFin = (DatePicker) view.findViewById(R.id.date_de_fin);
		m_valider = (Button) view.findViewById(R.id.button_valider);
		m_edit_text_somme = (EditText) view.findViewById(R.id.edit_text_somme);
		m_user_pseudo = (TextView) view
				.findViewById(R.id.utilisateur_soumission);
		m_user_ville = (TextView) view.findViewById(R.id.ville);

		m_email = (EditText) view.findViewById(R.id.mail);
		m_website = (EditText) view.findViewById(R.id.website);
		m_phone = (EditText) view.findViewById(R.id.phone);

		m_edit_text_somme = (EditText) view.findViewById(R.id.edit_text_somme);

		avatar = (ImageView) view.findViewById(R.id.avatar);

		m_valider.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				addProjectRequest();

			}
		});

		loadContent();

		return view;
	}

	public void addProjectRequest() {

		String titre = null;
		if (m_titre.length() == 0) {
			Toast.makeText(context, "Merci de mettre un titre",
					Toast.LENGTH_SHORT).show();
			return;
		}
		titre = m_titre.getText().toString();

		String description = null;
		if (m_Description.length() == 0) {
			Toast.makeText(context, "Merci de mettre une description",
					Toast.LENGTH_SHORT).show();
			return;
		}
		description = m_Description.getText().toString();

		String somme = null;
		if (m_edit_text_somme.length() == 0) {
			Toast.makeText(context, "Merci de mettre la somme désiré",
					Toast.LENGTH_SHORT).show();
			return;
		}
		somme = m_edit_text_somme.getText().toString();

		try {
			int s = Integer.parseInt(somme);
			if (s < 1) {
				Toast.makeText(context, "Le montant est invalide",
						Toast.LENGTH_SHORT).show();
				return;
			}

		} catch (Exception e) {
			Toast.makeText(context, "Le montant est invalide",
					Toast.LENGTH_SHORT).show();
			return;
		}

		int day = m_dateFin.getDayOfMonth();
		int month = m_dateFin.getMonth() + 1;
		int year = m_dateFin.getYear();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		String Stryear = "" + year;
		String StrMonth = null;
		if (month < 10) {
			StrMonth = "0" + month;
		} else {
			StrMonth = "" + month;
		}

		String StrDay = "" + day;
		String endDate = Stryear + "-" + StrMonth + "-" + StrDay + " 00:00:00";

		try {
			DateTime endDateTime = Utility.stringToDateTime(endDate);
			DateTime now = DateTime.now();
			if (endDateTime.isBefore(now)) {
				throw new Exception();
			}
		} catch (Exception e) {
			Toast.makeText(context, "Date de fin invalide", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction().disallowAddToBackStack();
		Fragment fragment = new addImageFragment();
		Bundle args = new Bundle();

		args.putString("title", titre);
		args.putString("description", description);
		args.putString("somme", somme);
		args.putString("endDate", endDate);

		if (m_email.length() > 0) {
			String email = m_email.getText().toString();
			args.putString("email", email);
		}

		if (m_phone.length() > 0) {
			String phone = m_phone.getText().toString();
			args.putString("phone", phone);
		}

		if (m_website.length() > 0) {
			String website = m_website.getText().toString();
			args.putString("website", website);
		}

		fragment.setArguments(args);
		fragment.setHasOptionsMenu(true);
		ft.replace(R.id.content_frame, fragment);
		ft.commit();

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void supprimerCalendarView() {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 11) {
			m_dateFin.setCalendarViewShown(false);
		}

	}

	public void loadContent() {

		try {
			Account compte = Account.getOwn();
			compte.getUser(new HoldToDo<User>() {

				@Override
				public void hold(User resource) {

					m_user_pseudo.setText(resource.getPseudo());
					m_user_ville.setText(resource.getCity());
					if (resource.getGender().equals("0")) {
						avatar.setImageResource(R.drawable.male_user_icon);
					} else {
						avatar.setImageResource(R.drawable.female_user_icon);
					}

				}

			});
		} catch (NoAccountExistsInLocal e) {
			e.printStackTrace();
		}

		supprimerCalendarView();

	}

}