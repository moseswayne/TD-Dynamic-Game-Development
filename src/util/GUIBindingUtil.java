package util;

import java.util.Collection;

import javafx.scene.Node;

public class GUIBindingUtil {
	
	public static void bindVisisble(Node thisNode, Collection<?> emptyCheck) {
		thisNode.visibleProperty().bind(thisNode.managedProperty());
		thisNode.setManaged(emptyCheck.size()>0);
	}

}
