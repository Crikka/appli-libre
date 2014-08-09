package adullact.publicrowdfunding.controlleur.detailProjet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Nelaupe Lucas
 */
public class drawLine extends View {

    private final Paint paint;

    /**
     * @param context The context of the application
     */
    public drawLine(Context context) {
        super(context);
        paint = new Paint();
    }

    /**
     * @param context The context of the application
     * @param attrs   The attributs
     */
    public drawLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

    }

    /**
     * Draw the line
     */
    protected void onDraw(Canvas canvas) {

        paint.setAntiAlias(true);

        int iWidth = canvas.getWidth();
        int taille = iWidth / 20;

        paint.setColor(Color.rgb(170, 170, 255));
        canvas.drawLine(taille, 0, 19 * taille, 1, paint);
    }
}