package adullact.publicrowdfunding;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;
 
public class CustomProgressBar extends ProgressBar {
    private String text;
    private Paint textPaint;
    private int sommeMax = 0;
    private int somme = 0;
    private Rect bounds;
 
    public CustomProgressBar(Context context) {
        super(context);
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        bounds = new Rect();

    }
 
    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        bounds = new Rect();
    }
 
    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        bounds = new Rect();
    }
 
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        textPaint.setTextSize(26);
        int x = getWidth() / 2 - bounds.centerX();
        int y = getHeight() / 2 - bounds.centerY();
       canvas.drawText(text, x, y, textPaint);

      
     
     
    }
 
    public synchronized void setText(String text) {
        this.text = text;
        drawableStateChanged();
    }
 
    public void setTextColor(int color) {
        textPaint.setColor(color);
        drawableStateChanged();
    }
    
    public void setMaxArgent(int sommeMax){
    	this.text = somme+"/"+sommeMax+"€";
    	this.sommeMax = sommeMax;
    
    }
    
    public void setArgent(int somme){
    	this.text = somme+"/"+sommeMax+"€";
    	this.somme = somme;
    
    }
}