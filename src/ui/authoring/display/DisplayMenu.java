package ui.authoring.display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.sun.org.glassfish.external.statistics.impl.StatsImpl;
import com.sun.prism.paint.Color;

import gamedata.DisplayData;
import gamedata.GameData;
import gamedata.LineageData;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.authoring.delegates.DisplayDelegate;
import ui.authoring.delegates.MenuDelegate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import gamedata.ActorData;

public class DisplayMenu {
	private DisplayData myData;
	private MenuDelegate myDelegate;
	private List<ActorData> myActors;
	private Map<ActorData, ImageView> actorMap;
	private List<ImageView> imageData;
	private ListView<ImageView> displayList;
	private boolean success;
	private GameData myGameData;
	private final ObjectProperty<ListCell<ImageView>> dragSource = new SimpleObjectProperty<>();

	public DisplayMenu(DisplayData data, DisplayDelegate d, GameData gameData) {
		myData = data;
		// setupBackButton();
		myGameData = gameData;
		myGameData.setDisplayData(myData);
		displayList = new ListView<ImageView>();
		displayList.setMaxWidth(100);
		displayList.setMaxHeight(400);
		
		
		actorMap = new LinkedHashMap<ActorData, ImageView>();
		getPlaceable(gameData);
		setupCellFactory();
		myActors = new ArrayList<ActorData>(actorMap.keySet());
		data.setActorOrder(myActors);
		
	}
	private void getPlaceable(GameData gameData){
		Collection<LineageData> lineage = gameData.getLineageData();
		for (LineageData lineageData : lineage) {
			ActorData actor = lineageData.getProgenitor();
			if (actor.getType().isPlaceable()) {
				//myActors.add(actor);
				addActor(actor);
			}
		}
	}
	private void setupCellFactory(){
		 displayList.setCellFactory(lv -> {
	           ListCell<ImageView> cell = new ListCell<ImageView>(){
	               @Override
	               public void updateItem(ImageView item , boolean empty) {
	                   super.updateItem(item, empty);
	                   setGraphic(item);
	               }
	           };

	           cell.setOnDragDetected(event -> {
	        	   if (! cell.isEmpty()) {
	                   Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
	                   ClipboardContent cc = new ClipboardContent();
	                   cc.putImage(cell.getItem().getImage());
	                   //cc.putImage(cell.getItem().getImage());
	                   db.setContent(cc);
	                   dragSource.set(cell);
	                 
	               }
	           });

	           cell.setOnDragOver(event -> {
	               Dragboard db = event.getDragboard();
	               if (db.hasImage()) {
	                   event.acceptTransferModes(TransferMode.MOVE);
	               }
	           });

	         

	           cell.setOnDragDropped(event -> {
	               Dragboard db = event.getDragboard();
	              
	              if(db.hasImage()) {
	            	
                   ObservableList<ImageView> items = displayList.getItems();
                   int draggedIdx = findEquality(items,db.getImage());
                   
                   int thisIdx=items.indexOf(cell.getItem());
                   
                   ActorData temo=myActors.get(thisIdx);
                   ImageView temp = items.get(thisIdx);
                   myActors.set(thisIdx,myActors.get(draggedIdx));
                   
                   myActors.set(draggedIdx,temo);
                  
                   items.set(thisIdx, new ImageView(db.getImage()));
                   items.set(draggedIdx, temp);
                   //test
                 System.out.println(myData);
                   
                   //
                   List<ImageView> itemscopy = new ArrayList<>(displayList.getItems());
                   displayList.getItems().setAll(itemscopy);

                   success = true;
               }
               event.setDropCompleted(success);
	           });

	           return cell ;
	        });
	}
	private int findEquality(List<ImageView>list,Image image){
		
		for(ImageView i:list){
			Image current=i.getImage();
			if(testEquality(current,image)){
				return list.indexOf(i);
			}
			
		}
		return -1;
	}
	private boolean testEquality(Image a,Image b){
		for (int i = 0; i < a.getWidth(); i++)
		{
		  for (int j = 0; j < a.getHeight(); j++)
		  {
		    if (!a.getPixelReader().getColor(i, j).equals( b.getPixelReader().getColor(i, j))){
		    	 
		    	
		    	  return false;	
		  
		    }
		  }
		}
		return true;
	}
	private void addActor(ActorData actor) {
		actorMap.put(actor,
				new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(actor.getImagePath()))));
		displayList.getItems().add(actorMap.get(actor));
	}

	public void updateDisplayList() {
		// TODO:cleanup

		addPlaceable();
		removeNonPlaceable();

	}

	private void addPlaceable() {

		for (LineageData lineage : myGameData.getLineageData()) {
			if (lineage.getProgenitor().getType().isPlaceable() && !actorMap.containsKey(lineage.getProgenitor())) {
				addActor(lineage.getProgenitor());

			}

		}

	}

	private void removeNonPlaceable() {
		Collection<ActorData> actors = new HashSet<ActorData>(actorMap.keySet());
		for (ActorData actor : actors) {
			
			if (!actor.getType().isPlaceable()) {
				System.out.println(actor.getName());
				removeActor(actor);
			}

		}
		
	}

	private void removeActor(ActorData actor) {
		System.out.println(displayList.getItems().size());
		displayList.getItems().remove(actorMap.get(actor));
		System.out.println(displayList.getItems().size());
		actorMap.remove(actor);
	}

	public ListView<ImageView> getNode() {
		return displayList;
	}

	public void setWidth(double d) {
		displayList.setMaxWidth(d);
	}

	public void setHeight(double d) {
		displayList.setMaxHeight(d);
	}
	
	
	
	
	
	
	
	

}
