package adullact.publicrowdfunding.controller.project.add;

import java.util.ArrayList;
import java.util.Calendar;

import adullact.publicrowdfunding.R;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.ressource.Account;
import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.ressource.User;
import adullact.publicrowdfunding.model.local.utilities.Utility;
import adullact.publicrowdfunding.model.server.event.CreateEvent;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout;
import com.touchmenotapps.carousel.simple.HorizontalCarouselLayout.CarouselInterface;
import com.touchmenotapps.carousel.simple.HorizontalCarouselStyle;

/**
 * @author Ferrand and Nelaupe
 */
public class BreadCrumbView extends LinearLayout {

	private TextView etape1;
	private TextView etape2;
	private TextView etape3;
	private TextView etape4;

	public BreadCrumbView(Context context) {
		super(context);
		init();
	}

	public BreadCrumbView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BreadCrumbView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.bread_crump, this);

		etape1 = (TextView) findViewById(R.id.etape1);
		etape2 = (TextView) findViewById(R.id.etape2);
		etape3 = (TextView) findViewById(R.id.etape3);
		etape4 = (TextView) findViewById(R.id.etape4);
	}

	public void setPosition(int position) {

		switch (position) {
		case 1:
			etape1.setTypeface(null, Typeface.BOLD);
			etape2.setTypeface(null, Typeface.NORMAL);
			etape3.setTypeface(null, Typeface.NORMAL);
			etape4.setTypeface(null, Typeface.NORMAL);
			break;
		case 2:
			etape1.setTypeface(null, Typeface.NORMAL);
			etape2.setTypeface(null, Typeface.BOLD);
			etape3.setTypeface(null, Typeface.NORMAL);
			etape4.setTypeface(null, Typeface.NORMAL);
			break;
		case 3:
			etape1.setTypeface(null, Typeface.NORMAL);
			etape2.setTypeface(null, Typeface.NORMAL);
			etape3.setTypeface(null, Typeface.BOLD);
			etape4.setTypeface(null, Typeface.NORMAL);
			break;
		case 4:
			etape1.setTypeface(null, Typeface.NORMAL);
			etape2.setTypeface(null, Typeface.NORMAL);
			etape3.setTypeface(null, Typeface.NORMAL);
			etape4.setTypeface(null, Typeface.BOLD);
			break;

		}

	}

}