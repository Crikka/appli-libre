package adullact.publicrowdfunding.controlleur.detailProjet;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import adullact.publicrowdfunding.model.local.ressource.Project;
import adullact.publicrowdfunding.model.local.utilities.FundingInterval;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Nelaupe Lucas
 */
public class GraphiqueView extends View {

	private final Paint paint;
	private boolean isDrawing;
	private float positionX;
	private float positionY;
	private Project projet;

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

	public void setProject(Project projet) {
		this.projet = projet;
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

		int largeur = canvas.getWidth() - 10;
		int hauteur = largeur;
		int offset = 100; // décalement vertical

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

		ArrayList<FundingInterval> graphData = new ArrayList<FundingInterval>();
		if (projet == null) {
			return;
		}
		graphData = projet.getFundingIntervals();
		paint.setAntiAlias(true);

		Paint textPaint = new Paint();

		int xPos = (int) (canvas.getWidth() / 2);
		int yPos = offset / 2;
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setTextSize(40);
		textPaint.setColor(Color.rgb(128, 128, 128));
		canvas.drawText("Progression du financement", xPos, yPos, textPaint);

		// Ajout texte 100 %
		textPaint = new Paint();
		xPos = (int) (6 * canvas.getWidth() / 10);
		yPos = offset - 20;
		textPaint.setTextSize(20);
		textPaint.setColor(Color.rgb(109, 195, 41));
		canvas.drawText("100 %", xPos, yPos, textPaint);

		// Ajout date de début
		textPaint = new Paint();
		xPos = 0;
		yPos = offset - 20;
		textPaint.setTextSize(20);
		textPaint.setColor(Color.rgb(160, 160, 160));
		Interval in = projet.getFundingInterval();

		DateTime dateStart = in.getStart();
		String month = "";
		if (dateStart.getMonthOfYear() < 10) {
			month = "0" + dateStart.getMonthOfYear();
		} else {
			month = "" + dateStart.getMonthOfYear();
		}

		String monthEnd = "";
		DateTime dateEnd = in.getEnd();
		if (dateEnd.getMonthOfYear() < 10) {
			monthEnd = "0" + dateEnd.getMonthOfYear();
		} else {
			monthEnd = "" + dateEnd.getMonthOfYear();
		}

		canvas.drawText(dateStart.getDayOfMonth() + "/" + month + "/"
				+ dateStart.getYear(), xPos, yPos, textPaint);

		// Ajout date de fin
		textPaint = new Paint();
		xPos = (int) (canvas.getWidth() - 120);
		yPos = offset - 20;
		textPaint.setTextSize(20);
		textPaint.setColor(Color.rgb(160, 160, 160));
		canvas.drawText(dateEnd.getDayOfMonth() + "/" + monthEnd + "/"
				+ dateEnd.getYear(), xPos, yPos, textPaint);

		// Exemple de coubre
		long pourcentageAccomplie = 0;

		int nbIteration = projet.getNbPeriod();
		long somme = 0;
		for (int i = 0; i < nbIteration; i++) {

			if (graphData.get(i).getTotal() == -1) {
				break;
			}

			long data = graphData.get(i).getTotal();

			somme += data;
			long pourcentage = 0;
			try {
				pourcentage = (somme * 100)
						/ Long.parseLong(projet.getRequestedFunding());
			} catch (ArithmeticException e) {
				e.printStackTrace();
			}
			long newPourcentage = pourcentage;
			if (newPourcentage > 100) {
				newPourcentage = 100;
			}

			long yDepart = largeur - ((pourcentageAccomplie * hauteur) / 100)
					+ offset;
			long yArrive = largeur - ((newPourcentage * hauteur) / 100)
					+ offset;

			int xDepart = i * (largeur / nombreDeCarre);
			int xArrive = (i + 1) * (largeur / nombreDeCarre);
			if (xArrive > largeur) {
				break;
			}

			paint.setColor(Color.argb(150, 131, 182, 255));
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
			// canvas.drawLine(positionX, offset, positionX, largeur + offset,
			// paint);

		}

	}
}