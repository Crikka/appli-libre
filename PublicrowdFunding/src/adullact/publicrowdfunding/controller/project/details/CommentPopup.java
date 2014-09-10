package adullact.publicrowdfunding.controller.project.details;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Commentary;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.server.event.CreateEvent;

/**
 * @author Ferrand and Nelaupe
 */
public class CommentPopup extends Fragment {

	private Fragment _this;

	private EditText comment_title;
	private EditText comment_message;

	private Button valider;

	private Animation shake;

	private RatingBar mark;

	private Project projet;

	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.popup_add_comment, container,
				false);

		_this = this;
		context = getActivity();

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			String idProject = bundle.getString("idProject");
			getProject(idProject);

		} else {
			back();
		}

		comment_title = (EditText) view.findViewById(R.id.titre_comment);
		comment_message = (EditText) view.findViewById(R.id.message_comment);

		mark = (RatingBar) view.findViewById(R.id.mark);

		valider = (Button) view.findViewById(R.id.valider);

		shake = AnimationUtils.loadAnimation(this.getActivity()
				.getBaseContext(), R.anim.shake);

		valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (comment_title.getText().length() < 1) {
					comment_title.startAnimation(shake);
					return;
				}

				if (comment_message.getText().length() < 1) {
					comment_message.startAnimation(shake);
					return;
				}

				try {
					float rating = mark.getRating();
					if (rating < 1) {
						throw new Exception();
					}
				} catch (Exception e) {
					mark.startAnimation(shake);
					return;
				}
				commenter();
			}

		});

		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					back();
					return false;
				}
				return false;
			}
		});

		return view;

	}

	public void back() {

		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.setCustomAnimations(R.anim.no_anim, R.anim.popup_exit);
		ft.remove(_this);


		// Multi Thread pour que l'animation s'éxécute
		FrameLayout filter = (FrameLayout) getActivity().getWindow().getDecorView()
				.findViewById(R.id.big_filter);			
		Animation fadeInAnimation = AnimationUtils.loadAnimation(_this.getActivity(), R.anim.fade_exit);
		filter.setAnimation(fadeInAnimation);
		ft.commit();
		filter.animate();
		
	}

	public void commenter() {
		String s_titre = comment_title.getText().toString();
		String s_message = comment_message.getText().toString();
		int rating = (int) mark.getRating();

		try {
			projet.postCommentary(s_titre, s_message, rating,
					new CreateEvent<Commentary>() {

						@Override
						public void errorResourceIdAlreadyUsed() {
							Toast.makeText(context,
									R.string.error,
									Toast.LENGTH_SHORT).show();
							back();
						}

						
						
						@Override
						public void onCreate(Commentary resource) {
							Toast.makeText(context, "Commentaire ajouté !",
									Toast.LENGTH_SHORT).show();
							back();
							ListCommentsFragment.reloadCommentFragment(getActivity(),projet);

						}

						@Override
						public void errorAuthenticationRequired() {
							Toast.makeText(context,
									R.string.error,
									Toast.LENGTH_SHORT).show();
							back();

						}

						@Override
						public void errorNetwork() {
							Toast.makeText(context,
									R.string.error,
									Toast.LENGTH_SHORT).show();
							back();

						}



						@Override
						public void errorServer() {Toast.makeText(context,
								R.string.error,
								Toast.LENGTH_SHORT).show();
						back();
							
						}

                        @Override
                        public void errorAdministratorRequired() {
                            Toast.makeText(context,
                                    R.string.error,
                                    Toast.LENGTH_SHORT).show();
                            back();
                        }
                    });
		} catch (NoAccountExistsInLocal e) {
			Toast.makeText(context, R.string.error,
					Toast.LENGTH_SHORT).show();
			back();
			e.printStackTrace();
		}

	}

	public void getProject(String idProject) {
		new Project().getCache(idProject)
				.toResource(new HoldToDo<Project>() {

					@Override
					public void hold(Project resource) {
						projet = resource;

					}

				});
	}	
	
}