package edenb.edenproj;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PointService {


    private  PointRepo drawRepo;
    private  ArrayList<CanvasChangeListener> listeners;
    private  ArrayList<Point> pointsList;

    public interface CanvasChangeListener 
    {
        void onChange();
    }

    public PointService(PointRepo drawRepo) 
    {
        this.drawRepo = drawRepo;
        listeners = new ArrayList<CanvasChangeListener>();
        pointsList = new ArrayList<>();
    }
    
   public void startDraw(Point point)
   {
        pointsList = new ArrayList<>();
        pointsList.add(point);
   }

   public void endDraw(Point point)
   {
        pointsList.add(point);
        System.out.println(pointsList);
        drawRepo.insert(pointsList);
   }

    public void addPoint(Point point) 
    {
        // drawRepo.insert(point);
        pointsList.add(point);
        // update all listeners that chat changed
        // for (CanvasChangeListener listener : listeners)
        //     listener.onChange();
    }

    public List<Point> getAllPoints() {

        return drawRepo.findAll();
    }

    public void addCanvasChangeListener(CanvasChangeListener listener)
    {
        synchronized (listeners)
      {
         listeners.add(listener);
      }
    }

    public void clearPoints() 
    {
        drawRepo.deleteAll();
    }

    public ArrayList<Point> DrawingPoints() 
    {
        synchronized(listeners){
        for (CanvasChangeListener listener : listeners){
            listener.onChange();
        }
        return pointsList;
    }
    }

    // public DrawRepo getDrawRepo() {
    //     return drawRepo;
    // }

    // public ArrayList<CanvasChangeListener> getListeners() {
    //     return listeners;
    // }
}
