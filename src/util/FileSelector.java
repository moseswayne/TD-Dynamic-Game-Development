package util;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FileSelector {
    
    FileChooser myChooser;
    
    public FileSelector(String ... extension) {
        myChooser = makeFileChooser();
        setExtension(extension);
    }
    
    public File open() {
        return open(null);
    }
    
    public File open(Stage stage) {
        myChooser.setTitle("Choose file");
        return myChooser.showOpenDialog(stage);
    }
    
    public File saveTo(Stage stage) {
        myChooser.setTitle("Save to");
        return myChooser.showSaveDialog(stage);
    }
    
    public void setExtension(String ... extension) {
        myChooser.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extension));
    }
    
    private FileChooser makeFileChooser() {
        FileChooser result = new FileChooser();
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        return result;
    }

}
