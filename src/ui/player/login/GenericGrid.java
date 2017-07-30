package ui.player.login;

import java.util.Map;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class GenericGrid extends DataEntryGrid{

		private Map<Text, TextField> entryMap;
		
		public Map<Text, TextField> getEntryMap() {
			return entryMap;
		}
		
		public GenericGrid(Map<Text, TextField> map){
			super(map);
			this.entryMap = map;
			addToGrid(entryMap);
		}
		
		@Override
		public void addValues(){
			
		}

	}

