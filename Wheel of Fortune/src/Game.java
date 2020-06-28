import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import static javafx.scene.control.ButtonType.OK;


class NewGame extends Application  {
    HBox upper = new HBox();
    BorderPane lower = new BorderPane();
    Label label;
    Button play = new Button("Play");
    Button exit = new Button("Exit");
    BorderPane bottom = new BorderPane();
    Group arrow = new Group();
    Group score1 = new Group();
    static Wheel wheel;
    BorderPane b = new BorderPane();
    static TextField t;
    static Integer randomScore;
    static  Integer currentScore=9;

    static ArrayList<Label> array = new ArrayList<>();
    static RotateTransition rotate;

    @Override
    public void start(Stage stage) throws Exception {
        upper.setAlignment(Pos.CENTER);
        upper.setSpacing(5);
        play.setPrefSize(120,80);
        exit.setPrefSize(60,30);
        play.setTextFill(Color.BLACK);
        exit.setTextFill(Color.BLACK);
        play.setFont(new Font("Arial", 30));
        exit.setFont(new Font("Arial", 15));
        bottom.setCenter(play);
        bottom.setBottom(exit);
        lower.setBottom(bottom);

        wheel = new Wheel();


        for(int i=0; i < Setting.getPhrase().length(); i++){
            Character current = Setting.getPhrase().toUpperCase().charAt(i);
            label = new Label(current.toString());
            label.setFont(new Font("Arial", 30));
            label.setPrefSize(35,50);
            label.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, null)));
            label.setAlignment(Pos.CENTER);
            if (current==' ') {
                label.setStyle("-fx-text-fill: white; -fx-background-color: white;");
            }else {
                label.setStyle("-fx-text-fill: #31ffff; -fx-background-color: #31ffff;");
            }
            upper.getChildren().add(label);
            upper.setHgrow(label, Priority.ALWAYS);
            array.add(label);

        }


        play.setOnAction (event ->{
            String path ="C:\\Users\\Padawan\\IdeaProjects\\Marul\\src\\spinn.mp3";
            Media media2 = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
            mediaPlayer2.setAutoPlay(true);
            RandomRotation();
            rotate.setOnFinished(event1 -> {
                Platform.runLater(NewGame::Game);
            });

        } );

        exit.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("The game will not be saved "+
                    "\nAre you sure you want to exit?"
            );
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get()== OK){
                Stage stage1 = new Stage();
                stage.getScene().getWindow().hide();
                try {
                    new Main().start(stage1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }});


        Rectangle rectangle = new Rectangle( 50.0, 350.0, 70.0,40.0);
        rectangle.setFill(Color.BLUE);
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                120.0,330.0, 200.0, 370.0, 120.0, 410.0);
        triangle.setFill(Color.BLUE);
        arrow.getChildren().addAll(triangle, rectangle);
        b.setCenter(arrow);
        lower.setLeft(b);


        Label score = new Label(" Your score:");
        score.setFont(new Font("Arial", 16));
        t= new TextField("0");
        t.setEditable(false);
        t.setFont(new Font("Arial", 20));
        t.setStyle("-fx-font-weight: Bold");
        t.setPrefSize(120,60);
        score.setLabelFor(t);
        score1.getChildren().addAll(t,score);



        lower.setTop(score1);
        lower.setCenter(wheel);
        BorderPane root = new BorderPane();
        root.setTop(upper);
        root.setCenter(lower);
        Scene scene = new Scene(root,700,700);
        stage.setScene(scene);
        stage.setTitle("Game is starting");
        stage.show();
    }


    public  static  void RandomRotation(){
        Integer[] arr = new Integer[]{
                360, 390, 420, 450, 480, 510, 540, 570, 600, 630, 660, 690
        };


        rotate = new RotateTransition() ;
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setAutoReverse(false);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setDuration(new Duration(2500));
        int   minimum = 0;
        int maximum = 11;
        Random rn = new Random();
        int range = maximum - minimum +1; //+1
        int randomAngle = arr[rn.nextInt(range) ];
        rotate.setByAngle(randomAngle);

        randomAngle%=360;

        randomAngle= randomAngle/30;

        randomScore = currentScore + randomAngle ;

        if(randomScore >= 12){
            randomScore = randomScore-12; }
        currentScore=randomScore;


        rotate.setNode(wheel);
        rotate.play();


    }



    public static Boolean CheckingLabels(){
        for(Label a: array){
            if(a.getStyle().equals("-fx-text-fill: #31ffff; -fx-background-color: #31ffff;")){
                return true;
            }
        } return false;
    }

    public static void CalculatingScore(){

        Integer current = Integer.parseInt(t.getText());
        Integer sum = Wheel.scores[randomScore]+ current;
        t.setText(sum.toString());

    }
    public static void Game(){
        String guessed;
        TextInputDialog e = new TextInputDialog();
        e.setHeaderText("Good luck!");
        e.setContentText("Guess a letter");
        Optional<String> result= e.showAndWait();

        String path1 ="C:\\Users\\Padawan\\IdeaProjects\\Marul\\src\\wrong.mp3";
        Media media = new Media(new File(path1).toURI().toString());
        MediaPlayer wrongAnswer = new MediaPlayer(media);
        String path ="C:\\Users\\Padawan\\IdeaProjects\\Marul\\src\\tada.mp3";
        Media media2 = new Media(new File(path).toURI().toString());
        MediaPlayer correctAnswer = new MediaPlayer(media2);

        guessed = e.getEditor().getText().toUpperCase();
        if(result.isPresent()) {
            if (Setting.getPhrase().toUpperCase().contains(guessed)) {
                for (Label l : array) {
                    if (l.getText().contains(guessed) && l.getStyle().equals("-fx-text-fill: #31ffff; -fx-background-color: #31ffff;")) {
                        l.setStyle("-fx-text-fill: Black; -fx-background-color: White");
                        correctAnswer.setAutoPlay(true);
                        continue;
                    }
                }
                CalculatingScore();
            } else {
                TextInputDialog e1 = new TextInputDialog();
                e1.setContentText("Wrong:( \n Now you have 2 attempts");
                wrongAnswer.setAutoPlay(true);
                e1.setHeaderText("Good luck!");
                e1.setTitle("Heey");
                Optional<String> result1 = e1.showAndWait();
                guessed = e1.getEditor().getText().toUpperCase();
                if (result1.isPresent()) {
                    if (Setting.getPhrase().toUpperCase().contains(guessed)) {
                        for (Label l : array) {
                            if (l.getText().contains(guessed) && l.getStyle().equals("-fx-text-fill: #31ffff; -fx-background-color: #31ffff;")) {
                                l.setStyle("-fx-text-fill: Black; -fx-background-color: White");
                                correctAnswer.setAutoPlay(true);
                                continue;
                            }
                        }
                        CalculatingScore();
                    } else {
                        TextInputDialog e2 = new TextInputDialog();
                        e2.setContentText("Wrong:( \n Now you have 1 attempt");
                        wrongAnswer.setAutoPlay(true);
                        e2.setHeaderText("Good luck!");
                        e2.setTitle("Heey");
                        Optional<String> result2 = e2.showAndWait();
                        guessed = e2.getEditor().getText().toUpperCase();
                        if (result2.isPresent()) {
                            if (Setting.getPhrase().toUpperCase().contains(guessed)) {
                                for (Label l : array) {
                                    if (l.getText().contains(guessed) && l.getStyle().equals("-fx-text-fill: #31ffff; -fx-background-color: #31ffff;")) {
                                        l.setStyle("-fx-text-fill: Black; -fx-background-color: White");
                                        correctAnswer.setAutoPlay(true);
                                        continue;
                                    }
                                }
                                CalculatingScore();
                            } else {
                                Alert m = new Alert(Alert.AlertType.INFORMATION);
                                m.setTitle("Heyy");
                                m.setHeaderText("Wrong:(");
                                wrongAnswer.setAutoPlay(true);
                                m.setContentText(" Try one more time:) \n No one lose this game :))");
                                m.show();

                            }
                        }
                    }
                }
            }
        }
        if(!CheckingLabels()){
            Alert congrats = new Alert(Alert.AlertType.INFORMATION);
            String path2 ="C:\\Users\\Padawan\\IdeaProjects\\Marul\\src\\clapping.mp3";
            Media media3 = new Media(new File(path2).toURI().toString());
            MediaPlayer finish = new MediaPlayer(media3);
            finish.setAutoPlay(true);
            congrats.setTitle("The end");
            congrats.setHeaderText("Finish");
            congrats.setContentText("Your score is "+ t.getText());

            congrats.showAndWait();
        }

    }
}



