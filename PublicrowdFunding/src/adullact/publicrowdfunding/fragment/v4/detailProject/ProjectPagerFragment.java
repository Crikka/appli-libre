package adullact.publicrowdfunding.fragment.v4.detailProject;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Toast;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Bookmark;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.CanI;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import adullact.publicrowdfunding.model.server.event.DeleteEvent;

public class ProjectPagerFragment extends Fragment {

	FragmentTransaction fragMentTra;
	FragmentManager fm;
	private String idProject;

	private MenuItem star;

	private boolean m_Is_favorite;

	private Project projectCurrent;

	private adullact.publicrowdfunding.MainActivity context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.pager_tab, container, false);

		Bundle bundle = this.getArguments();
		idProject = bundle.getString("idProject");

		context = (adullact.publicrowdfunding.MainActivity) getActivity();

		fm = context.getSupportFragmentManager();
		fm.beginTransaction().disallowAddToBackStack().commit();

		PagerAdaptor adaptor = new PagerAdaptor(fm, idProject);

		ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
		viewPager.setAdapter(adaptor);
		viewPager.setCurrentItem(1);

		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					fm.popBackStack();

					return true;
				}
				return false;
			}
		});
		return view;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		try {
			menu.clear(); // Permettra d'éviter les bugs de superpositions
		} catch (Exception e) {
			e.printStackTrace();
		}
		inflater.inflate(R.menu.detail_projet, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		star = menu.findItem(R.id.add_favorite);
		initBookmark();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.add_favorite:
			setBookmark();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;

	}

	public void setBookmark() {

		try {
			Account account = Account.getOwn();
			account.getUser(new HoldToDo<User>() {

				@Override
				public void hold(User resource) {

					if (m_Is_favorite) {
						resource.removeBookmark(projectCurrent,
								new DeleteEvent<Bookmark>() {

									@Override
									public void errorResourceIdDoesNotExist() {
										Toast.makeText(context,
												"Une erreur s'est produite",
												Toast.LENGTH_SHORT).show();

									}

									@Override
									public void onDelete(Bookmark resource) {
										m_Is_favorite = false;
										Toast.makeText(context,
												"Projet retiré de vos favoris",
												Toast.LENGTH_SHORT).show();
										changeColorStar();
									}

									@Override
									public void errorAdministratorRequired() {
										Toast.makeText(context,
												"Une erreur s'est produite",
												Toast.LENGTH_SHORT).show();

									}

									@Override
									public void errorAuthenticationRequired() {
										Toast.makeText(context,
												"Une erreur s'est produite",
												Toast.LENGTH_SHORT).show();

									}

									@Override
									public void errorNetwork() {
										Toast.makeText(context,
												"Une erreur s'est produite",
												Toast.LENGTH_SHORT).show();

									}

								});
					} else {

						resource.addBookmark(projectCurrent,
								new CreateEvent<Bookmark>() {

									@Override
									public void errorResourceIdAlreadyUsed() {
										Toast.makeText(context,
												"Une erreur s'est produite",
												Toast.LENGTH_SHORT).show();

									}

									@Override
									public void onCreate(Bookmark resource) {
										m_Is_favorite = true;
										Toast.makeText(context,
												"Projet ajouté à vos favoris",
												Toast.LENGTH_SHORT).show();
										changeColorStar();
									}

									@Override
									public void errorAuthenticationRequired() {
										Toast.makeText(context,
												"Une erreur s'est produite",
												Toast.LENGTH_SHORT).show();

									}

									@Override
									public void errorNetwork() {
										Toast.makeText(context,
												"Une erreur s'est produite",
												Toast.LENGTH_SHORT).show();

									}

								});
					}

				}

			});
		} catch (NoAccountExistsInLocal e) {
			Toast.makeText(context,
					"Il faut un compte pour avoir des favoris !",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	public void initBookmark() {

		new Project().getCache(idProject)
				.toResource(new HoldToDo<Project>() {

					@Override
					public void hold(Project resource) {
						projectCurrent = resource;
						function();

					}

				});

	}

	public void function() {
		try {
			Account.getOwn();
			new CanI() {
				@Override
				protected void yes() {
					m_Is_favorite = false;
				}

				@Override
				protected void no() {
					m_Is_favorite = true;
				}

			}.bookmark(projectCurrent);

		} catch (NoAccountExistsInLocal e) {
			m_Is_favorite = false;
		}
		changeColorStar();
	}

	public void changeColorStar() {
		PorterDuffColorFilter filter;
		if (m_Is_favorite) {

			filter = new PorterDuffColorFilter(Color.YELLOW,
					PorterDuff.Mode.SRC_ATOP);

		} else {

			filter = new PorterDuffColorFilter(Color.TRANSPARENT,
					PorterDuff.Mode.SRC_ATOP);
		}
		star.getIcon().setColorFilter(filter);
	}

}