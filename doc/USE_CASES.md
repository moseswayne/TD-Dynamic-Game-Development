##**Team "I Love Singletons" - Duvall, probably** Use Cases

###Use Cases:

1. **User selects new game from game selection menu**
Game selector attempts to launch the game by loading the corresponding files. The controller of the desired game is initialized, and this brings up the window for the game.

2. **User tries to load game from bad file**
Game selector attempts to launch the game by loading the corresponding files, but an exception is thrown. This is reported back to the user as an error message inside a dialog box.

3. **User tries to quit to game selection menu while game is playing**
Game first prompts the user as to whether or not they actually want to quit with a dialog box (they will lose their progress otherwise). If “cancel” option is selected, do nothing; if “OK” is selected, close the game window and return to the game selector.

4. **User tries to quit to game main menu while playing (not the game selection menu)**
Game first prompts the user as to whether or not they actually want to quit with a dialog box (they will lose their progress otherwise). If “cancel” option is selected, do nothing; if “OK” is selected, end the current level and return to the main menu screen of the game.

5. **Adding new tower while the level is running**
Menu item in tower menu registers a click, and sets the action of the next click to place a tower if the click location is a valid tower location. On next click, check the mouse position with the grid to see if it is a valid tower location. If it is, instantiate a tower object within the grid; if not, do nothing.

6. **User wants to clear history of current scores**
Scores are saved to an XML file. When the user tries to clear the scores, a dialog box prompt asks the user whether or not they actually want to do this. If cancel is pressed, do nothing. If OK is pressed, wipe the XML file.

7. **User wants to load in a previous game**
Load option is selected from the game’s options menu. A filechooser pops up, allowing the user to select the save file. The controller for the game then opens the file and reads the game state from the file, updating the view and model as necessary.

8. **User wants to save the current game**
Save option is selected from the game’s options menu. The user is prompted for a filename for the save file. The controller then reads the game state from the view and model, then writes this to the file. 

9. **User creates an invalid level (ie. path never reaches base)**
Backend of game authoring environment is able to detect that the path created does not reach the base and accordingly throws an alert informing the user that he/she has created an invalid level and explains why the level created is invalid. The user can then hit “OK” to finish creating a valid level. 

10. **User wants to delete a created level in the game authoring environment**
The user selects the level from the level chooser at the top of the window to edit that level. The user then clicks the trash can icon in the top right of the window to delete the level. An alert the pops up asking if the user is sure that the level should be deleted, with two options, “OK” and “No”. If the user hits “OK”, the level is no longer shown in the level chooser and is deleted in the list of levels in the backend so the XML generator does not include that level. 

11. **Changing path during game**
Choose menu item for editing path which pauses the simulation. The user can then add to the path by clicking and dragging, or delete from the path (if enemies are not there). The backend then checks to ensure that the path is valid (still connects to the base), then changes the grid on the backend accordingly, then the simulation continues. 

12. **User wants to create a new level while playing the game**
Choose menu item for adding new level. Backend saves the current state of the game as XML file. User is taken to game authoring environment to create/edit levels. User clicks “Resume Game,” when done making changes. Backend resumes game from saved XML file with changes from the game authoring environment. 

13. **Adding a tower that alters the troop path during the game:**
The same use case for when a tower is added from the the menu item is used to determine the type of tower selected and where it is placed. The game map determines if the position chosen for the tower prevents a possible path to the base and disallows the addition of the tower if this is the case. Troop units no longer recognize that the position to which the tower was added as a valid position to move toward. 
The type of tower and path must be specified as possible within the game authoring environment to allow for such functionality in game.

14. **Tower fires projectile during the game:**
Towers in the game “fire” projectiles at troops automatically during game play. Each tower has a “fire rate”, a component that determines the rapidity at which towers spawn projectiles toward troops. When a tower releases a projectile, it calculates the direction in which to fire the projectile, spawning a new projectile by cloning an instance of it. The projectile then interacts with its environment accordingly. 

15. **Upgrading a tower:**
The player will be able to upgrade a tower. There will be a variety of options to upgrade from. For example, the tower upgrade could extend the tower range. Another upgrade could be increasing the fire rate and or projectile speed. Upgrading a tower we be able to be run both during as well as between levels. 

16. **Game playing: You earn experience for destroying an enemy troop or winning a level:**
The GamePlayer recognizes that an experience earning event as happened, which it then registers as a change in the GameStatus. If the user has ascended a level, the GameStatus updates, allowing the menu item to add any additional unlocked towers.

17. **Enemy troop moves into a destroyable tower on a path:**
The troop registers the block to the path from the tower and receives information from the tile path that the only direction to move is forward. The troop then begins to attack the destroyable tower during each step depending on its rate of attack. The troop will attack until it is removed or the tower is destroyed, similar to in plants vs zombies. 

