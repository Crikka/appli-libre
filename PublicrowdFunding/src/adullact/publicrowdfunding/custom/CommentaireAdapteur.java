package adullact.publicrowdfunding.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.local.ressource.Commentary;
import adullact.publicrowdfunding.model.local.ressource.User;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 
 * @author warting {@link https://github.com/warting/AndroidChatBubbles}
 *
 */
public class CommentaireAdapteur extends ArrayAdapter<Commentary> {

	private TextView commentaire;
	private TextView utilisateurName;
	private TextView utilisateurVille;
	private RatingBar rating;
	private TextView titre;
	private Vector<Commentary> commentaries = new Vector<Commentary>();
	private LinearLayout layout;

	public void setCommentaries(Vector<Commentary> object) {
		commentaries = object;
	}

	public CommentaireAdapteur(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public int getCount() {
		return this.commentaries.size();
	}

	public Commentary getItem(int index) {
		return this.commentaries.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		LayoutInflater inflater = null;
		if (row == null) {
			inflater = (LayoutInflater) this.getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_discuss, parent, false);
		}

		layout = (LinearLayout) row.findViewById(R.id.wrapper);

		Commentary coment = getItem(position);

		commentaire = (TextView) row.findViewById(R.id.comment);
		commentaire.setText(coment.getMessage());
		
		titre = (TextView) row.findViewById(R.id.titre);
		titre.setText(coment.getTitle());

		rating = (RatingBar) row.findViewById(R.id.rating);
		rating.setRating((float)coment.getMark());
		rating.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
	    });
		rating.setFocusable(false);
		utilisateurName = (TextView) row.findViewById(R.id.utilisateur_name);
		coment.getUser(new WhatToDo<User>() {

			@Override
			public void hold(User resource) {
				utilisateurName.setText(resource.getPseudo());
			}

			@Override
			public void eventually() {
				// TODO Auto-generated method stub
			}

		});
		
		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}