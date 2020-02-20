package com.martin.Views;

import javafx.stage.Stage;
import org.docgene.help.JavaHelpFactory;
import org.docgene.help.gui.jfx.JFXHelpContentViewer;

import java.io.File;
import java.net.URL;

public class AyudaController {
    private JFXHelpContentViewer viewer;
    private void initializeHelp(Stage stage)
    {
        try {
            File file = new File("C:\\Users\\DAM\\Desktop\\ProyectosDI_MartinLoza\\Correo\\articles.zip");
            URL url =file.toURI().toURL();
            JavaHelpFactory factory = new JavaHelpFactory(url);
            factory.create();
            viewer = new JFXHelpContentViewer();
            factory.install(viewer);
            viewer.getHelpWindow(stage, "Help Content", 600, 700);
        }catch (Throwable e)
        {
            e.printStackTrace();
        }
    }


    public void start(Stage primaryStage) throws Exception {
        initializeHelp(primaryStage);
        viewer.showHelpDialog(500,500);

    }
}
