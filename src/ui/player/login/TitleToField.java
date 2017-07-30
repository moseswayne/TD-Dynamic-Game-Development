package ui.player.login;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class TitleToField {

	private HBox completeHbox;
	private Text title;
	private TextField field;
	
	public HBox getCompleteHbox() {
		return completeHbox;
	}
	public void setCompleteHbox(HBox completeHbox) {
		this.completeHbox = completeHbox;
	}
	public Text getTitle() {
		return title;
	}
	public void setTitle(Text title) {
		this.title = title;
	}
	public TextField getField() {
		return field;
	}
	public void setField(TextField field) {
		this.field = field;
	}
	
	public TitleToField(Text title, String fieldtype) {
		completeHbox = new HBox();
		this.title = title;
		if (fieldtype.equals("TextField")) {
			this.field = new TextField();
		}
		else if (fieldtype.equals("PasswordField")) {
			this.field = new PasswordField();
		}
	}
}
