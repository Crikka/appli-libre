package adullact.publicrowdfunding;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Nelaupe Lucas
 */
public class GraphiqueView extends View {

    private final Paint paint;

    /**
     * @param context The context of the application
     */
    public GraphiqueView(Context context) {
        super(context);
        paint = new Paint();
    }

    /**
     * @param context The context of the application
     * @param attrs   The attributs
     */
    public GraphiqueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

    }


    
    /**
     * Draw 
     */
    protected void onDraw(Canvas canvas) {

        paint.setAntiAlias(true);

        int nombreDeCarre = 10;
        
        int largeur = canvas.getWidth()-10;
        int hauteur = largeur;//canvas.getHeight()-10;


        paint.setColor(Color.rgb(170, 170, 170));
    
        for(int i=0;i<nombreDeCarre+1;i++){
        	// Horizontal
        	 canvas.drawLine(0, i*(hauteur/nombreDeCarre), largeur, i*(hauteur/nombreDeCarre), paint); 
        	 // Vertical
        	 canvas.drawLine(i*(largeur/nombreDeCarre), 0, i*(largeur/nombreDeCarre), hauteur, paint); 
        	
        }
        
        // Exemple de coubre
        int pourcentageAccomplie = 0;
        /// 100% = hauteur
        // 30 % = 
        
        paint.setColor(Color.rgb(255, 10, 17));
        
        for(int i=0;i<nombreDeCarre+1;i++){
        	int random = (int)(Math.random() * (20+1-1)) + 1;
        	System.out.println(random);
        	int newPourcentage = pourcentageAccomplie + random;
        	if(newPourcentage > 100){
        		newPourcentage = 100;
        	}
        	
        	int yDepart = hauteur - ((pourcentageAccomplie * hauteur) /100);
        	int yArrive =  hauteur - ((newPourcentage * hauteur) /100);
        	
        	int xDepart = i*(largeur/nombreDeCarre);
        	int xArrive =  (i+1)*(largeur/nombreDeCarre);
        	if(xArrive > largeur){
        		break;
        	}
        	
        	canvas.drawLine(xDepart, yDepart,xArrive, yArrive, paint);
        	pourcentageAccomplie = newPourcentage;
        }
    }
}