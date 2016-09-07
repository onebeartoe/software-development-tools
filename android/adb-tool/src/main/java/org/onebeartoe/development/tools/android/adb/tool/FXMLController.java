
package org.onebeartoe.development.tools.android.adb.tool;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import org.onebeartoe.system.Commander;

public class FXMLController implements Initializable 
{
    @FXML
    private Label label;
    
    @FXML
    private TextArea inputTextArea;
    
    @FXML   
    private TextArea ouputTextArea;
    
    @FXML
    private Button sendButton;
    
    private Commander commander;
    
    private TextField adbPathTextField;
    
    private String adbPath = "adb";
    
    public FXMLController()
    {
        commander = new Commander();
        
        adbPathTextField = new TextField("/path/to/adb");
        
        adbPath = "C:\\Users\\urhm020\\AppData\\Local\\Android\\sdk\\platform-tools\\adb.exe";
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        System.out.println("You clicked me!");
        label.setText("dang dan gdang da dang!");
        
        String args = "";
        
        String adbCommand = adbPath;
        adbCommand = adbCommand + args;        
//        adbCommand = "dir";
        
        try 
        {
            int exitCode = commander.executeCommand(adbCommand);
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        
        
        List<String> stderr = commander.getStderr();
        List<String> stdout = commander.getStdout();

        System.out.println("\n" + "Stdout:");
        stdout.stream()
            .forEach( s -> 
            {
                System.out.println(s);
            });

        boolean missingAdbPath = false;
        List<String> lines = stderr.subList(0, stderr.size() );
        System.out.println("\n" + "Errors:");
        for(String l : lines)
        {
            System.out.println(l);
            
            if( l.contains("'adb' is not recognized as an internal or external command") ||
                    l.contains("Cannot run program \"adb\"") )
            {
                missingAdbPath = true;
            }
        }
        
        if(missingAdbPath)
        {
            showMissingAdbDialog();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }

    @FXML
    private void inputTextKeyReleased(KeyEvent event)
    {
        // format the input text to adb formatted text
        System.out.println("ms word");
        
        String inputText = inputTextArea.getText();
        
        inputText = toAdbText(inputText);
        
        ouputTextArea.setText(inputText);
    }

    private void showMissingAdbDialog()
    {
        // Custom dialog
		
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Missing adb Executable Path");
        dialog.setHeaderText("This is a dialog. Enter info and \n" +
                "press Okay (or click title bar 'X' for cancel).");
        dialog.setResizable(true);

        Label label1 = new Label("full adb path: ");

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        grid.add(label1, 1, 1); // col=1, row=1
        
        grid.add(adbPathTextField, 2, 1);

        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        final ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk );

        // Result converter for dialog
        dialog.setResultConverter(new Callback<ButtonType, String>() 
        {
            @Override
            public String call(ButtonType b) 
            {
                if (b == buttonTypeOk) 
                {
                    String path = adbPathTextField.getText().trim();

                    return path;
                }

                return null;
            }
        });

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) 
        {
            String path = result.get();
            
            System.out.println("A new path was set for th adb executable: " + path);
            
            adbPath = path;
        }        
    }    
    
    private String toAdbText(String input)
    {
        String output = input.replace(" ", "%s");
        
        return output;
    }
}
