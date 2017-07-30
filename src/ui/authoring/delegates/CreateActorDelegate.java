package ui.authoring.delegates;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import util.Tuple;

public interface CreateActorDelegate {
	public abstract void closeSelfAndReturn(Pane pane, String actorTypeName, String imagePath);
	public abstract void closeSelf(Pane pane);
}
