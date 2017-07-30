## Use Cases (Extra)

### Combination Use Case: Shoot Property

**1. User adds ShootTargetFar property to an actor in the authoring environment**

The user will the following variables for the ShootTargetFar property:
- Range (how far away the projectile can reach)
- Fire rate (how often a projectile is fired)
- Type (what will the actor shoot at)
- Projectile (what index of projectile will the actor shoot)
- Speed (how fast will the projectile move)

These will be saved in the form of a ShootTargetFarData in an ActorData representing that specific Actor.

**2. User subsequently removes ShootTargetFar property from that actor**

The ShootTargetFarData object to be removed will be passed into the ActorData. The actor data will iterate through its current data objects and remove the matching one, if it is present. The memory of this data object will be forgotten within the ActorData.

**3. User has added ShootTargetFar property to an actor in the game environment and now adds that actor in the game player (MOSES) **

When the user places the actor on the board, this actually instantiates the Actor via the ActorGenerator. As a result, the ShootTargetFarData object is passed to the PropertyFactory to create a ShootTargetFarProperty. This gives the Actor a property with shooting behavior (called via the action method).

**4. User presses play, the actor's target is NOT in range (MOSES) **

The Actor will "search" for a target and find none within range (a variable within property). As a result, it will do nothing.

**5. Target moves into range (MOSES)**

The Actor will "search" for a target and find one within range. It will then spawn a projectile within its action method and "send" that towards its target. This projectile actually becomes an actor itself. These projectiles will be spawned periodically, depending on the defined duration, until the target moves from within range. 

###Combination Use Case: Enemy waves

**6. User tries to add more than one of the same type of property to an actor**

In future implementations, we plan to have error checking for the types of properties added to an Actor. For instance, it doesn't make all that much sense to have an Actor have two types of health (E.g., LimitedHealth and ImmuneHealth). We will group properties/datas so that actors can only be given one of each property type. If another is added, it will replace the old one. This will be accomplished with a drop-down for each component type.

**7. User toggles preferences on side bar**

When the user changes preferences on the sidebar of the authoring environment, these are saved to a PreferenceData in the GameData object being created currently. For instance, if the user decides to forego enemy waves, then they can switch this to off and this will be stored as false in the preference data and be used in the GameController to decide how to place and create enemies on the board.


###Additional Use Cases

**8. User changes the theme color in the game (in the authoring environment).**

The user will be given control over the game look and feel in the second sprint. As a result, they will have a layout editor which will allow them to make changes to simple elements of the GUI. One of these elements will be color theme. We will have several stored color themes, indexed by number, which will be available to the user. They will be able to choose any one of these themes (by integer) to be loaded into the Game Player. 


**9.The user makes an Actor with no information in it.**

If the user defines no information for an ActorData, but still adds it to a game, the imagepath is defined via a default image and the basic information is filled with defaults (e.g., Jigglypuff with the Jigglypuff image). The ActorData is also automatically given a LimitedHealthData (where a HealthData object is required). 

**10. The user adds a new category of actor.**

When the user clicks the "+" button to add a new category, they are given the chance to choose a name (e.g., "Monster") for this new type of Actor. This new category is added to a list of BasicActorTypes, which can be addressed as a target type, an ally type, etc. In this authoring environment, this spawns a new editor dedicated to this category for creation of relevant actors.

**11. A pathed actor "walks" off of the grid.**

The actor will be processed depending on whether or not there is a base they should be dealing damage to before being filtered off of the grid. If so, the damage will be applied and the actor will be filtered off from the grid. 

**12. A user pauses the game.**

The controller will be given this information and halt the stepping of the actor grid. When the game is resumed, the grid will continue stepping through.

**13. An enemy is given shoot capabilities.**

An actordata is made which holds the actor ding the shooting as well as the type of projectile that the actor will be shooting. The type of projectile will contian information about the targeting of the projectile and it's damage.

**14. Enemy with set path is poisoned or confused.**

The move property is deactivated according to a timer from the controller before it can keep polling a new position from the list of its path coordinates. 

If the effect is to slow down the enemy at a specific rate, add into the list of path coordinates more coordnates with smaller increments (go through pair of points in the list and call path utility to get more increment points)

**15. Enemy with free movement.** 

A free movement property will take in the base coordinates and use path utility to get a list of random coordinates to go to but heading towards the base coordinates (assume user did not define any path in the authoring environment).

**16. Enemy waves contain many types of enemy, each type may take on one or many paths**

User specifies each wave with the types of enemy, the number of them, and one or more path options. If there is more than one path option, each enemy of that type will get assigned one randomly. 
 
**17. User tries to move a re-draggable object during the game**

For best results, the user should pause the game, move the object, and then continue. However, moving the object while playing will update its new location the same.

**18. Object is defeated**

As the map updates, the object will be removed from the screen. If object was in a path, the path will now be unobstructed.

**19. Adding new objects while the game is playing.**

User must select preferred object, drag and drop onto the screen, and then click secondarily to place the object into the back end grid.

**21. Removing objects during gameplay**

User drags the object off screen, removing it from both front end display and back end grid.

**22. Creating a new actor**

Using authoring environment, user can create new actor options by specifying information such as imagepath, and basic actor type. An XML file storing this info will be created and used to create an ActorData object, which can be used to instantiate this actor. 

**23. Creating/Designing a level**

In the authoring environment, user can specify their preferences (preferenceData), and design levels by creating enemy waves, which will be made into enemyWaveData objects and stored in LevelData objects that LevelController will use to spawn the enemy waves during gameplay.

**24. Saving an authored game**

The user has created a game, adding new Actors (in the form of ActorDatas), building levels (LevelDatas) and delineating settings. This all must be saved! This information is held in a GameData object, which is serialized. All of the components (e.g., fields in the data items) are stored for later reconstruction.

**25. Loading an authored game**

The user wants to load a previously authored game. To do this, they press the load button in the side menu to pull up the file directory. They choose their desired folder to restore, and open it. This file is then sent through the XMLParser and used to reconstruct the original GameData. This information is then dispersed to the components of the authoring environment (e.g., actors are put in the editors, all of the categories are laid out).

