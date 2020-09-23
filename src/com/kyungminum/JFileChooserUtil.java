package com.kyungminum;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class JFileChooserUtil {

    public static String jFileChooserUtil(){

        String osName = System.getProperty("os.name");
        String homeDir = System.getProperty("user.home");
        if(osName.equals("Mac OS X")){
            System.setProperty("apple.awt.fileDialogForDirectories", "true");
        }

        String folderPath = null;

        JFileChooser chooser = new JFileChooser(/*FileSystemView.getFileSystemView().getHomeDirectory()*/);
        chooser.setCurrentDirectory(new File("/"));
        chooser.setAcceptAllFileFilterUsed(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","*.txt");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);

        if(returnVal == JFileChooser.APPROVE_OPTION){
            folderPath = chooser.getSelectedFile().toString();
        }else if(returnVal == JFileChooser.CANCEL_OPTION){
            folderPath = null;
        }

        return folderPath;
    }
}
