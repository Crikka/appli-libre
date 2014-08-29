package adullact.publicrowdfunding.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class CustomProgressBar extends ProgressBar {

	public CustomProgressBar(Context context) {
		super(context);

	}

	public CustomProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}