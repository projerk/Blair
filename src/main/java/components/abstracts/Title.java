package components.abstracts;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public abstract class Title extends Label {

    public Title(String text) {
        super(text);

        // listen for changes on heightProperty
        heightProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() > 0) {
                // if height no 0, restrict font
                fontProperty().bind(
                    Bindings.createObjectBinding(
                        () -> Font.font(getWidth() / 5),
                        widthProperty()
                    )
                );
            }
        });
    }
}
