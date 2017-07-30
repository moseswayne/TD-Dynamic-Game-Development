# Part 1

1. We use composition for decision making to allow for more possibilities. We don't use inheritance.

2. Our primary backend interface, the actor interface, only has a .act() method which defines all actions that actor cold make. Someone implementing a new design for our program would just have to change the .act() method.

3. Grid interacts with the user when adding new elemtents to the game and with the actors to update them. 

4. An error could occur if two actors had the same ID. 

5. Because it allows for multiple games to be made with the same environment.

# Part 2

1. Working with the user interface to add actors to the grid on the backend when they are added on the frontend. 

2. I am worried that the front end and back end won't be able to effectively communicate. 

3. Working with saving/loading levels.

4. I think that they were all reasonable. Perhaps a little bit too predictable. We should have a made couple more really out there use cases to push the design. 

5. We originally did not do this. I think that we are going to do more error checking once the authoring envoronment can be translated to the back end. This should occur in the next couple of days. 
