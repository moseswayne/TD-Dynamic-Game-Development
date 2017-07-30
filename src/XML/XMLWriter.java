package XML;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gamedata.ActorData;
import gamedata.BasicData;
import gamedata.EnemyInWaveData;
import gamedata.GameData;
import gamedata.LevelData;
import gamedata.WaveData;
import gamedata.compositiongen.Data;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import util.Tuple;

public class XMLWriter {
	private GameData myGameData;
	public static final String TYPE_ATTRIBUTE = "type";
	private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();

	public XMLWriter(GameData gameData) {
		myGameData = gameData;
		writeXML();
	}

	private void writeXML() {
		Document doc = DOCUMENT_BUILDER.newDocument();
		Element root = doc.createElement(TYPE_ATTRIBUTE);
		doc.appendChild(root);
		setAttributes(root, doc);
		setPathData(root, doc);
		setActorData(root, doc);
		setLevelData(root, doc);
		// setProjectileData(root,doc);
		printFile(doc);
	}

	private void setPathData(Element root, Document doc) {
		Element pathData = doc.createElement("PathData");
		for (Integer i : myGameData.getPathOptions().keySet()) {
			Element path = doc.createElement("Path");
			myGameData.getPathOptions().get(i);
			Tuple<List<Double>, List<Double>> coorLists = extractCoordinates(myGameData.getPathOptions().get(i));
			Element x = doc.createElement("X");
			Element y = doc.createElement("Y");
			x.appendChild(doc.createTextNode(listToString(coorLists.x)));
			y.appendChild(doc.createTextNode(listToString(coorLists.y)));
			path.appendChild(x);
			path.appendChild(y);
			pathData.appendChild(path);

		}
		root.appendChild(pathData);
	}

	private void setActorData(Element root, Document doc) {
		Element actorData = doc.createElement("ActorData");
		for (int i = 0; i < myGameData.getNumOptions(); i++) {
			ActorData current = myGameData.getOption(i);
			Element actor = doc.createElement("Actor");
			Element type = doc.createElement("type");
			type.appendChild(doc.createTextNode(current.getType().getType()));
			BasicData currentBasic = current.getBasic();
			Element name = doc.createElement("Name");
			name.appendChild(doc.createTextNode(currentBasic.getName()));
			Element imagePath = doc.createElement("imagePath");
			imagePath.appendChild(doc.createTextNode(currentBasic.getImagePath()));
			actor.appendChild(type);
			actor.appendChild(name);
			actor.appendChild(imagePath);
			List<Data> dataList = new ArrayList<Data>(current.getMyData());
			dataList.add(current.getHealth());
			for (Data d : dataList) {
				Element dataElement = doc.createElement(
						d.getClass().toString().split("\\.")[d.getClass().toString().split("\\.").length - 1]);
				//Map<String, Object> fields = OptionGenerator.getFields(d);
				Map<String, Object> fields = new HashMap<String, Object>();
				ArrayList<Object> fieldStrings = new ArrayList<Object>();
				for (String s : fields.keySet()) {
					fieldStrings.add(fields.get(s));
				}
				String fieldString = listToString(fieldStrings);
				dataElement.appendChild(doc.createTextNode(fieldString));
				actor.appendChild(dataElement);
			}
			actorData.appendChild(actor);

		}
		root.appendChild(actorData);
	}

