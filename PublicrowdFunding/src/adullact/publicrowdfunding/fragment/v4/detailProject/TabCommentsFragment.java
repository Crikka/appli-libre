package adullact.publicrowdfunding.fragment.v4.detailProject;

import java.util.ArrayList;
import java.util.Vector;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.custom.CommentaireAdapteur;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Commentary;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class TabCommentsFragment extends Fragment {

	private RatingBar m_notation;
	private ListView lv;

	private Vector<Commentary> commentaries;

	private FrameLayout layoutConnect;

	protected CommentaireAdapteur adapter;

	private SwipeRefreshLayout swipeView;

	private Project projetCurrent;

	private FragmentManager fm;

	private LinearLayout loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list_comments,
				container, false);

		fm = this.getActivity().getSupportFragmentManager();

		layoutConnect = (FrameLayout) view.findViewById(R.id.connect);

		loading = (LinearLayout) view.findViewById(R.id.loading);

		isConnect();

		commentaries = new Vector<Commentary>();
		
		lv = (ListView) view.findViewById(R.id.commentaires);
		adapter = new CommentaireAdapteur(
				getActivity().getApplicationContext(), R.layout.adaptor_comment);
		
		adapter.setCommentaries(commentaries);

		lv.setAdapter(adapter);

		m_notation = (RatingBar) view
				.findViewById(R.id.rating_bar_projet_detail);

		TextView empty = (TextView) view.findViewById(R.id.empty);
		lv.setEmptyView(empty);

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			String idProject = bundle.getString("idProject");
			Cache<Project> projet = new Project().getCache(idProject)
					.forceRetrieve();
			projet.toResource(new HoldToDo<Project>() {
				@Override
				public void hold(Project project) {
					projetCurrent = project;
					displayInfo();
				}
			});
		}

		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.refresher);
		swipeView.setEnabled(false);
		swipeView.setColorScheme(R.color.blue, R.color.green, R.color.yellow,
				R.color.red);
		swipeView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						// refresh();

					}

				});

		lv.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int i) {

			}

			@Override
			public void onScroll(AbsListView absListView, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0)
					swipeView.setEnabled(true);
				else
					swipeView.setEnabled(false);
			}
		});

		return view;

	}

	public void isConnect() {
		try {
			Account.getOwn();
			layoutConnect.setVisibility(View.VISIBLE);
		} catch (NoAccountExistsInLocal e1) {
			layoutConnect.setVisibility(View.GONE);
		}
	}

	/*
	 * public void refresh() { _this.refresh(); adapter.notifyDataSetChanged();
	 * }
	 */

	public void displayInfo() {
		projetCurrent.getCommentaries(new HoldAllToDo<Commentary>() {

			@Override
			public void holdAll(ArrayList<Commentary> resources) {
				commentaries = new Vector<Commentary>(resources);
				loading.setVisibility(View.GONE);
				adapter.setCommentaries(commentaries);
				adapter.notifyDataSetChanged();
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Commentary com = commentaries.get(position);
				com.getUser(new WhatToDo<User>() {

					@Override
					public void hold(User resource) {

						FragmentTransaction ft = fm.beginTransaction();

						// ft.setCustomAnimations(R.anim.enter, R.anim.exit);
						Fragment fragment = new adullact.publicrowdfunding.fragment.v4.profile.PagerFragment();
						Bundle bundle = new Bundle();
						bundle.putString("idUser", resource.getResourceId());
						fragment.setArguments(bundle);
						fragment.setHasOptionsMenu(true);
						ft.replace(R.id.content_frame, fragment);
						ft.addToBackStack(null);
						ft.commit();

					}

					@Override
					public void eventually() {
						// TODO Auto-generated method stub

					}
				});

			}
		});

		m_notation
				.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {

						System.out.println(rating);
						addCommentAlert commentaireBuilder = new addCommentAlert(
								getActivity(), rating, projetCurrent);
						commentaireBuilder.show();

					}
				});
	}

}
