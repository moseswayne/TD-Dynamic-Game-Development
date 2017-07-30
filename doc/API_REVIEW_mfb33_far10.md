#API Design Review

**Maddie Briere (mfb33)**

**Faith Rodriguez (far10)**

##Part 1

**1. What about your API/design is intended to be flexible?**

The Data structures built in the backend are meant to represent any type of property taken on by an Actor. Using these Data structures, the authoring environment can "mix and match" any number of Data objects to represent a new actor, allowing for infinite possibilities and little need to extend to accommodate new actor types. 

**2. How is your API/design encapsulating your implementation decisions?**

A) The frontend only has knowledge of the data, not the logic, that will be used in the game. They create a GameData object that can be added to the GameController, along with game logic, to build a working game.

B) The integer representing an ActorData as an option is the only information (including basic stuff like the name and imagepath) that other parts of the program have access to. They can "order" an Actor corresponding to some number, but never have knowledge of how these orders are matched up or how they are translated into Actors.

**3. How is your part linked to other parts of the project?**

My part is actually linked to every other part of the game. Most notably, the GameData object is a communicator between the Game Authoring Environment and the Game Player. The authoring environment builds this GameData to represent the game they want, this is saved via XML, and then reloaded in the GamePlayer to fill all of the necessary values.

The Factory is used in the GamePlayer/Controller. The GamePlayer can make an order to the GameEngine, which will then invoke the factory to fulfill that request.

**4. What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?**

I don't anticipate many possible errors in this part of the game because the front and back end are only given very restricted ways to communicate with the GameData. Perhaps not initializing some of the values could cause problems, but these will soon be replaced by default values (e.g., use of Optionals).

**5. Why do you think your API/design is good (also define what your measure of good is)?**

This design is good because it separates the front and backend and limits interactions to a single piece of information (the GameData). This separation gives the frontend control over the data, and makes the work done in the game authoring environment savable and loadable for use in the GamePLayer.

##Part 2

**1. What feature/design problem are you most excited to work on?***

I'm most excited about the factory. I love factories! I built an abstract factory for the last project and am using it again in this project to facilitate my work.

**2. What feature/design problem are you most worried about working on?**

I am most worried about how the Data objects must match up to the Property objects -- I think this could cause bugs that may be hard to find.

**3. What major feature do you plan to implement this weekend?**

I plan to implement the LevelFactory, which generates LevelControllers from the current data.This will require careful review of the possible preferences and possible refactoring. 

**4. Discuss the use cases/issues created for your pieces: are they descriptive, appropriate, and reasonably sized?**

The use cases range across many different options (in the game engine, path, player, etc.) and are appropriately sized to small possible use cases. For instance, the following use case can be found in our USE_CASES.md file:

*Game Engine: The type of movement/attack for an enemy changes*

This is a reasonably small and descriptive use case that defines a case in which many components must interact. The GamePlayer will register the change, pass this on to the GameEngine, which will then make modifications. 

**5. Do you have use cases for errors that might occur?**

Example use cases:

A) *User creates an invalid level (ie. path never reaches base):*
Backend of game authoring environment is able to detect that the path created does not reach the base and accordingly throws an alert informing the user that he/she has created an invalid level and explains why the level created is invalid. The user can then hit “OK” to finish creating a valid level. 

B) *User tries to load game from bad file:*
Game selector attempts to launch the game by loading the corresponding files, but an exception is thrown. This is reported back to the user as an error message inside a dialog box.