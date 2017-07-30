package ui.authoring.map;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;



import XML.xmlmanager.classes.ConcreteFileHelper;
import gamedata.MapLayersData;
import gamedata.PathData;
import gamedata.DisplayData;
import gamedata.LayerData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ui.Preferences;
import ui.authoring.PopupSize;
import ui.authoring.delegates.LayerViewDelegate;
import ui.authoring.delegates.PopViewDelegate;
import ui.authoring.map.layer.BaseLayerView;
import ui.authoring.map.layer.Layer;
import ui.authoring.map.layer.LayerPopupDelegate;
import ui.authoring.map.layer.LayerPopupView;
import ui.authoring.map.layer.PathLayerView;
import ui.authoring.map.layer.PolygonLayerView;
import ui.general.CustomColors;
import ui.general.ImageViewPane;
import ui.general.UIHelper;
import util.Tuple;

/**
 * 
 * Creates and stores the PathData object
 * 
 * @author TNK
 *
 */
public class MapEditorView extends StackPane implements LayerViewDelegate, LayerPopupDelegate {
	
	private boolean IS_LOADED = false;
	private static final Color[] LAYER_COLORS = { 
			CustomColors.AMBER, CustomColors.BLUE_500, 
			CustomColors.GREEN, CustomColors.INDIGO };

	private ImageViewPane myBackgroundView;
	private MapLayersData myMapData;
	private List<Layer> myLayers;
	private HBox myLayerPicker;
	private PopViewDelegate myPopDelegate;
	private BaseLayerView myBaseLayer;
	private DisplayData myDisplayData;
	private Pane myLayerPopup;
	
	public MapEditorView(PopViewDelegate popDelegate, MapLayersData mapData, DisplayData displayData) {

		super();
		myLayers = new ArrayList<>();
		myBackgroundView = new ImageViewPane(new ImageView(new Image(displayData.getBackgroundImagePath())));
		myMapData = mapData;
		myPopDelegate = popDelegate;
		myBaseLayer = new BaseLayerView(myMapData.getMyBaseData());
		myDisplayData=displayData;
		setupViews();
		setupMouseEvents();
		this.widthProperty().addListener(e -> sizeDidChange());
		this.heightProperty().addListener(e -> sizeDidChange());
	}
	
	@Override
	protected void layoutChildren(){
		super.layoutChildren();
		if(!IS_LOADED)
			didFinishLayout();
	}

	private void didFinishLayout() {
		IS_LOADED = true;
		setupMapData();
	}

	private void setupMouseEvents() {
		this.myBackgroundView.addEventHandler(MouseEvent.MOUSE_ENTERED,
				e -> this.getScene().setCursor(Cursor.CROSSHAIR));
		this.myBackgroundView.addEventHandler(MouseEvent.MOUSE_EXITED, e -> this.getScene().setCursor(Cursor.DEFAULT));
	}

	private void setupViews() {
		UIHelper.setBackgroundColor(myBackgroundView, CustomColors.GREEN);
		StackPane.setAlignment(myBackgroundView, Pos.TOP_CENTER);
		StackPane.setMargin(myBackgroundView, new Insets(8, 8, 72, 8));
		this.getChildren().add(myBackgroundView);

		setupButtons();
		setupLayerSelector();

	}

	/**
	 * adds the already existing layers in the mapdata into this classes
	 * children nodes
	 */
	private void setupMapData() {
		addLayerView(myBaseLayer, "Base"); //TODO
		addLayerView(new PathLayerView(myMapData.getMyPathData()), "Path");
		for (Entry<String, LayerData> entry : myMapData.getMyLayers().entrySet()) {
			addLayerView(new PolygonLayerView(entry.getValue()), entry.getKey());
		}

	}

