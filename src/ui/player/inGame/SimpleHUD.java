package ui.player.inGame;

import java.util.Map;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import util.observerobservable.VoogaObserver;

/**
 * Creates a simple grid pane HUD
 * @author anngelyque, sarahzhou, gideon
 *
 */
public class SimpleHUD implements VoogaObserver<Map<String,String>>{

	private GridPane hud;
	private int rowIdx = 0;
	private static final String css = "HUD.css";
	private static final String gridId = "grid";
	private static final String keyId = "key";
	private static final String valId = "val";
	
	public GridPane getGrid(){
		return hud;
	}
	
	public SimpleHUD(){
		hud = new GridPane();
		hud.setHgap(10.);
		applyCSSToParent(hud, css, gridId);
	}
	

	@Override
	public void update(Map<String, String> arg) {
		hud.getChildren().clear();
		arg.forEach((k,v) -> display(k,v,rowIdx));
	}
	
	private void display(String key, String val, int row) {
		hud.add(makeAndApplyCSStoText(key, keyId), 0, row);
		hud.add(makeAndApplyCSStoText(val, valId), 1, row);
		rowIdx++;
	}

	private Text makeAndApplyCSStoText(String strText, String Id){
		Text text = new Text(strText);
		applyCSSToNode(text, Id);
		return text;
	}
	
	private void applyCSSToParent(Parent node, String css, String Id){
		node.getStylesheets().add(css);
		node.setId(Id);
	}
	
	private void applyCSSToNode(Node node, String Id){
		node.setId(Id);
	}
}
