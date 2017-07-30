package gameengine.controllers;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import builders.objectgen.ActorGenerator;
import gamedata.ActorData;
import gamedata.DisplayData;
import gamedata.GameData;
import gamedata.LineageData;
import gameengine.grid.ActorGrid;
import gameengine.grid.interfaces.ActorGrid.ReadableGrid;
import gameengine.grid.interfaces.controllergrid.ControllableGrid;
import gameengine.grid.interfaces.controllerinfo.GridHandler;
import gameengine.grid.interfaces.frontendinfo.FrontEndInformation;
import gameengine.handlers.LevelHandler;
import gamestatus.GameStatus;
import gamestatus.WriteableGameStatus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import types.BasicActorType;
import ui.handlers.AnimationHandler;
import ui.handlers.LoginHandler;
import ui.handlers.UIHandler;
import ui.player.inGame.GameScreen;
import ui.player.inGame.SimpleHUD;
import ui.player.listener.ListenQueue;
import ui.player.listener.SceneListen;
import ui.player.users.InitialGameStatus;
import ui.player.users.WriteableUser;
import util.GameObjectUtil;
import util.InsufficientMoneyException;
import util.LayerNotPlaceableException;
import util.VoogaException;
import util.observerobservable.VoogaObserver;
/**
 * GameController is the controller layer between the front end display and the back end game engine
 * Implements UIHandler and initializes all necessary back end and front end components of game engine, as well as the Timeline
 * @author sarahzhou
 *
 */
public class GameController {
	private Timeline animation;
	
	private GameData myGameData;
	
	private GameStatus myGameStatus;
	
	private UIHandler myUIHandler;
	private AnimationHandler myAnimationHandler;
	private LevelHandler myLevelHandler;
	private GridHandler myGridHandler;
	
	private GameLevelController myLevelController;
	
	private ControllableGrid myGrid;
	
	private SceneListen mySceneListen;
	
	private GameObjectUtil myGameObjectUtil;
	
	private InitialGameStatus myInitGameStatus;
	
	private GameScreen myGameScreen;
	private SimpleHUD mySimpleHUD;
	
	private final int MAX_X = 1;
	private final int MAX_Y = 1;
	
	private static int INIT_EXP = 0;
	
	private Function<BasicActorType,Integer> actorCounts;
	
	private final double MILLISECOND_DELAY=17;
	
	public GameController(GameData gameData,LoginHandler loginHandler) {
		myGameData = gameData;
		myGameObjectUtil = new GameObjectUtil();
		initializeUIHandler();
		initializeAnimationHandler();
		initializeGridHandler();
		initializeLevelHandler();
		initializeInitialGameStatus();
		setupGameStatus(loginHandler.getActiveUser(),myInitGameStatus);
		setUpGameScreen(loginHandler);
		myGrid = getNewActorGrid(myGameScreen);
		myLevelController = new GameLevelController(myLevelHandler,myGameData,myGameStatus);
	}
	
	private void setUpGameScreen(LoginHandler loginHandler) {
		System.out.println(myUIHandler.getDisplayData().getBackgroundImagePath());
		myGameScreen = new GameScreen(loginHandler,myUIHandler,myAnimationHandler,() -> mySimpleHUD);
		myGameScreen.setAnimationHandler(myAnimationHandler);
		myGameScreen.setSong(myGameData.getPreferences().getMusicFilePath()); //set music for game
	}
	
	private Function<BasicActorType,Integer> getCounts(ReadableGrid grid) {
		return (target) -> { 
			return grid.getActorLocations(target).size();
		};
	}
	
	/**
	 * @param UIObserver
	 * @return a new clean instance of ActorGrid
	 */
	private ActorGrid getNewActorGrid(VoogaObserver<Map<Integer,FrontEndInformation>> UIObserver) {
		ActorGrid actorGrid = new ActorGrid(MAX_X,MAX_Y,myGridHandler,
				i -> ActorGenerator.makeActor(i,myGameData.getOption(i)));
		actorCounts = getCounts(actorGrid);
		actorGrid.addObserver(UIObserver);
		return actorGrid;
	}
	
