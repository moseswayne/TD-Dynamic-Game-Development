NetIDs: Tnk3, Drl21, smm117

###Part 1
* What about your API/design is intended to be flexible?

My API for the StackButton is flexible because it allows other frontend developers to modify the way it looks or add new features without messing up the way it currently works. For instance, the user can override the layoutViews method to change the way the different components fit together,

* How is your API/design encapsulating your implementation decisions?

The API provides other developers an easy way to position the icon and text by simply sending a “Pos” object.

* How is your part linked to other parts of the project?

Everybody on the frontend will probably use this at some point because of how much easier it is to use a StackButton as opposed to a javafx Button.

* 	What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?

Possible exceptions are when the lambda function (that determines what happens when the user clicks the button) throws an error. 

* Why do you think your API/design is good (also define what your measure of good is)?

I think my API for the StackButton is good because it helps make everyone else’s job in the frontend easier.

###Part 2
* What feature/design problem are you most excited to work on?

We’re going to give the user the ability to edit the layout of the Game’s interface. So for instance, the user is going to be able to determine where the menu is going to be, where the health bar and stuff are going to be, etc. This is a hard design problem because 

* What feature/design problem are you most worried about working on?

I'm mostly worried about not being able to make the whole thing look good enough. 

* 	What major feature do you plan to implement this weekend?

I plan to implement the Tower Editor, Enemy Editor, Projectile Editor, and Level Editor over the weekend. I also plan to provide the base UI components for, like a window that pops up, for my teammates to use in their code.

* Discuss the use cases/issues created for your pieces: are they descriptive, appropriate, and reasonably sized?

The issues we created are descriptive. We are going to make issues for all of these features.

* Do you have use cases for errors that might occur?

We do have use cases for errors that might occur. For instance, if the user makes a path on a map that does not have an exit point, then the Authoring Environment will not let the user save the game to an xml file. It will instead prompt the user to finish making the path.
