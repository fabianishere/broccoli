# Contents
1. [Functional Requirements](#functional-requirements)  
1.1 [Must Have](#must-have)  
1.2 [Should Have](#should-have)  
1.3 [Could Have](#could-have)  
1.4 [Would Have](#would-have)  
2. [Non-Functional Requirements](#nonfunctional-requirements)

# <a id="functional-requirements"></a> Functional Requirements

For the game Gudeballs, the requirements regarding functionality and service are grouped under the Functional Requirements. Within these functional requirements, four categories can be identified using the MoSCoW model<sup>[1](#ref-moscow)</sup> for prioritizing requirements:

## 1. <a id="must-have"></a> Must Have

1. No more balls shall be accepted by the receptor, if all slots of the receptor are filled
2. All balls in a receptor shall explode and vanish (leaving the slots open), if all slots of the receptor are filled with balls of the same colour
3. A receptor shall have 4 slots
4. A receptor shall accept a ball in open slots
5. A level shall have at least two receptors
6. A ball shall leave its slot in a receptor in case of mouse click on the ball
7. A receptor shall turn 90 degrees clockwise on a mouse click
8. Two receptors shall be interconnected by a rail that guides balls to the next receptor
9. The game shall have a begin line which spawns a new ball every _x_ seconds
10. The game shall choose a random ball to spawn on the begin line.
11. A ball shall bounce back, in case the ball collides with a slot that is already filled with a ball.
12. Balls shall be divided into multiple colours.
13. The player shall complete a level when all receptors have exploded at least once.
14. The game shall have a time counter to show the amount of time that is left for the begin line.
15. A level shall have a maximum amount of time that can be spend on that level.
16. The player shall lose a level when the maximum amount of time has passed away
17. Rails shall be able to handle multiple balls at the same time without collision

## 2. <a id="should-have"></a> Should Have

1. The game shall have a score counter
2. The score counter shall start at zero at the start of a level
3. The game shall increase the score counter by _x_ amount of points, on an exploding receptor
4. The game shall provide a menu screen
5. The game shall provide a button on the menu screen to start/resume the game
6. A running game shall allow to be paused.
7. A paused game shall allow to be resumed.
8. The player shall lose a level when a ball in the begin line is not put in a slot within a given amount of time.

## 3. <a id="could-have"></a> Could Have

1. The game shall provide a button on the menu screen to show the highscores
2. The game shall save the scores of all of its players by using a high-score list, holding the top ten names and scores of players with the highest scores
3. The game shall provide an option to set a username
4. The game shall play sound effects on events.
5. The game shall play a music theme when in progress
6. The game shall provide an option to disable sound effects/music
7. The game shall have a different background for each level
8. The game shall have a splash screen
9. There must be an option to play multiple levels
10. The game shall have locks, which can only be passed by balls of the same colour as the lock
11. The colour of the balls the lock shall accept shall vary
12. The joker ball shall be able to behave as any colour
13. A receptor shall not accept balls in the turn action
14. The game shall have one way rails
15. A ball shall be sent to the direction the arrow points to, if the ball collides
with that arrow

## 4. <a id="would-have"></a> Would/Won't Have

1. The game shall have a multiplayer option
2. The game shall provide an option to customize a level
3. The game shall provide an option to create a level from scratch
4. The game shall provide support for mobile devices

# <a id="nonfunctional-requirements"></a> Non-Functional Requirements

Besides the provided functionality and services, design constraints need to be included in the requirements specification as well. These requirements do not indicate what the system should do, but instead indicate the constraints that apply to the system or the development process of the system.

- The game shall be playable on Windows (7 or higher), macOS (10.8 and higher), and Linux.
- The game shall be implemented in Java
- A first fully working version of the game shall be delivered at September 15, 2017
- For the iterations after the delivery of the first fully working version, the Scrum methodology shall be applied
- The implementation of the game shall have at least 75% of meaningful line test coverage (where meaningful means that the tests actually test the functionalities of the game and for example do not just execute the methods involved)

#
1. <a id="ref-moscow"></a> [https://en.wikipedia.org/wiki/MoSCoW\_method](https://en.wikipedia.org/wiki/MoSCoW_method)
