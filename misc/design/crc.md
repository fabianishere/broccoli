# Description of making the CRC cards
From the requirements we made for the Gudeballs game, five objects stand out the 
most: Ball, Receptor, Track, Level and Nexus. Since a ball is able to travel over 
a Receptor, Track and Nexus, we introduce a Travelable which indicates a ball is able to travel over this entity.
 
Other nouns like Score Counter and Timer fit more as attributes of (for example) the level class, so we don’t view these as candidate classes.

Furthermore, objects like Locks and One way tracks can be found in the requirements. From this we form the candidate classes:

- Ball
- Travelable
- Receptor
- Track
- Level
- Nexus
- Lock
- One way Track

# Responsibilities
The Ball class does not have any responsibilities. A ball is a dumb object that gets passed around between the Travelable entities in the game (Receptor, Track, Nexus).

A Travelable entity should be able to accept balls to allow them to travel over the entity. It should also be able to release the ball to pass it on to the next entity.

A Receptor must be able to have its slots rotated.  A Track should interconnect two Receptors and allow balls to travel over it. A Nexus should spawn (random) balls and hand them over to a Receptor.

With these responsibilities, we have created CRC cards which can be found in the same directory as this document.

# Comparison with the actual implementation
The difference with the design that came up with the creation of the CRC cards that there is no notion of a grid on which entities are placed. 
This notion is used in our implementation and simplifies traveling of the balls over these entities, since the four tiles on this grid where balls can go to or be received from are known.

Also, we have not implemented a notion of locks or one way tracks, so these classes do not exist in our implementation yet.

# Design of implementation
The main classes we use in the implementation mostly align with the design described above. These main classes are:
 - Grid         - A grid of tiles on which tileable entities are placed 
 - Tileable     - Abstract class which is implemented by entities which can be placed on a tile
 - Receptor     - A tileable entity which accepts balls in empty slots and allows release of balls
 - Track        - A tileable entity which accepts balls from its two endpoints
 	- HorizontalTrack
 	- VerticalTrack
 - Nexus        - A horizontal track which delivers balls to a receptor if the receptor is placed below the nexus
 - SpawningNexus
 - Empty        - An empty object which is placed initially on a tile
 - Level        - A description of the grid of a game

The non-main classes of the project are Entity, Ball, Direction, GameSession, NexusContext and Slot. We could have refactored these classes away, but we think the current approach is clearer.

For example, the Ball class does not have many responsibilities, but is simply used a dumb object that is passed around. 
Also the Direction enum could be converted to a simple integer, but we think using an enum conveys the meaning better and prevents errors.

Lastly, the Entity interface does not have any responsibilities, since it does not define any methods. This means the interface is basically useless and could be removed in future releases.
