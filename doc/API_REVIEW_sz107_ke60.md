NetIDs: sz107, ke60

###Part 1
1. What about your API/design is intended to be flexible?
	
	IActor interface allows flexibility with regards to the step() function. All actors (no matter what subtype can be updated with the generic act() method. The act() method simply has different implementations for the different subtypes but serve the same general purpose of updating state of the actor.
	
2. How is your API/design encapsulating your implementation decisions?

	We are generally following the MVC framework in which the front end implementation details are encapsulated from the back end and vice versa. The Controller layer takes care of allowing the View and Model to evoke changes on each other, while encapsulating as much details as possible. This is done through Handler interfaces that specify a contract for what behaviors can be called. 
	
3. How is your part linked to other parts of the project?

	My part (GameController) links the View and Model together so that they can evoke changes on each other, while encapsulating as much details as possible. This is done through Handler interfaces that specify a contract for what behaviors can be called. 

4. What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?

	- Front end trying to create a Game Object of a type that doesnt exist
	- Front end trying to update a Game Object that doesn't exist in back end

	Will take care of these by throwing customized Exception class VoogaException that displays message of the error case
	
5. Why do you think your API/design is good (also define what your measure of good is)?

	Good means flexible, minimal dependencies, encapsulation, and extensibility. Our API is good because of the abstractions we used to represent actor, and our Controller passes Handlers to encapsulate implementation details between Model and View. 

###Part 2

1. What feature/design problem are you most excited to work on?
	- Statuses
2. What feature/design problem are you most worried about working on?
	- Controller because there are so many dependencies and is sensitive to any minor changes on both View and Model
3. What major feature do you plan to implement this weekend?
	- Controller
4. Discuss the use cases/issues created for your pieces: are they descriptive, appropriate, and reasonably sized?
5. Do you have use cases for errors that might occur?
	- Yes, we have use cases involving exception-throwing when errors occur