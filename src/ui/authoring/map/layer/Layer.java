package ui.authoring.map.layer;

import gamedata.MapLayersData;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Layer extends Pane{
	public abstract void activate();
	public abstract void deactivate();
	public abstract void clear();
	public abstract void undo();
	public abstract void setColor(Color c);
	public abstract boolean isActive();
}