	private void setLevelData(Element root, Document doc) {
		Element levelData = doc.createElement("LevelData");
		LevelData myLevelData = myGameData.getLevel(0);
		Element healthMultiplier = doc.createElement("HealthMultiplier");
		healthMultiplier.appendChild(doc.createTextNode(Double.toString(myLevelData.getHealthMultiplier())));
		Element speedMultiplier = doc.createElement("SpeedMultiplier");
		speedMultiplier.appendChild(doc.createTextNode(Double.toString(myLevelData.getSpeedMultiplier())));
		Element attackMultiplier = doc.createElement("AttackMultiplier");
		attackMultiplier.appendChild(doc.createTextNode(Double.toString(myLevelData.getAttackMultiplier())));
		Element difficulty = doc.createElement("Difficulty");
		//difficulty.appendChild(doc.createTextNode(Double.toString(myLevelData.getDifficulty())));
		Element duration = doc.createElement("Duration");
		duration.appendChild(doc.createTextNode(Double.toString(myLevelData.getDuration())));
		levelData.appendChild(healthMultiplier);
		levelData.appendChild(speedMultiplier);
		levelData.appendChild(attackMultiplier);
		levelData.appendChild(duration);
		levelData.appendChild(difficulty);

		List<WaveData> waves = myLevelData.getMyWaves();
		for (WaveData currentWave : waves) {
			Element wave = doc.createElement("Wave");
			List<EnemyInWaveData> enemies = currentWave.getWaveEnemies();
			for (EnemyInWaveData enemy : enemies) {
				Element enemyElement = doc.createElement("Enemy");
				for (Integer i : myGameData.getOptions().keySet()) {
					if (enemy.getMyActor().equals(myGameData.getOption(i))) {
						Element actorID = doc.createElement("ActorID");
						actorID.appendChild(doc.createTextNode(i.toString()));
						enemyElement.appendChild(actorID);
					}

				}

				Element number = doc.createElement("Number");
				//number.appendChild(doc.createTextNode(Integer.toString(enemy.getMyNumber())));
				enemyElement.appendChild(number);

				wave.appendChild(enemyElement);
			}

			levelData.appendChild(wave);
		}
		root.appendChild(levelData);

	}

	// private void setProjectileData(Element root,Document doc){
	// Element projectileData=doc.createElement("ProjectileData");
	// for(Integer i:myGameData.getMyProjectiles().getMyProjectiles().keySet()){
	// Element projectile=doc.createElement("Projectile");
	// ProjectileType
	// proj=myGameData.getMyProjectiles().getMyProjectiles().get(i);
	// Element damage=doc.createElement("Damage");
	// damage.appendChild(doc.createTextNode(Double.toString(proj.getDamage())));
	// System.out.println(proj.getImagePath());
	// Element image=doc.createElement("Image");
	// image.appendChild(doc.createTextNode(proj.getImagePath()));
	// Element explosive=doc.createElement("Explosive");
	// explosive.appendChild(doc.createTextNode(Boolean.toString(proj.isExplosive())));
	// System.out.println(proj.isExplosive());
	// Element restrictive=doc.createElement("Restrictive");
	// restrictive.appendChild(doc.createTextNode(Boolean.toString(proj.isRestrictive())));
	// projectile.appendChild(damage);
	// projectile.appendChild(image);
	// projectile.appendChild(explosive);
	// projectile.appendChild(restrictive);
	// projectileData.appendChild(projectile);
	// }
	// root.appendChild(projectileData);
	// }
	private void printFile(Document doc) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {

			throw new XMLException(e);
		}
		DOMSource source = new DOMSource(doc);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select location to save XML file");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter(".xml files", "*.xml"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		Result result = null;
		// result = new StreamResult(fileChooser.showSaveDialog(new Stage()));
		result = new StreamResult(new File("ss.xml"));
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			throw new XMLException(e);
		}
	}

	private Tuple<List<Double>, List<Double>> extractCoordinates(List<Grid2D> list) {
		List<Double> xs = new ArrayList<Double>();
		List<Double> ys = new ArrayList<Double>();
		for (Grid2D coor : list) {
			xs.add(coor.getX());
			ys.add(coor.getY());
		}
		return new Tuple<List<Double>, List<Double>>(xs, ys);
	}

	private String listToString(List<?> list) {
		List<String> stringList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			stringList.add(list.get(i).toString());
		}

		return String.join(" ", stringList);
	}

	private void setAttributes(Element root, Document doc) {

		Attr type = doc.createAttribute(TYPE_ATTRIBUTE);
		type.setValue("GameData");
		root.setAttributeNode(type);
	}

	private static DocumentBuilder getDocumentBuilder() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new XMLException(e);
		}
	}

	public static void main(String[] args) {
		XMLReader you = new XMLReader("data/voogatest.xml");
		GameData d = you.getData();
		new XMLWriter(d);
	}
}
