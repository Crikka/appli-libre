package adullact.publicrowdfunding.custom;

import java.util.ArrayList;
import java.util.List;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.shared.Commentary;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author warting 
 * {@link https://github.com/warting/AndroidChatBubbles}
 *
 */
public class CommentaireAdapteur extends ArrayAdapter<Commentary> {

	private TextView commentaire;
	private List<Commentary> commentaries = new ArrayList<Commentary>();
	private LinearLayout layout;

	@Override
	public void add(Commentary object) {
		commentaries.add(object);
		super.add(object);
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
			inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listitem_discuss, parent, false);
		}
		

		layout = (LinearLayout) row.findViewById(R.id.wrapper);

		Commentary coment = getItem(position);

		commentaire = (TextView) row.findViewById(R.id.comment);

		commentaire.setText(coment.message());


		commentaire.setBackgroundResource(position % 2 == 0 ? R.drawable.bubble_yellow : R.drawable.bubble_green);
		layout.setGravity(position % 2 == 0 ? Gravity.LEFT : Gravity.RIGHT);


		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}