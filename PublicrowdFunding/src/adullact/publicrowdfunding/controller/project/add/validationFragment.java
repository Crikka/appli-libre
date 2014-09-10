package adullact.publicrowdfunding.controller.project.add;

import org.joda.time.DateTime;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Ferrand and Nelaupe
 */
public class validationFragment extends Fragment {

	private Context context;

	private ImageView checkbox;
	private TextView message;

	private String m_titre;
	private String m_description;
	private String m_somme;
	private DateTime m_endDateTime;
	private int m_illustration;
	private LatLng m_location;
	private String m_email;
	private String m_phone;
	private String m_website;

	private String m_userId;

	private boolean m_isAdmin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_add_validation,
				container, false);

		BreadCrumbView breadCrumbView = (BreadCrumbView) view
				.findViewById(R.id.breadcrumb);
		breadCrumbView.setPosition(4);

		message = (TextView) view.findViewById(R.id.message);
		checkbox = (ImageView) view.findViewById(R.id.checkbox);

		Bundle args = this.getArguments();

		m_titre = args.getString("title");
		m_description = args.getString("description");
		m_somme = args.getString("somme");
		String endDate = args.getString("endDate");

		m_endDateTime = Utility.stringToDateTime(endDate);

		m_email = null;
		try {
			m_email = args.getString("email");
		} catch (NullPointerException e) {
			System.out.println("Pas d'email ajouté !");
		}

		m_phone = null;
		try {
			m_phone = args.getString("phone");
		} catch (NullPointerException e) {
			System.out.println("Pas de téléphone ajouté !");
		}

		m_website = null;
		try {
			m_website = args.getString("website");
		} catch (NullPointerException e) {
			System.out.println("Pas de site internet ajouté !");
		}

		m_illustration = args.getInt("illustration");

		Double longitude = args.getDouble("location_longitude");
		Double latitude = args.getDouble("location_latitude");
		m_location = new LatLng(latitude, longitude);

		try {

			Account compte = Account.getOwn();
			m_isAdmin = compte.isAdmin();
			compte.getUser(new HoldToDo<User>() {

				@Override
				public void hold(User resource) {

					m_userId = resource.getResourceId();

					push();

				}

			});
		} catch (NoAccountExistsInLocal e) {
			e.printStackTrace();
		}

		return view;
	}

	public void push() {

		new Project(m_titre, m_description, m_userId, m_somme,
				m_endDateTime, m_location, m_illustration, m_email, m_website,
				m_phone, m_isAdmin).serverCreate(new CreateEvent<Project>() {
			@Override
			public void errorResourceIdAlreadyUsed() {

				Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onCreate(Project resource) {

				Drawable checked = context.getResources().getDrawable(
						R.id.checkbox);
				checkbox.setImageDrawable(checked);

				message.setText("Projet soumis, en attente de validation");

			}

			@Override
			public void errorAuthenticationRequired() {
				Toast.makeText(context, "Vous devez vous authentifier",
						Toast.LENGTH_SHORT).show();

			}

			@Override
			public void errorNetwork() {
				Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT)
						.show();

			}

			@Override
			public void errorServer() {
				Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT)
						.show();

			}

            @Override
            public void errorAdministratorRequired() {
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT)
                        .show();
            }
        });
	}

}