	/**
	 * This adds a layer that spans the size of the backgroundView. It will able
	 * to switched to by clicking the button on the HBox on the bottom of the
	 * MapEditor
	 * 
	 * @param layer
	 * @param layerName
	 *            the name that you can set to w.e you want
	 */
	private void addLayerView(Layer layer, String layerName) {
		StackPane.setAlignment(layer, Pos.TOP_CENTER);
		StackPane.setMargin(layer, getAdjustedInsets());
		layer.setColor(LAYER_COLORS[myLayers.size() % LAYER_COLORS.length]);
		Label layerNumber = new Label(layerName);
		layerNumber.setFont(Preferences.FONT_MEDIUM_BOLD);
		layerNumber.setTextFill(CustomColors.BLUE_500);
		StackPane.setMargin(layerNumber, new Insets(8));
		StackPane layerIcon = UIHelper.buttonStack(e -> switchToLayer(layer), Optional.of(layerNumber),
				Optional.ofNullable(null), Pos.CENTER, false);
		HBox.setMargin(layerIcon, new Insets(8));
		UIHelper.setBackgroundColor(layerIcon, CustomColors.GREEN_100);
		myLayerPicker.getChildren().add(myLayerPicker.getChildren().size() - 1, layerIcon);
		layer.setOpacity(0.8);
		this.getChildren().add(layer);
		this.myLayers.add(layer);
		switchToLayer(layer);
	}

	/**
	 * This method is crucial to making sure that the points returned are
	 * accurate. Without this method, all the point data will be inaccurate.
	 * 
	 * @return insets that constrain to the image rather than the entire
	 *         ImageViewPane node.
	 * 
	 */
	private Insets getAdjustedInsets() {
		Insets LAYER_INSET = new Insets(8, 8, 72, 8);
		Tuple<Double, Double> xyInsets = myBackgroundView.getImageInsets();
		return new Insets(LAYER_INSET.getTop() + xyInsets.y, LAYER_INSET.getRight() + xyInsets.x,
				LAYER_INSET.getBottom() + xyInsets.y, LAYER_INSET.getLeft() + xyInsets.x);
	}

	/**
	 * Switches off all of the layers that aren't visible
	 * 
	 * @param layer
	 *            the layer that should be visible to the user
	 */
	private void switchToLayer(Layer layer) {

		myLayers.forEach(l -> {
			if (l == layer) {
				l.setOpacity(0.85);
				l.activate();
				getChildren().remove(layer);
				getChildren().add(layer);

			} else {
				l.setOpacity(0.15);
				l.deactivate();

			}
		});

	}

	private void setupLayerSelector() {
		// setup HBox
		myLayerPicker = new HBox();
		// add button that switches path maker
		StackPane.setAlignment(myLayerPicker, Pos.BOTTOM_CENTER);
		StackPane.setMargin(myLayerPicker, new Insets(8, 192, 8, 8));
		myLayerPicker.setMaxHeight(56);
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(myLayerPicker);
		// TODO scrollpane
		UIHelper.setBackgroundColor(myLayerPicker, CustomColors.GREEN);
		// add new layer button
		addNewLayerButton();
		this.getChildren().add(myLayerPicker);

	}

	private void addNewLayerButton() {
		ImageView img = new ImageView(new Image("add_icon_w.png"));
		img.setFitHeight(32);
		img.setPreserveRatio(true);
		StackPane button = UIHelper.buttonStack(e -> didClickNewLayerButton(), Optional.ofNullable(null),
				Optional.of(img), Pos.CENTER, true);
		UIHelper.setBackgroundColor(button, Color.TRANSPARENT);
		this.myLayerPicker.getChildren().add(button);
	}

	private void didClickNewLayerButton() {
		launchLayerPopup();
	}

	private void launchLayerPopup() {
		this.myLayerPopup = new LayerPopupView(this);
		this.myPopDelegate.openViewWithSize(myLayerPopup, PopupSize.SMALL);
	}

