package adullact.publicrowdfunding.controlleur.detailProjet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @author Nelaupe Lucas
 */
public class GraphiqueView extends View {

	private final Paint paint;
	private boolean isDrawing;
	private float positionX;
	private float positionY;
	private int[] avancement = new int[11];

	/**
	 * @param context
	 *            The context of the application
	 */
	public GraphiqueView(Context context) {
		super(context);
		paint = new Paint();
	}

	/**
	 * @param context
	 *            The context of the application
	 * @param attrs
	 *            The attributs
	 */
	public GraphiqueView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		isDrawing = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isDrawing = true;
			positionX = event.getX();
			positionY = event.getY();
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			isDrawing = true;
			positionX = event.getX();
			positionY = event.getY();
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			isDrawing = false;
			invalidate();
			break;
		}
		return true;

	}

	/**
	 * Draw
	 */
	protected void onDraw(Canvas canvas) {

		avancement[0] = 10;
		avancement[1] = 15;
		avancement[2] = 5;
		avancement[3] = 20;
		avancement[4] = 5;
		avancement[5] = 5;
		avancement[6] = 10;
		avancement[7] = 10;
		avancement[8] = 5;
		avancement[9] = 15;
		avancement[10] = 0;

		paint.setAntiAlias(true);

		

		int largeur = canvas.getWidth() - 10;
		int hauteur = largeur;
		int offset = 100; // décalement vertical

		Paint textPaint = new Paint();
		int xPos = (int) (canvas.getWidth() / 2);
		int yPos = offset / 2;
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setTextSize(40);
		textPaint.setColor(Color.rgb(128, 128, 128));
		canvas.drawText("FUNDING PROGRESS", xPos, yPos, textPaint);

		textPaint = new Paint();
		xPos = (int) (3 * canvas.getWidth() / 4);
		yPos = offset + 20;
		textPaint.setTextSize(20);
		textPaint.setColor(Color.rgb(109, 195, 41));
		canvas.drawText("100 %", xPos, yPos, textPaint);

		// Exemple de coubre
		int pourcentageAccomplie = 0;
		// / 100% = hauteur
		// 30 % =

		int nombreDeCarre = 10;
		for (int i = 0; i < nombreDeCarre + 1; i++) {

			if (i == 0) {
				paint.setColor(Color.rgb(109, 195, 41));
			}

			// Horizontal
			canvas.drawLine(0, i * (largeur / nombreDeCarre) + offset, largeur,
					i * (largeur / nombreDeCarre) + offset, paint);
			// Vertical
			paint.setColor(Color.rgb(210, 210, 210));
			canvas.drawLine(i * (largeur / nombreDeCarre), offset, i
					* (largeur / nombreDeCarre), largeur + offset, paint);

		}
		
		for (int i = 0; i < nombreDeCarre + 1; i++) {

			// int random = (int) (Math.random() * (20 + 1 - 1)) + 1;
			// System.out.println(random);
			int newPourcentage = pourcentageAccomplie + avancement[i];
			if (newPourcentage > 100) {
				newPourcentage = 100;
			}

			int yDepart = largeur - ((pourcentageAccomplie * hauteur) / 100)
					+ offset;
			int yArrive = largeur - ((newPourcentage * hauteur) / 100) + offset;

			int xDepart = i * (largeur / nombreDeCarre);
			int xArrive = (i + 1) * (largeur / nombreDeCarre);
			if (xArrive > largeur) {
				break;
			}

			paint.setColor(Color.rgb(131, 182, 255));
			Path path = new Path();
			path.setFillType(Path.FillType.EVEN_ODD);
			path.moveTo(xDepart, yDepart);
			path.lineTo(xArrive, yArrive);
			path.lineTo(xArrive, hauteur + offset);
			path.lineTo(xDepart, hauteur + offset);
			path.lineTo(xDepart, yDepart);
			path.close();

			canvas.drawPath(path, paint);

			// La ligne
			paint.setColor(Color.rgb(183, 0, 0));
			paint.setStrokeWidth(2);
			canvas.drawLine(xDepart, yDepart, xArrive, yArrive, paint);

			pourcentageAccomplie = newPourcentage;
		}



		if (isDrawing) {
			paint.setColor(Color.rgb(183, 0, 0));
			paint.setStrokeWidth(2);
			canvas.drawLine(positionX, offset, positionX, largeur + offset,
					paint);
			

		}

	}
}