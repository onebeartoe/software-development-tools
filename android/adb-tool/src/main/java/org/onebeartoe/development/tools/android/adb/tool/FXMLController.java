
package org.onebeartoe.development.tools.android.adb.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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
    
    private TextField adbPathTextField;
    
    private String adbPath = "adb";
    
    public FXMLController()
    {
// Can these statements move to the initialize() method?        
        adbPathTextField = new TextField("/path/to/adb");
        
        String userName = System.getProperty("user.name");
        
        adbPath = "C:\\Users\\" + userName + "\\AppData\\Local\\Android\\sdk\\platform-tools\\adb.exe";
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        System.out.println("You clicked me!");
        label.setText("dang dan gdang da dang!");

        String clipboard = "'I%s<3%shttp://www.onebeartoe.net/3d-printing/animals/owl/low-poly/'";
  
        String c = ouputTextArea.getText();
        clipboard = "'" + c + "'";
        
        // sample invocation of the shell to copy and paste text from a PC to an 
        // Andriod emulator
        //
        //     $ adb shell input text "'I%s<3%shttp://www.onebeartoe.net/3d-printing/animals/owl/low-poly/'"
        //
        String [] command = {adbPath, "shell", "input", "text", clipboard};
        
        ProcessBuilder builder = new ProcessBuilder(command);
        try 
        {
            Process jobProcess = builder.start();
            
            int waitValue = jobProcess.waitFor();

            // read the output from the command
            InputStream instream = jobProcess.getInputStream();
            InputStreamReader insteamReader = new InputStreamReader(instream);
            BufferedReader stdInput = new BufferedReader(insteamReader);		
            String s = null;
            List<String> stdout = new ArrayList();
            while ((s = stdInput.readLine()) != null) 
            {
                stdout.add(s);
            }

            // read any errors from the attempted command
            List<String> stderr = new ArrayList();
            InputStream is = jobProcess.getErrorStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader stdError = new BufferedReader(isr);
            while ((s = stdError.readLine()) != null) 
            {
                stderr.add(s);
            }

            int exitCode = jobProcess.exitValue();
            
            System.out.println("\n" + "Stdout:");
            List<String> stdoutLines = stdout.subList(0, stdout.size() );
            boolean noDevicesFound = false;
            for(String l : stdoutLines)
            {
                System.out.println(l);
                
                String target = "adb: error: connect failed: no devices/emulators found";
                if( l.contains(target) )
                {
                    noDevicesFound = true;
                }
            }
            
            if(noDevicesFound)
            {
                showNoDevicesFound();
            }

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
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        
    }

    /**
     * Fformat the input text to adb formatted text.
     * 
     * @param event 
     */
    @FXML
    private void inputTextKeyReleased(KeyEvent event)
    {
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
    
    private void showNoDevicesFound()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("No Android Emulator Found");
        String message = "No Android emulator devices were found. \n<br>" +
                "Make sure an emulator is running.";
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && result.get() == ButtonType.OK)
        {
            System.out.println("okay was the repsonse");
        }        
    }
    
    private String toAdbText(String input)
    {
        String output = input.replace(" ", "%s");
        
        return output;
    }
}
