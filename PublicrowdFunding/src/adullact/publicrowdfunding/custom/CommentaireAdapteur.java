package adullact.publicrowdfunding.custom;

import java.util.ArrayList;
import java.util.List;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.shared.Commentaire;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentaireAdapteur extends ArrayAdapter<Commentaire> {

	private TextView commentaire;
	private List<Commentaire> commentaires = new ArrayList<Commentaire>();
	private LinearLayout layout;
	private boolean left;

	@Override
	public void add(Commentaire object) {
		commentaires.add(object);
		super.add(object);
	}

	public CommentaireAdapteur(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		left = true;
	}

	public int getCount() {
		return this.commentaires.size();
	}

	public Commentaire getItem(int index) {
		return this.commentaires.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_discuss, parent, false);
		}

		layout = (LinearLayout) row.findViewById(R.id.wrapper);

		Commentaire coment = getItem(position);

		commentaire = (TextView) row.findViewById(R.id.comment);

		commentaire.setText(coment.getMessage());


		commentaire.setBackgroundResource(left ? R.drawable.bubble_yellow : R.drawable.bubble_green);
		layout.setGravity(left ? Gravity.LEFT : Gravity.RIGHT);

		
		left = !left;
		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}