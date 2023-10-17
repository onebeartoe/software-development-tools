package org.onebeartoe.development.tools.titanic.fx.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.onebeartoe.development.tools.titanic.TitanicDataSource;
import org.onebeartoe.development.tools.titanic.statistics.TitanicPassenger;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    int t = 11;
    
    @Override
    public void start(Stage stage) throws IOException, SQLException
    {
t++;

//        TitanicDataSource ds;

        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
        
        List<TitanicPassenger> passengers = TitanicDataSource.retrievePassengers();
                
        System.out.println("passengers count: " + passengers.size());
        
        long slashCount = 0;
        for(TitanicPassenger passenger : passengers)
        {
            if(passenger.homeDest.contains("/"))
            {
                slashCount++;
                
            }
        }
        
        System.out.println("slashcount: " + slashCount);
        
        Map<String, Long> homeDestCounts = passengers.stream()
                              .collect(Collectors.groupingBy(TitanicPassenger::getHomeDest, Collectors.counting()));
        Set<String> keySet = homeDestCounts.keySet();
        for(String key : keySet)
        {
            System.out.println(key + ": " + homeDestCounts.get(key));
        }
//  .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
