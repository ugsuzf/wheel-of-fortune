import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.*;
import java.util.Set;
import java.util.TreeSet;

public class Setting extends BorderPane {
    static final String fileName="C:\\Users\\Padawan\\IdeaProjects\\Marul\\src\\Phrases";

    private ObservableList<String> all;
    private static ObservableList<String> selected;
    private ListView<String> allView;
    private ListView<String> selectedView;

    Setting (Main main){
        selected = FXCollections.observableArrayList(new TreeSet<>());
        selectedView=new ListView<>(selected);
        phrases();
        ImageIcon icon;
        Button rightS = new Button(" \u2192 ");// ->
        Button leftS = new Button(" \u2190 "); // <-
        Button addNew = new Button("Add new");
        Button delete = new Button("Remove");
        Button newGame = new Button("New game");
        VBox buttons = new VBox();
        buttons.getChildren().addAll(rightS, leftS, addNew, delete, newGame);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10));

        //moving
        EventHandler<ActionEvent> move = event -> {
            String path ="C:\\Users\\Padawan\\IdeaProjects\\Marul\\src\\addlist.mp3";
            Media media2 = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
            mediaPlayer2.setAutoPlay(true);

            ListView<String> from;
            ObservableList<String> fromList, toList;
            if (event.getSource().equals(rightS)){
                from=allView;
                fromList=all;
                toList=selected;

            }else if (event.getSource().equals(leftS)){
                from = selectedView;
                fromList = selected;
                toList = all;
            }else
                return;
            String string = from.getSelectionModel().getSelectedItem();

            if (string!=null){
                from.getSelectionModel().getSelectedItem();
                fromList.remove(string);
                toList.add(string);
            }
        };

        //add list
        EventHandler<ActionEvent> addPhrase=event -> {

            String string = JOptionPane.showInputDialog("New Phrase");
            if (string != null){
                try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true), true)) {
                    printWriter.println(string);
                    all.add(string);
                    String path ="C:\\Users\\Padawan\\IdeaProjects\\Marul\\src\\add.mp3";
                    Media media2 = new Media(new File(path).toURI().toString());
                    MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
                    mediaPlayer2.setAutoPlay(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        //remove
        EventHandler<ActionEvent> remove = event -> {
            String path ="C:\\Users\\Padawan\\IdeaProjects\\Marul\\src\\remove.mp3";
            Media media2 = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(media2);

            String string = allView.getFocusModel().getFocusedItem();
            if (string != null) {
                int check = JOptionPane.showConfirmDialog(null, "Delete \"" + string + "\"?", "Delete phrase", JOptionPane.OK_CANCEL_OPTION);
                if (check == 0) {
                    mediaPlayer2.setAutoPlay(true);
                    removePhrase(string);
                }
            }
        };

        //new game

        EventHandler<ActionEvent> startGame= event -> {
            String path ="C:\\Users\\Padawan\\IdeaProjects\\Marul\\src\\clapping.mp3";
            Media media2 = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
            if (selected.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please select at least one phrase");
            }else {
                mediaPlayer2.setAutoPlay(true);
                main.NewGame();
            }
        };



        rightS.setOnAction(move);
        leftS.setOnAction(move);
        addNew.setOnAction(addPhrase);
        delete.setOnAction(remove);

        setCenter(buttons);
        setLeft(allView);
        setRight(selectedView);
        newGame.setOnAction(startGame);


    }

    private void phrases() {
        Set<String> set =new TreeSet<>();
        try{
            BufferedReader bufferedReader= new BufferedReader(new FileReader(fileName));
            String temp;
            while ((temp=bufferedReader.readLine()) !=null){
                set.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        all=FXCollections.observableArrayList(set);
        allView=new ListView<>(all);
    }
    private void removePhrase(String string){
        all.remove(string);
        Set<String> allTemp = new TreeSet<>();
        allTemp.addAll(all);
        allTemp.addAll(selected);
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName))) {
            for (String s : allTemp) {
                printWriter.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
     static String getPhrase(){
        return selected.get((int) (Math.random() * selected.size()));

    }
}
