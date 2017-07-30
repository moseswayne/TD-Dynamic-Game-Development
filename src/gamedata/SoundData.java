package gamedata;

/**
 * Use with music
 * 
 * @author maddiebriere
 * @author Anh 
 *
 */
public class SoundData {
	private String musicFilePath; 
	public SoundData(String music){
		setMusicFilePath(music);
	}
	public String getMusicFilePath() {
		return musicFilePath;
	}
	public void setMusicFilePath(String musicFilePath) {
		this.musicFilePath = musicFilePath;
	}
}