18. **Picking the number of enemies per level:**
In the authoring environment, the author will be able to select the number of enemies that will appear in every level. For the beginner levels, there could be a low number of enemies and the number could increase with every subsequent level. 

19. **Game playing: The base is destroyed:**
If the base is destroyed by the enemies, then the game would end. A splash screen would open then be open from which the player could decide to load a previous save, start from scratch, reload their last game play, or exit the game. 

20. **Game playing: You win a level:**
You win a level! You can edit your map (adding towers, upgrading, selling towers, etc). You can then start the next level whenever you want. If it was the last level, it will say congratulations and allow you to restart the last game, start a new game, or exit. 

21. **Authoring: You create a new tower**
The user must select an image to use for the tower and for the projectile. Then he/she must enter the values of its specifications like power, projectile type, range

22. **Authoring: You add a new upgrade to a tower**
The user can select an existing tower and choose to upgrade it. The rest of the upgrade process is the same as the previous step.

23. **Authoring: You create a new type of tile for the map**
The user selects an image and a tile type. Then this tile can be selected and added to the map by clicking the GridPane’s cells.

24. **Authoring: You design a map**
...by placing tiles on the grid’s cells. The user can select from the tiles he has created or from the default tiles that are already there. 
The user must enter a valid path. 

25. **Authoring: You add a new enemy**
The user is prompted to select an image file for the enemy. Then the user is prompted to type in the specific properties of the enemy. 
The user then must choose an upgrade function for the enemy. He can choose from linear, quadratic, and exponential.

26. **Authoring: Add a new level**
Adding a new level saves the current level to the list of levels. The new level will be a copy of the current level. The user can then edit the new things for that level. For example, making the enemies harder by increasing the number of enemies and their health points.

27. **Authoring: Save changes you’ve made into an XML file**
Pass the Data instance of the Game Objects you have altered into an XMLFactory method that will generate an XML file. FileBrowser window will prompt user to save the XML in desired file location.

28. **Authoring: Load an existing Game file**
XMLFactory has a method that takes in an XML file and returns GameData instance with the properties/data specified in the XML file

29. **GamePath: user adds a tower in the game while the enemies are moving**
The getDirection method in PathFinder class recomputes the number of towers along the path that it is on. If the number of towers in that path increases due to the newly added tower then the pathfinder will compare that path to other paths and choose the direction corresponding to the path with minimum number of towers. 

30. **GamePath: when the map allows for free movements ( no predetermined set of paths by the game designer)  (field runner game)**
The PathFinder just finds the direction to the path that has the minimum number of tiles in the beginning, but will change path if towers are added along that path. 

31. **GamePath:  game with multiple starts, multiple ends.**
Given a current tile, the pathfinder will loop through every ending tiles, and find the path from the current tile to an ending tile that has the minimum number of towers involved. 

32. **GamePath: smart and dumb enemies**
For dumb enemies, pathfinder would just choose a random viable path to go, not accounting for the number of towers along its path. For smart enemies as an upgrade for enemy, pathfinder would find the best path. 

33. **Game Engine: The user wins a level**
The GameStatus object within the GameController, which keeps track of game-specific details, will update the current level. It will then look into the GameData object to retrieve the corresponding next LevelData. If one does not exist, the game is “won.” Otherwise, the GameController passes on the new LevelData to the LevelFactory to get back a LevelController for use on the current level. This becomes the new current LevelController.

34. **Game Engine: The user gains experience**
The GameStatus object within the GameController, which keeps track of game-specific details, will update the current experience according to the new input. This data will be kept in the GameStatus object for the duration of the GameController’s lifetime and will not be changed by level.

35. **Game Engine: The type of movement/attack for an enemy changes**
The ActorHandler collection in the GameController keeps track of mappings between Actors and Decisions (or reactions to updates). When the type of action changes for an actor, this mapping must be altered -- the old Actor is kept, but a new Decision is added to this mapping.

36. **Game Engine: An actor is removed game play**
The ActorHandler collection will remove the Actor/Decision pairing from its Collection following the update. This will require use of the active/inactive marker on the Actors to mark an Actor  “to be removed” without removing it while iterating through all of the Actors (which would throw an error). 

37. **Game Engine: A dumb enemy becomes smart**
ActorHandler updates mapping of Actor to smart Decision (smart Pathfinder) rather than the dumb Decision

38. **Game Engine: An enemy dies after HP=0**
The enemy will be marked inactive and after finishing the iteration through the ActorHandler collection, the enemy will be removed as specified in Use Case 36

39. **Game Engine: User adds actor during game play**
An ActorHandler will be be added to the collection. The front end View instance of the Actor will be added to the scene. 

40. **Game Engine: Update Game status**
GameController calls GameStatus object’s set methods to update status
