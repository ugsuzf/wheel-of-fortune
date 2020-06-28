import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;
    private Scene settingScene;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        this.stage =stage;
        Parent root;
        settingScene=new Scene(new Setting(this));
        stage.setTitle("Wheel of fortune");
        stage.setScene(settingScene);
        stage.show();
    }

    void NewGame(){
        Stage stage1 = new Stage();
        stage.getScene().getWindow().hide();
        try {
            new NewGame().start(stage1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
