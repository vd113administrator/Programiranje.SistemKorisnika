/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programiranje.baza_korisnika_shell.server.izlaz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Graficka forma za izlaz servera 
 * @author Mikec
 */
public class IzlazApplication extends Application{
    private IzlazThread thread; 
    
    /**
     * Pokretanje forme 
     * @param primaryStage grafika 
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/programiranje/baza_korisnika_shell/server/izlaz/IzlazView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Server");
        primaryStage.show();
    }
    
    
    /**
     * Pozivna funkcija
     * @param args argumenti 
     */
    public static void pokreni(String ... args){
        launch(args);
    }
}
