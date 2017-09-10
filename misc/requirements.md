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

1. __Ball__  
    a. A ball shall have one of six colours
2. __Receptor__  
    a. A receptor shall have 4 slots  
    b. A receptor shall accept one ball per open slot  
    c. A receptor shall turn 90 degrees clockwise on a mouse click  
    d. All balls in a receptor shall explode and vanish (leaving the slots open), if all slots of the receptor are 
    filled with balls of the same colour  
    e. A ball shall leave its slot in a receptor in case of mouse click on the ball iff the slot is connected to a rail
3. __Rail__  
    a. A rail shall interconnect two receptors  
    b. A rail shall allow balls to travel between its two endpoints   
    c. A rail shall be able to handle multiple balls at the same time without collision
4. __Game__  
    a. The game shall have a begin line which spawns a new ball every _x_ seconds  
    b. The game shall choose a random ball to spawn on the begin line  
    c. The game shall have a time counter to show the amount of time that is left for the begin line.  
5. __Level__  
    a. A level shall have at least two receptors  
    b. A level shall have a maximum amount of time that can be spent on that level  
    c. A level shall be marked as completed iff all receptors have exploded at least once  
    d. A level shall be marked as lost iff the maximum amount of time to complete that level has passed
    e. A level shall be marked as lost iff the maximum amount of time of the begin line has passed

## 2. <a id="should-have"></a> Should Have

1. The game shall have a score counter
2. The score counter shall start at zero at the start of a level
3. The game shall increase the score counter by _x_ amount of points, on an exploding receptor
4. The game shall provide a menu screen
5. The game shall provide a button on the menu screen to start/resume the game
6. A running game shall allow to be paused and resumed
8. The player shall lose a level when a ball in the begin line is not put in a slot within a given amount of time.

## 3. <a id="could-have"></a> Could Have

1. The game shall save the scores of all of its players by using a high-score list, holding the top ten names and scores of players with the highest scores
2. The game shall provide a button on the menu screen to show the high-score list
3. The game shall provide an option to set a username
4. The game shall play sound effects on events.
5. The game shall play a music theme when in progress
6. The game shall provide an option to disable sound effects/music
7. The game shall have a different background for each level
8. The game shall have a splash screen
9. The game shall have an option to play multiple levels
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
