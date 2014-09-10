package adullact.publicrowdfunding.controller.adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Vector;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Commentary;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Share;

/**
 * 
 * @author warting {@link https://github.com/warting/AndroidChatBubbles}
 *
 */
public class CommentsAdaptor extends ArrayAdapter<Commentary> {

	private TextView commentaire;
	private TextView utilisateurName;
	private TextView utilisateurVille;
	private RatingBar rating;
	private TextView titre;
	private Vector<Commentary> commentaries = new Vector<Commentary>();
	private ImageView avatar;
	private CommentsAdaptor _this;

	public void setCommentaries(Vector<Commentary> object) {
		commentaries = object;
		_this = this;
	}

	public CommentsAdaptor(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.notifyDataSetChanged();
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
			row = inflater.inflate(R.layout.adaptor_comment, parent, false);
		}

		Commentary coment = getItem(position);

		commentaire = (TextView) row.findViewById(R.id.comment);
		commentaire.setText(Share.formatString(coment.getMessage()));

		titre = (TextView) row.findViewById(R.id.titre);
		titre.setText(Share.formatString(coment.getTitle()));

		utilisateurVille = (TextView) row.findViewById(R.id.utilisateur_ville);

		avatar = (ImageView) row.findViewById(R.id.avatar);

		rating = (RatingBar) row.findViewById(R.id.rating);
		rating.setRating((float) coment.getMark());
		rating.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		rating.setFocusable(false);
		utilisateurName = (TextView) row.findViewById(R.id.utilisateur_name);
		coment.getUser(new HoldToDo<User>() {

			@Override
			public void hold(User resource) {
				utilisateurName.setText(Share.formatString(resource.getPseudo()));
				utilisateurVille.setText(Share.formatString(resource.getCity()));
				if (resource.getGender().equals("0")) {
					avatar.setImageResource(R.drawable.male_user_icon);
				} else {
					avatar.setImageResource(R.drawable.female_user_icon);
				}
				_this.notifyDataSetChanged();
				
			}

		});

		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}