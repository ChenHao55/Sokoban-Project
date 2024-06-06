package view;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class OptionsGamePanel {
    
    private String fileSeparator = File.separator;
    
    public File saveGame(char option) {
        File file = null;
        JFileChooser fileChooser = new JFileChooser();
        
        // Establece el directorio actual al directorio "games_saved"
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + fileSeparator + "games_saved"));
        
        // Filtrar para mostrar solo archivos .txt
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        // Muestra el diálogo de guardar archivo
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            
            // Añade .txt al fichero si no lo tiene ya
            if (option == 's' && !file.getPath().endsWith(".txt")) {
                file = new File(file.getPath() + ".txt");
            }
        }
        
        return file;
    }
}