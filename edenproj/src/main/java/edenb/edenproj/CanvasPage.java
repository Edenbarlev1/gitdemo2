package edenb.edenproj;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import edenb.edenproj.PointService.CanvasChangeListener;
import org.vaadin.pekkam.Canvas;
import org.vaadin.pekkam.CanvasRenderingContext2D;
import java.util.List;

@Route("/")
public class CanvasPage extends VerticalLayout {

    private PointService drawService;
    private static final int CANVAS_WIDTH = 300;
    private static final int CANVAS_HEIGHT = 300;
    private CanvasRenderingContext2D ctx;
    private boolean isDrawing = false;
    private String userName;
    private String colorState;

    public CanvasPage(PointService drawService) {
        this.drawService = drawService;

        H2 h2 = new H2("Welcome to canvas page");
        TextField textField = new TextField();
        add(textField, new Button("Set Name", e-> {userName=textField.getValue(); System.out.println(userName);}));

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        ctx = canvas.getContext();
        ctx.setLineWidth(1);
        colorState = "red";
        ctx.setStrokeStyle(colorState);
        canvas.getStyle().set("border", "3px solid black");


        Button btn = new Button("Clear", event -> {
            colorState = "black"; 
            clearCanvas();
        });
        
        Button btn1 = new Button("Yellow", event -> {
            colorState = "yellow";
            ctx.setStrokeStyle(colorState);
        });

        Button btn2 = new Button("blue", event -> {
            colorState = "blue";
            ctx.setStrokeStyle(colorState);
        });

        Button btn3 = new Button("gray", event -> {
            colorState = "gray";
            ctx.setStrokeStyle(colorState);
        });


        canvas.addMouseDownListener(event -> {
                        isDrawing = true;
                        ctx.beginPath();
                        Point p = new Point(event.getOffsetX(), event.getOffsetY(), userName, colorState);
                        ctx.moveTo(event.getOffsetX(), event.getOffsetY());
                        drawService.startDraw(p);
                      //  ctx.fillRect(p.getX(), p.getY(), 2, 2);
        });

        canvas.addMouseMoveListener(event -> {                    
                        if (isDrawing)
                        {
                            Point p = new Point(event.getOffsetX(), event.getOffsetY(), userName, colorState);
                            ctx.lineTo(p.getX(), p.getY());
                            ctx.stroke();
                            drawService.addPoint(p);
                        }
        });

        canvas.addMouseUpListener(event -> {
                        Point p = new Point(event.getOffsetX(), event.getOffsetY(), userName, colorState);
                        isDrawing = false;
                        drawService.endDraw(p);
                        ctx.closePath();
                        drawService.DrawingPoints();
                        drawService.clearPoints();
        });
      
         HorizontalLayout buttonsLayout = new HorizontalLayout(btn, btn1, btn2, btn3);
        // הוספת הרכיבים ל־HorizontalLayout
        add(h2, buttonsLayout, canvas);
        // יישור אופקי של הרכיבים בתוך HorizontalLayout
        setHorizontalComponentAlignment(Alignment.CENTER, buttonsLayout, canvas, h2);
      
        refreshCanvas();

        drawService.addCanvasChangeListener(new CanvasChangeListener() {

            @Override
            public void onChange() 
            {
                //System.out.println("\n>>>>>> CanvasChangeListener: " + userName +"\n");
                UI ui = getUI().orElseThrow();
                ui.access(() -> refreshCanvas());
        }
        });
    }

    private void clearCanvas() 
    {
        ctx.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        ctx.beginPath();
        ctx.setStrokeStyle("black");
        drawService.clearPoints();
    }

    private void refreshCanvas() {
        if(userName != null)
        {
        List<Point> allPoints = drawService.getAllPoints();
        // מספר הנקודות ברשימה
        int pointsCount = allPoints.size();
    
        if (pointsCount > 1) {
            // מתחיל נתיב חדש בציור
            ctx.beginPath();
            //מקבל את הנקודה הראשונה ברשימה
            Point firstPoint = allPoints.get(0);
            ctx.moveTo(firstPoint.getX(), firstPoint.getY());

            //לולאה שעוברת על כל הנקודות מהנקודה השנייה והלאה.
            for (int i = 1; i < pointsCount; i++) {
                //מקבל את הנקודה הנוחכית
                Point currentPoint = allPoints.get(i);
                ctx.setStrokeStyle(currentPoint.getColor());
                //מעביר קו בין הנקודה הראשונה לנקודה הנוחכית 
                ctx.lineTo(currentPoint.getX(), currentPoint.getY());
            }
            ctx.stroke();
        }
        }
    }
}