	private void setupButtons() {
		List<StackPane> panes = new ArrayList<>();

		ImageView backImage = makeImageFromString("undo_icon.png");
		StackPane b = UIHelper.buttonStack(
				e -> myLayers.stream().filter(layer -> layer.isActive()).findFirst().ifPresent(layer -> layer.undo()),
				Optional.ofNullable(null), Optional.of(backImage), Pos.CENTER, true);
		StackPane.setMargin(b, new Insets(12));
		panes.add(b);

		ImageView clearImage = makeImageFromString("clear_icon.png");
		StackPane c = UIHelper.buttonStack(
				e -> myLayers.stream().filter(layer -> layer.isActive()).findFirst().ifPresent(layer -> layer.clear()),
				Optional.ofNullable(null), Optional.of(clearImage), Pos.CENTER_RIGHT, true);
		StackPane.setMargin(c, new Insets(0, 72, 12, 0));
		panes.add(c);

		ImageView BGImage = makeImageFromString("background_icon.png");
		StackPane d = UIHelper.buttonStack(e -> toggleBackground(e), Optional.ofNullable(null), Optional.of(BGImage),
				Pos.CENTER_RIGHT, true);
		StackPane.setMargin(d, new Insets(0, 132, 12, 0));
		panes.add(d);

		for (StackPane s : panes) {
			s.setMaxSize(40, 40);
			StackPane.setAlignment(s, Pos.BOTTOM_RIGHT);
			UIHelper.setBackgroundColor(s, CustomColors.GREEN_100);
			this.getChildren().add(s);
		}
	}

	private ImageView makeImageFromString(String toMake) {
		ImageView IV = new ImageView(new Image(toMake));
		IV.setFitWidth(32);
		IV.setPreserveRatio(true);
		return IV;
	}

	private void toggleBackground(MouseEvent e) {
		e.consume();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Image File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
		if (selectedFile != null) {

			ConcreteFileHelper manager = new ConcreteFileHelper();
			// TODO copy file to images folder
			
			
			try {
				
				//Files.move(Paths.get(selectedFile.getAbsolutePath()), Paths.get(new File("images\\").getAbsolutePath()));
				
				manager.moveFile(selectedFile.getParent(), "images", selectedFile.getName());
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
			e1.printStackTrace();
				
			}
			//Image image=new Image(selectedFile.getName());
			Image image=new Image(selectedFile.toURI().toString());
			myBackgroundView.getImageView().setImage(image);
			myDisplayData.setBackgroundImagePath(selectedFile.getName());

		}
	}
	
	private void setBackground(String fileName){
		//TODO
	}

	/**
	 * This method updates the location of the points on the map This method is
	 * needed because the scaling of the points will change as the user resizes
	 * the map
	 */
	private void sizeDidChange() {
		for (Layer layer : myLayers) {
			StackPane.setMargin(layer, getAdjustedInsets());
		}
	}
	
	
	public BaseLayerView getBaseLayer(){
		return this.myBaseLayer;
	}

	/*
	 * LayerViewDelegate
	 * 
	 * @see
	 * ui.authoring.delegates.LayerViewDelegate#removeLayerView(ui.authoring.map
	 * .layer.Layer)
	 */
	@Override
	public void removeLayerView(Layer layerView) {
		// TODO Auto-generated method stub
		// this.getChildren().remove(layerView);
		// this.myLayers.remove(layerView);
		// this.myLayerPicker.getChildren().removeIf(filter);
	}

	/*
	 * LayerPopupDelegate
	 * 
	 * @see
	 * ui.authoring.map.LayerPopupDelegate#layerPopupDidPressConfirm(java.lang.
	 * String)
	 */
	@Override
	public void layerPopupDidPressConfirm(String nameInput) {
		LayerData data = new LayerData();
		PolygonLayerView layer = new PolygonLayerView(data);
		this.myMapData.addLayer(nameInput, data);
		this.addLayerView(layer, nameInput);
		myPopDelegate.closeView(myLayerPopup);
	}


	@Override
	public void layerPopupDidPressCancel() {
		myPopDelegate.closeView(myLayerPopup);
	}

}
