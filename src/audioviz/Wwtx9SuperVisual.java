/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author wangweihan
 */
public class Wwtx9SuperVisual implements Visualizer {
    private final String name = "Wwtx9 Visualizer";
    
    private Integer numBands;
    private AnchorPane vizPane;
    
    private Double width = 0.0;
    private Double height = 0.0;
    private Double degree = 0.0;
    private Double midX = 0.0;
    private Double midY = 0.0;
    private Double radiusCenter = 90.0;//90
    private Double radiusRote = 20.0;//40
    private Double roteAngle = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    
    
    private final Double startHue = 260.0;
   
    private Circle[] circles;
   
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end();
        
        this.numBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        midX = width/2;
        midY = height/2;
     
        circles = new Circle[numBands];
        for (int i = 0; i < numBands; i++) {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setOffsetX(4.0);
            dropShadow.setOffsetY(4.0);
            dropShadow.setColor(Color.hsb(startHue-10, 1.0, 1.0, 1.0));
            
            Circle circle = new Circle();
            degree = ((double)i * ((2* PI) / numBands));
           
          
           System.out.println(degree);
            circle.setEffect(dropShadow);
            circle.setCache(true);
            circle.setCenterX(midX + (radiusCenter * cos(degree)));
            circle.setCenterY(midY - (radiusCenter * sin(degree)));
            
            circle.setRadius(radiusRote);
            circle.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
           
            vizPane.getChildren().add(circle);
            
            circles[i] = circle; 
        }

    }
    @Override
    public void end() {
         if (circles != null) {
             for (Circle circle : circles) {
                 vizPane.getChildren().remove(circle);
             }
            circles = null;
        } 
    }
    
    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if (circles == null) {
            return;
        }
       
        Integer num = min(circles.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) {
            DropShadow dropShadow2 = new DropShadow();
            dropShadow2.setOffsetX(4.0);
            dropShadow2.setOffsetY(4.0);
            dropShadow2.setColor(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            circles[i].setEffect(dropShadow2);
            circles[i].setRadius(((60.0 + magnitudes[i])/60.0)*1.3 + radiusRote);
            circles[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
        }
    }
    
}
