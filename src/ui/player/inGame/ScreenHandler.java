package ui.player.inGame;

import gamedata.ActorData;
import javafx.scene.layout.Pane;

public interface ScreenHandler {
	public Actor createActor(double x, double y, int option,ActorData actorData );
	public void showError(String msg);
	public void addActorToMap(int id, Actor actor);
	public void deleteActorFromScreen(int id);
	public boolean isActorInMap(int id);
	public double getWidth();
	public double getHeight();
	public void showUpgrades(Integer option, Actor actor);
}
