# Contents
1. [Functional Requirements](#functional-requirements)  
1.1 [Must Have](#must-have)  
1.2 [Should Have](#should-have)  
1.3 [Could Have](#could-have)  
1.4 [Would Have](#would-have)  
2. [Non-Functional Requirements](#nonfunctional-requirements)

# <a id="functional-requirements"></a> Functional Requirements

For the game Gudeballs, the requirements regarding functionality and service are grouped under the Functional Requirements. Within these functional requirements, four categories can be identified using the MoSCoW model<sup>[1](#ref-moscow)</sup> for prioritizing requirements:

## <a id="must-have"></a> Must Have

- No more balls shall be accepted by the receptor, if all slots of the receptor are filled
- All balls in a receptor shall explode and vanish (leaving the slots open), if all slots of the receptor are filled with balls of the same colour
- A receptor shall have 4 slots
- A receptor shall accept a ball in open slots
- A level shall have at least two receptors
- A ball shall leave its slot in a receptor in case of mouse click on the ball
- A receptor shall turn 90 degrees clockwise on a mouse click
- Two receptors shall be interconnected by a rail that guides balls to the next receptor
- The game shall have a begin line which spawns a new ball every _x_ seconds
- The game shall choose a random ball to spawn on the begin line.
- A ball shall bounce back, in case the ball collides with a slot that is already filled with a ball.
- Balls shall be divided into multiple colours.
- The player shall complete a level when all receptors have exploded at least once.
- The game shall have a time counter to show the amount of time that is left for the begin line.
- A level shall have a maximum amount of time that can be spend on that level.
- The player shall lose a level when the maximum amount of time has passed away
- Rails shall be able to handle multiple balls at the same time without collision

## <a id="should-have"></a> Should Have

- The game shall have a score counter
- The score counter shall start at zero at the start of a level
- The game shall increase the score counter by _x_ amount of points, on an exploding receptor
- The game shall provide a menu screen
- The game shall provide a button on the menu screen to start/resume the game
- A running game shall allow to be paused.
- A paused game shall allow to be resumed.
- The player shall lose a level when a ball in the begin line is not put in a slot within a given amount of time.

## <a id="could-have"></a> Could Have

- The game shall provide a button on the menu screen to show the highscores
- The game shall save the scores of all of its players by using a high-score list, holding the top ten names and scores of players with the highest scores
- The game shall provide an option to set a username
- The game shall play sound effects on events.
- The game shall play a music theme when in progress
- The game shall provide an option to disable sound effects/music
- The game shall have a different background for each level
- The game shall have a splash screen
- There must be an option to play multiple levels
- The game shall have locks, which can only be passed by balls of the same colour as the lock
- The colour of the balls the lock shall accept shall vary
- The joker ball shall be able to behave as any colour
- A receptor shall not accept balls in the turn action
- The game shall have one way rails
- A ball shall be sent to the direction the arrow points to, if the ball collides
with that arrow

## <a id="would-have"></a> Would/Won't Have

- The game shall have a multiplayer option
- The game shall provide an option to customize a level
- The game shall provide an option to create a level from scratch
- The game shall provide support for mobile devices

# <a id="nonfunctional-requirements"></a> Non-Functional Requirements

Besides the provided functionality and services, design constraints need to be included in the requirements specification as well. These requirements do not indicate what the system should do, but instead indicate the constraints that apply to the system or the development process of the system.

- The game shall be playable on Windows (7 or higher), macOS (10.8 and higher), and Linux.
- The game shall be implemented in Java
- A first fully working version of the game shall be delivered at September 15, 2017
- For the iterations after the delivery of the first fully working version, the Scrum methodology shall be applied
- The implementation of the game shall have at least 75% of meaningful line test coverage (where meaningful means that the tests actually test the functionalities of the game and for example do not just execute the methods involved)

#
1. <a id="ref-moscow"></a> [https://en.wikipedia.org/wiki/MoSCoW\_method](https://en.wikipedia.org/wiki/MoSCoW_method)
