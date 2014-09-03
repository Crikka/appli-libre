package adullact.publicrowdfunding.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Ferrand and Nelaupe
 */
public class SimpleLine extends View {

    private final Paint paint;

    /**
     * @param context The context of the application
     */
    public SimpleLine(Context context) {
        super(context);
        paint = new Paint();
    }

    /**
     * @param context The context of the application
     * @param attrs   The attributs
     */
    public SimpleLine(Context context, AttributeSet attrs) {
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
        
        paint.setColor(Color.rgb(190,190,190));
        //paint.setColor(Color.rgb(255,255,255));
        canvas.drawLine(taille, 0, 19 * taille, 1, paint);
    }
}