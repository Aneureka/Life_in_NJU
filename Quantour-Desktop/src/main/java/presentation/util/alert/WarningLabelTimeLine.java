package presentation.util.alert;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;


/**
 * Created by wyj on 2017/4/18.
 */
public class WarningLabelTimeLine {

    public static void timeLineWarning(Label label) {
        label.setVisible(true);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(label.visibleProperty(), false);
        KeyFrame kf = new KeyFrame(Duration.millis(3000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public static void timeLineWarningContent(Label label, String content) {
        label.setText(content);
        timeLineWarning(label);
    }
}
