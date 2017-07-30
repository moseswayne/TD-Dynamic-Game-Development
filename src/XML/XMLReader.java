package XML;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import builders.objectgen.DataGenerator;
import gamedata.ActorData;
import gamedata.BasicData;
import gamedata.EnemyInWaveData;
import gamedata.GameData;
import gamedata.LevelData;
import gamedata.PathData;
import gamedata.ProjectileData;
import gamedata.ProjectileType;
import gamedata.StringToFieldFactory;
import gamedata.WaveData;
import gamedata.compositiongen.Data;
import gamedata.compositiongen.HealthData;
import gamedata.reflections.Reflections;
import gameengine.grid.classes.Coordinates;
import gameengine.grid.interfaces.Identifiers.Grid2D;
import types.BasicActorType;

public class XMLReader extends XMLParser {
	
	private GameData myGameData;
	private Map<Integer, List<Grid2D>> pathData;
	private List<ActorData> myActors;
	private static final DocumentBuilder DOCUMENT_BUILDER = getDocumentBuilder();

	public XMLReader(File data) {
		super(data);
		myGameData = new GameData();
		generateData();
	}

	public XMLReader(String fileName) {
		this(new File(fileName));
	}

  

	private void generateData() {
		getPathData();
		try {
			getActorData();
		} catch (Exception e) {
			throw new XMLException(e);
		}
		getLevelData();
		//getProjectileData();

	}
	public GameData getData(){
		return this.myGameData;
	}
	private void getPathData() {
		Element root = getRootElement(dataFile);
		Element allPaths = (Element) root.getElementsByTagName("PathData").item(0);
		NodeList paths = allPaths.getElementsByTagName("Path");
		pathData = new HashMap<Integer, List<Grid2D>>();
		for (int i = 0; i < paths.getLength(); i++) {
			String[] xCoors = getTextValue((Element) paths.item(i), "X").split(" ");
			String[] yCoors = getTextValue((Element) paths.item(i), "Y").split(" ");
			if (xCoors.length != yCoors.length) {
				// throw new XMLException();
			}
			List<Grid2D> path = new ArrayList<Grid2D>();
			for (int j = 0; j < xCoors.length; j++) {
				Coordinates coor = new Coordinates(Double.parseDouble(xCoors[j]), Double.parseDouble(yCoors[j]));
				path.add(coor);
			}
			pathData.put(i, path);
		}

		myGameData.setMyPaths(new PathData(pathData));

	}

	private void getActorData() throws ClassNotFoundException, IOException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<Data>[] dataTypes = Reflections.getClasses("gamedata.composition");
		myActors = new ArrayList<ActorData>();
		Element root = getRootElement(dataFile);
		Element allActors = (Element) root.getElementsByTagName("ActorData").item(0);
		NodeList actors = allActors.getElementsByTagName("Actor");

