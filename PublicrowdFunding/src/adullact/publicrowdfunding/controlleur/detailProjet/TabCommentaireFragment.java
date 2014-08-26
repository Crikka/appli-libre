package adullact.publicrowdfunding.controlleur.detailProjet;

import java.util.ArrayList;
import java.util.Vector;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.controlleur.membre.ConnexionActivity;
import adullact.publicrowdfunding.custom.CommentaireAdapteur;
import adullact.publicrowdfunding.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldAllToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Commentary;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.SyncServerToLocal;
import adullact.publicrowdfunding.model.server.event.UpdateEvent;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class TabCommentaireFragment extends Fragment {

	private RatingBar m_notation;
	private ListView lv;

	private Vector<Commentary> commentaries;

	private LinearLayout layoutConnect;
	private LinearLayout layoutDisconnect;

	private Button m_connexion;

	protected CommentaireAdapteur adapter;

	private MainActivity _this;

	private SwipeRefreshLayout swipeView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_list_commentaire,
				container, false);

		layoutConnect = (LinearLayout) view.findViewById(R.id.connect);
		layoutDisconnect = (LinearLayout) view.findViewById(R.id.disconnect);

		isConnect();

		_this = (MainActivity) getActivity();

		m_connexion = (Button) view.findViewById(R.id.connexion);
		m_connexion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(_this.getApplicationContext(),
						ConnexionActivity.class);
				startActivity(in);
			}
		});

		swipeView = (SwipeRefreshLayout) view.findViewById(R.id.refresher);
		swipeView.setEnabled(false);
		swipeView.setColorScheme(R.color.blue, R.color.green, R.color.yellow,
				R.color.red);
		swipeView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						refresh();

					}

				});

		commentaries = new Vector<Commentary>();

		m_notation = (RatingBar) view
				.findViewById(R.id.rating_bar_projet_detail);

		lv = (ListView) view.findViewById(R.id.commentaires);

		adapter = new CommentaireAdapteur(
				getActivity().getApplicationContext(),
				R.layout.listitem_discuss);
		
		TextView empty = (TextView) view.findViewById(R.id.empty);
		lv.setEmptyView(empty);

		_this.projetCurrent.getCommentaries(new HoldAllToDo<Commentary>() {

			@Override
			public void holdAll(ArrayList<Commentary> resources) {
				commentaries = new Vector<Commentary>(resources);
			}
		});

		adapter.setCommentaries(commentaries);

		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Commentary com = commentaries.get(position);
				com.getUser(new WhatToDo<User>() {

					@Override
					public void hold(User resource) {
						Context c = _this.getBaseContext();
						Intent in = new Intent(
								c,
								adullact.publicrowdfunding.controlleur.profile.MainActivity.class);
						in.putExtra("myCount", false);
						in.putExtra("id", resource.getResourceId());
						startActivity(in);

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
						ajouterCommentaireAlert commentaireBuilder = new ajouterCommentaireAlert(
								getActivity(), rating, _this.projetCurrent);
						commentaireBuilder.show();

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
			layoutDisconnect.setVisibility(View.GONE);
		} catch (NoAccountExistsInLocal e1) {
			layoutConnect.setVisibility(View.GONE);
			layoutDisconnect.setVisibility(View.VISIBLE);
		}
	}

	public void refresh() {
		_this.refresh();
		adapter.notifyDataSetChanged();
	}

}
