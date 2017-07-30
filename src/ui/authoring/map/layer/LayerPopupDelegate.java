package ui.authoring.map.layer;

public interface LayerPopupDelegate {
	public abstract void layerPopupDidPressConfirm(String nameInput);
	public abstract void layerPopupDidPressCancel();
}