		for (int i = 0; i < actors.getLength(); i++) {
			Element actor = (Element) actors.item(i);
			String type = getTextValue(actor, "type");
			BasicActorType basicType = new BasicActorType(type);
			String imagePath = getTextValue(actor, "imagePath");
			String name = getTextValue(actor, "name");
			BasicData basicData = new BasicData(name, imagePath);
			HealthData healthData = null;
			List<Data> data = new ArrayList<Data>();
			for (Class c : dataTypes) {

				String field = c.getName().split("\\.")[c.getName().split("\\.").length - 1];
				Optional<String> fieldData = Optional.ofNullable(getTextValue(actor, field));
				if (fieldData.isPresent() && fieldData.get().length() > 0) {
					String[] fields = fieldData.get().split(" ");
					Constructor<?>constr=null;
					Constructor<?>[] constructors = c.getConstructors();
					for(Constructor<?>current:constructors){
						if(current.getParameterTypes().length==fields.length){
							constr=current;
						}
					}
					Class[] neededFields = constr.getParameterTypes();

					Object[] convertedFields = new Object[neededFields.length];
					for (int j = 0; j < neededFields.length; j++) {
						convertedFields[j] = StringToFieldFactory.getObject(fields[j], neededFields[j]);
						
					}
				
					Object o = DataGenerator.makeData(field.substring(0, field.length() ), convertedFields);
					if (o instanceof HealthData) {
						healthData = (HealthData) o;
					
					} else {
						data.add((Data) o);
						
					}

				}
			}
			Data[] dataArray = new Data[data.size()];
			for (int k = 0; k < data.size(); k++) {
				dataArray[k] = data.get(k);
			}
			ActorData newActor = new ActorData(basicType, basicData, healthData, dataArray);
			myActors.add(newActor);
			myGameData.add(newActor);
		}
	}

	private void getLevelData() {
		Element root = getRootElement(dataFile);
		Element allWaves = (Element) root.getElementsByTagName("LevelData").item(0);
		double duration = Double.parseDouble(getTextValue(allWaves, "Duration"));
		double difficulty = Double.parseDouble(getTextValue(allWaves, "Difficulty"));
		Optional<Double> healthMultiplier = getOptionalDouble(getTextValue(allWaves, "HealthMultiplier"));
		Optional<Double> speedMultiplier = getOptionalDouble(getTextValue(allWaves, "SpeedMultiplier"));
		Optional<Double> attackMultiplier = getOptionalDouble(getTextValue(allWaves, "AttackMultiplier"));
		LevelData levelData = new LevelData(duration);
				NodeList waves = allWaves.getElementsByTagName("Wave");
		for (int i = 0; i < waves.getLength(); i++) {
			Element wave = (Element) waves.item(i);
			NodeList enemies = wave.getElementsByTagName("Enemy");
			WaveData waveData = new WaveData();
			for (int j = 0; j < enemies.getLength(); j++) {
				Element enemy = (Element) enemies.item(j);
				Integer actorID = Integer.parseInt(getTextValue(enemy, "ActorID"));
				Integer number = Integer.parseInt(getTextValue(enemy, "Number"));
				
				EnemyInWaveData waveEnemy = new EnemyInWaveData(myActors.get(actorID), number);
				 waveData.addWaveEnemy(waveEnemy);
				
			}
			 levelData.addWave(waveData);
			// when multiple waves get implemented
		}
		myGameData.addLevel(levelData, 0);
	}

//	private void getProjectileData() {
//		Element root = getRootElement(dataFile);
//		Element allProjectiles = (Element) root.getElementsByTagName("ProjectileData").item(0);
//		NodeList projectiles = allProjectiles.getElementsByTagName("Projectile");
//		ProjectileData projectileData=new ProjectileData(new HashMap<Integer,ProjectileType>());
//		for(int i=0;i<projectiles.getLength();i++){
//			Element projectile=(Element)projectiles.item(i);
//			String image=getTextValue(projectile,"Image");
//			Double damage=Double.parseDouble(getTextValue(projectile,"Damage"));
//		
//			Boolean explosive=Boolean.valueOf(getTextValue(projectile,"Explosive"));
//			Boolean restrictive=Boolean.valueOf(getTextValue(projectile,"Restrictive"));
//			ProjectileType newProjectile=new ProjectileType(image,damage,explosive,restrictive);
//		projectileData.addProjectile(newProjectile);
//		System.out.println(newProjectile.getDamage());
//		}
//		myGameData.add(data);
//	}

	private Optional<Double> getOptionalDouble(String s) {
		try {
			return Optional.ofNullable(Double.parseDouble(s));
		} catch (NumberFormatException e) {
			return Optional.ofNullable(null);

		}
	}


	public static void main(String[] args) throws ClassNotFoundException, IOException {
	ArrayList<String>sd=new ArrayList<String>();
	sd.add("dsad");
	sd.add("ds");
	ArrayList<String>ds=new ArrayList<String>();
	ds.addAll(sd);
	sd.add("dasdasdsa");
	System.out.println(sd);
	System.out.println(ds);
		

	}
}
