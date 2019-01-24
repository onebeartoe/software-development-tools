
package org.onebeartoe.data.visualization.serial.plotter;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import static javafx.application.Application.launch;
import org.onebeartoe.io.serial.SerialPorts;
import org.onebeartoe.system.Sleeper;

/**
 * This application uses JavaFX and to visualize data (float values) as it streams 
 * in from a serial input connection.
 * 
 * This was tested with the Adafruit EZ Link (adafruit.com/products/1628) using this command:
 * 
 * $ java -Djava.library.path="c:\opt\rxtx" -jar target/serial-plotter-0.0.1-SNAPSHOT.jar
 * 
 * or on 64bit operating system:
 * $ java -Djava.library.path="C:\opt\rxtx\ch-rxtx-2.2-20081207-win-x64" -jar target/serial-plotter-0.0.1-SNAPSHOT.jar
 * 
 * @author Roberto Marquez
 */
public class SerialPlotter extends Application implements SerialPortEventListener
{
    private Logger logger;
    
    private NumberAxis xAxis = new NumberAxis(0,MAX_DATA_POINTS,MAX_DATA_POINTS/10);
    
    private static final int MAX_DATA_POINTS = 50;
    
    private int xSeriesData = 0;
    
    private FXMLLoader loader;
    
    private Map<String, DataChannel> dataMap;

    private List<String> messages;

    private BufferedReader input;

    NumberAxis yAxis = new NumberAxis();
    
    private SerialPort serialPort;
    
    /**
     * This is the chart that is presented to the user.
     */
    final AreaChart<Number, Number> sc = new AreaChart<Number, Number>(xAxis, yAxis) 
    {
//TODO: or was it this that made the tick marks appear?        
//        @Override 
//        protected void dataItemAdded(XYChart.Series<Number, Number> series, int itemIndex, XYChart.Data<Number, Number> item) 
//        {
//            ; // This method is overriden with empty statement, to remove symbols on each data point.
//        }
    };    
    
    private synchronized void addDataToSeries(String dataName)
    {
        DataChannel dc = dataMap.get(dataName);

        ConcurrentLinkedQueue<Number> dataQueue = dc.getDataQueue();
        if( !dataQueue.isEmpty() )
        {
            int xPosition = dc.getxPosition();
            Number value = dataQueue.remove();
            AreaChart.Data acData = new AreaChart.Data(xSeriesData, value);
            XYChart.Series cs = dc.getChartSeries();
            cs.getData().add(acData);

            dc.setxPosition(xPosition+1);

            if(cs.getData().size() > MAX_DATA_POINTS)
            {
                int size = cs.getData().size() - MAX_DATA_POINTS;
                ObservableList data = cs.getData();
                data.remove(0, size);
            }
        }

        xSeriesData++;
                
        // update 
        xAxis.setLowerBound(xSeriesData-MAX_DATA_POINTS);
        xAxis.setUpperBound(xSeriesData-1);
    }
    
    @Override
    /**
     * This is called to initialize the JavaFX application.
     */
    public void init()
    {
        logger = Logger.getLogger( getClass().getName() );
       
        dataMap = new HashMap();
        
        messages = new ArrayList();
        
        initializeSerialPort();
    }    
    
    private void initializeSerialPort()
    {
        try
        {
            System.out.println("obtaining serial port");
            serialPort = SerialPorts.get("COM27");
            System.out.println("serial port obtained");
            Sleeper.sleepo(2000);
            
            InputStream is = serialPort.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            input = new BufferedReader(isr);
            
            serialPort.addEventListener(this);
        }
        catch (Exception | NoClassDefFoundError ex)
        {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    @Override
    public synchronized void serialEvent(SerialPortEvent event) 
    {
        if(event.getEventType() == SerialPortEvent.DATA_AVAILABLE) 
        {
            try 
            {
                String inputLine = input.readLine();

                messages.add(inputLine);
 
                String [] split = inputLine.split(":");
                
                if( split.length >= 2)
                {
                    twoArgSerialEvent(split);
                }
                else
                {
                    System.out.println("bad data: " + inputLine);
                }
            
                System.out.println(inputLine + ": " + xSeriesData);
            } 
            catch (Exception e) 
            {
                System.err.println(e.toString());
            }
        }        
    }

    @Override
    public void start(Stage stage) throws Exception 
    {
        URL url = getClass().getResource("/fxml/Scene.fxml");
                
        loader = new FXMLLoader(url);
                
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
//TODO: is this really what made the tick marks appear?        
//        scene.getStylesheets().add("/styles/Styles.css");

        logger = Logger.getLogger(getClass().getName());
                
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickMarkVisible(true);
        xAxis.setTickLabelsVisible(true);
        xAxis.setTickUnit(10);
        
        yAxis.setAutoRanging(true);
        yAxis.setTickMarkVisible(true);
        yAxis.setTickLabelsVisible(true);
        yAxis.setTickUnit(10);
        
        sc.setAnimated(false);
        sc.setId("serialPlotterChart");
        sc.setTitle("Serial Plotter");
        
        SerialPotterFxmlController controller = loader.getController();
        AnchorPane anchorPane = controller.getAnchorPane();
        ObservableList<Node> children = anchorPane.getChildren();
        
        children.add(sc);

        stage.setScene(scene);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() 
        {
            @Override
            public void handle(WindowEvent t) 
            {
                Platform.exit();
                System.exit(0);
            }
        });
       
        stage.setTitle("onebeartoe apps");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop()
    {
        SerialPorts.shutdown(serialPort);                
    }
    
    private void twoArgSerialEvent(String [] split)
    {
        String dataName = split[0].trim();

        if( dataName.length() != 0)
        {
            String s = split[1];                    
            double d  = Double.valueOf(s);

            DataChannel dataChannel = dataMap.get(dataName);
            if(dataChannel == null)
            {
                dataChannel = new DataChannel();

                XYChart.Series chartSeries = dataChannel.getChartSeries();
                chartSeries.setName(dataName);                                

                Platform.runLater(
                        () -> {sc.getData().add(chartSeries);}
                );


                dataMap.put(dataName, dataChannel);
            }

            dataChannel.getDataQueue().add(d);                    

            int size = messages.size();
            if(size >= 50)
            {
                messages.remove(0);
            }

            Platform.runLater(
                () -> { addDataToSeries(dataName); }
            );
        }
    }
}