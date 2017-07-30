**Team 1 (I love Singletons)**
Vishnu Gottiparthy, vtg4

##Part 1

1. What about your API/design is intended to be flexible?
Making a new type of game is all just setting new rules in the editor. This allows for making multiple types of tower defense games.

2. How is your API/design encapsulating your implementation decisions?
Separating into hard backend vs. frontend. Having few links between game engine, gae, and gp.

3. How is your part linked to other parts of the project?
Game authoring environment allow the user to choose the rules and design the visual elements. 
These are then written to a file to be interpreted by the game player. The game player creates a game engine, which uses the files
to determine the game logic.

4. What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
Mostly have to do with game designer specifying unreasonable options (example, menu size 1 million)

5. Why do you think your API/design is good (also define what your measure of good is)?
Good design because it is flexible, and with minor refactoring, can be changed to accomodate different genres such as Brickbreaker.

##Part 2

1. What feature/design problem are you most excited to work on?
Creating inheritance hierarchies for the editors

2. What feature/design problem are you most worried about working on?
Game data writing/communicating with other parts of the project.

3. What major feature do you plan to implement this weekend?
Several editors

4. Discuss the use cases/issues created for your pieces: are they descriptive, appropriate, and reasonably sized?
The use cases all describe the user creating various objecs in the game  environment. They are descriptive, appropriate, and reasonably sized.

5. Do you have use cases for errors that might occur?
Errors arise from the user entering unreasonable values into the editor.

==============================================================================================================================================================================



Team 2 (Raindrop)
Hamsa Pillai, hsp8

##Part 1

1. What about your API/design is intended to be flexible?
Our design is flexible because we really used the MVC system, along with numerous interfaces, to be able to easily add new features.

2. How is your API/design encapsulating your implementation decisions?
We use a lot of interfaces.

3. How is your part linked to other parts of the project?
My part is linked to other parts by a Controller object, and that is the only link.

4. What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
Not much really since we only execute pre-defined behavior, so the creation of that behavior would handle errors.

5. Why do you think your API/design is good (also define what your measure of good is)?
I think it is good (extensible and easily able to play different kinds of games) because we use a component-based, action-driven design with lots of interfaces which allows easy changeability.

##Part 2

1. What feature/design problem are you most excited to work on?
Collision handling

2. What feature/design problem are you most worried about working on?
Powerup handling

3.What major feature do you plan to implement this weekend?
Collision handling

4. Discuss the use cases/issues created for your pieces: are they descriptive, appropriate, and reasonably sized?
The use cases discuss collisions between 2 specicif objects so they are common and appropriately sized.

5. Do you have use cases for errors that might occur?
We do not, but would be a good thing to add.