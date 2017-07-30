package ui.player.inGame;

import java.io.File;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author Anngelyque
 * @author Anh
 *
 */
public class MusicPlayer {

	private VBox layout;
	private CheckBox checkbox;
	private HBox component1;
	private Media song;
	private MediaPlayer mediaPlayer;
	private Slider volumeSlider;
	static final String heroSong = "music/hero_song.mp3";

	public MusicPlayer() {
		layout = new VBox(20);
		component1 = new HBox();

		song = new Media(new File(heroSong).toURI().toString());
		mediaPlayer = new MediaPlayer(song);

		checkbox = new CheckBox("Mute");
		checkbox.setOnAction(e -> mediaPlayer.setMute(!mediaPlayer.isMute()));

		Label volumeLabel = new Label("Vol: ");
		volumeSlider = new Slider();
		setupVolumeSlider();

		setupMusicPlayer();

		component1.getChildren().addAll(volumeLabel, volumeSlider);
		layout.getChildren().addAll(checkbox, component1);
	}

	private void setupMusicPlayer() {
		// TODO Auto-generated method stub
		mediaPlayer.setOnReady(new Runnable() {
			public void run() {
				if (!volumeSlider.isValueChanging()) {
					volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume() * 100));
				}
			}
		});
	}

	private void setupVolumeSlider() {
		volumeSlider.setPrefWidth(90);
		volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
		volumeSlider.setMinWidth(20);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (volumeSlider.isValueChanging()) {
					mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
				}
			}
		});

	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setSong(String s) {
		song = new Media(new File(s).toURI().toString());
		mediaPlayer = new MediaPlayer(song);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}

	public Node getNode() {
		return layout;
	}
}
