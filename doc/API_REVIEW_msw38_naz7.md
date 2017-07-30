# API REVIEW

Moses Wayne: msw38

Nikita Zemlevskiy: naz7

## My review of Nikita's API

### Part 1:

Nikita:

1. Uses entities with events and actions that define how different objects interact in the game. By using composition, the design for objects is much more extensible and flexible.
2. Everything is self contained and nothing ever has any extra information. 
3. Everything depends on the interactions between entities and the actions that they invoke. Without this part, the back end engine cannot run.
4. Reflection problems will throw an exception, and so do dunctionality errors. 
5. Strong encapsulation between componenets, composition instead of inheritance hierarchy makes it easy to add new events and actions for entities in the game.

### Part 2:

Nikita:

1. More interactions and more events to react to are very exciting for Nikita.
2. They are worried about how the front end will represent the game player and back end interactions.
3. They plan on finishing the serialization of the game. 
4. Use cases based around certain issues that can rise in the game. Issues raised for resolution involving more than two commits.
5. There are use cases in which exceptions are thrown. There are also use cases in which the user forgets to add an action for an event.


##Nikita's review of my API

## Part 1

### What about your API/design is intended to be flexible?
Write based on composition instead of inheritance as large hierarchies. 

### How is your API/design encapsulating your implementation decisions?
Use generics to encapsulate, and use lambda functions to prevent access to more than a specific function, nothing gets full access to more than an actor in a game.

### How is your part linked to other parts of the project?
Everything depends on the engine to function.

### What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
- Reflection problems
- Access rights
- Functionality errors alerted to the user. They are handled by alerting the frontend.
- Must have a path from beginning to end of a level

### Why do you think your API/design is good (also define what your measure of good is)?
Good encapsulation, flexible and extensible design. Easy to add new properties, and mix and match properties and actors. Using composition instead of inheritance facilitates these good qualities.

## Part 2

### What feature/design problem are you most excited to work on?
Create a tower that will allow for user interaction.

### What feature/design problem are you most worried about working on?
Worried about how the frontend is going to interact with the backend.

### What major feature do you plan to implement this weekend?
Planning on finishing up shooting properties and finishing the properties. Also fixing access rights.

### Discuss the use cases/issues created for your pieces: are they descriptive, appropriate, and reasonably sized?
Make issues for each use case. Issues could be utilized better or more frequently.

### Do you have use cases for errors that might occur?
Fieldrunner ie - throw an error when a path is blocked by a tower that is placed by the user.
Chat Conversation End
Type a message...



