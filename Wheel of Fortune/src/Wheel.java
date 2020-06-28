import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;


public class Wheel extends StackPane {
    static Integer[] scores = new Integer[]{100, 300, 500, 250, 400, 800, 650, 150, 420, 900, 620, 230};
    private static final double Angle = 360d / (double) 12;
    private static  double StartAngle = 270;
    int radius = 220;
    int XCenter = 200;
    int YCenter = 200;
    double angle = (2 * Math.PI) / 12;
    private static Text text;
    static ArrayList<Segment> arrayList = new ArrayList<>();

    public Wheel() {
        DrawSegments();
    }

    public void DrawSegments(){
        Group Segments = new Group();
        Group texts = new Group();
        for (int i = 0; i < scores.length; i++) {
            Segment segment = new Segment();
            segment.setStartAngle(StartAngle);


            if(i%2==0){
                segment.setFill(Color.DARKBLUE);
            }
            else{
                segment.setFill(Color.BLUE);
            }
            StartAngle+=Angle;
            Segments.getChildren().add(segment);
            double x = Math.sin(angle * i) * radius,
                    y = Math.cos(angle * i) * radius;

            Label label = new Label(scores[i].toString());
            label.relocate(x,y);
            label.setTextFill(Color.WHITE);
            label.setFont(new Font("Arial", 25));
            segment.setLabel(label);
            if(i%2==0){

                label.setStyle("-fx-background-color: #13bbff");
            }

            else{

                label.setStyle("-fx-background-color: #31ffff");
            }


            texts.getChildren().add(label);
        }
        getChildren().addAll(texts,Segments);
    }
    class Segment extends Arc {
        private static final double Angle = 360d / (double) 12;
        private double StartAngle;
        int radius = 200;
        int XCenter = 200;
        int YCenter = 200;
        private Label label;

        public Label getLabel() {
            return label;
        }

        public void setLabel(Label label) {
            this.label = label;
        }

        public Segment() {
            setCenterX(XCenter);
            setCenterY(YCenter);
            setRadiusX(radius);
            setRadiusY(radius);
            setStartAngle(StartAngle);
            setLength(Angle);
            setType(ArcType.ROUND);
            setStroke(Color.BLACK);
        }
    }
}
