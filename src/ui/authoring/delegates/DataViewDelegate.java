package ui.authoring.delegates;

import gamedata.compositiongen.Data;
import ui.authoring.actor.DataView;

public interface DataViewDelegate {
	public abstract void setData(Data newData);
	public abstract void didClickDelete(DataView dataView);
}