	private void setupGameStatus(WriteableUser writeableUser,InitialGameStatus initialGameStatus) {
		mySimpleHUD = new SimpleHUD();
		myGameStatus = new GameStatus(writeableUser,initialGameStatus);
		myGameStatus.addObserver(mySimpleHUD);
	}
	
	public void start(Stage stage,double width, double height, Paint fill) {
		setScene(stage,width,height,fill);
		intitializeTimeline();
	}
	
	private void setScene(Stage stage,double width, double height, Paint fill) {
		Scene myScene = new Scene(myGameScreen,width,height,fill);
		mySceneListen = new SceneListen(myScene); 
		stage.setScene(myScene);
	}
	
	private void intitializeTimeline() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				d -> step());
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
	}
	
	private void step() {
		myLevelController.update();
		mySceneListen.pollQueue();
		myGrid.step();
	}
	
	private void initializeGridHandler() {
		myGridHandler = new GridHandler() {
			@Override
			public WriteableGameStatus getWriteableGameStatus() {
				return myGameStatus;//myWriteableGameStatus;
			}

			@Override
			public ListenQueue getEventQueue() {
				return mySceneListen.getQueue();
			}
		};
	}
	
	private void initializeAnimationHandler() {
		myAnimationHandler = new AnimationHandler() {
			@Override
			public void pause() {
				animation.pause();
			}
			@Override
			public void play() {
				animation.play();
			}
			@Override
			public void stop() {
				animation.stop();
			}
			@Override
			public void exit() {
				System.exit(0);
			}
		};
	}
	
	private void initializeInitialGameStatus() {
		myInitGameStatus = new InitialGameStatus() {

			@Override
			public Optional<Integer> getInitMoney() {
				return Optional.of(0);
			}

			@Override
			public Integer getInitLives() {
				return myGameData.getPreferences().getNumLives();
			}
		};
	}

	private void initializeUIHandler() {
		myUIHandler = new UIHandler() {
			@Override
			public void deleteGameObject(int id) {
				myGameObjectUtil.deleteGameObject(id, myGrid);
			}
			@Override
			public void updateGameObjectType(int id, Integer currentOption) throws InsufficientMoneyException {
				myGameObjectUtil.updateGameObjectType(id, myGameData.getMappedLineageData().get(currentOption), myGrid, myGameData,myGameStatus);
			}
			@Override
			public void updateGameObjectLocation(int id, double xRatio, double yRatio) throws VoogaException {
				myGameObjectUtil.updateGameObjectLocation(id, xRatio, yRatio, myGrid);
			}
			@Override
			public int addGameObject(Integer option, double xRatio, double yRatio) throws LayerNotPlaceableException, InsufficientMoneyException{
				return myGameObjectUtil.addGameObject(option, xRatio, yRatio, myGameData, myGameStatus, myGrid);
			}
			@Override
			public Map<Integer, ActorData> getOptions() {
				return myGameData.getOptions();
			}
		
			public DisplayData getDisplayData(){
				return myGameData.getDisplayData();
			}
			@Override
			public void changeLevel(int level) throws VoogaException {
				myLevelController.changeLevel(level);
			}
			
			public void launchGame() throws VoogaException {
				myLevelController.changeLevel(1);
			}
			@Override
			public Map<Integer,LineageData> getLineageData() {
				return myGameData.getMappedLineageData();
			}
		};
	}
	
	private void initializeLevelHandler() {
		myLevelHandler = new LevelHandler() {

			@Override
			public ControllableGrid getMyGrid() {
				return myGrid;
			}

			@Override
			public void displayWinAlert() {
				animation.stop();
				myGameScreen.notifyWin();
			}

			@Override
			public void levelUp() {
				myGameStatus.levelUp();
			}

			@Override
			public void displayLoseAlert() {
				animation.stop();
				myGameScreen.notifyLose();
			}

			@Override
			public Function<BasicActorType, Integer> actorCounts() {
				return actorCounts;
			}
			
			public BasicActorType getBasicActorTypeEnemy() {
				return myGameObjectUtil.getBasicActorEnemyType(myGameData);
			}
			
		};
	}
	
	
	
}