# Project 3 Prep

**For tessellating pluses, one of the hardest parts is figuring out where to place each plus/how to easily place plus on screen in an algorithmic way.
If you did not implement tesselation, please try to come up with an algorithmic approach to place pluses on the screen. This means a strategy, pseudocode, or even actual code! 
Consider the `HexWorld` implementation provided near the end of the lab (within the lab video). Note that `HexWorld` was the lab assignment from a previous semester (in which students drew hexagons instead of pluses). 
How did your proposed implementation/algorithm differ from the given one (aside from obviously hexagons versus pluses) ? What lessons can be learned from it?**

Answer: I would use the randomizer code given in order to randomize the information. 
However, I would additioanlyl develop an algorithm through the "psueudorandomness" in order to randomize the information based on the seed. 
I would then create the different shapes and put them in locations where they would be able to fully fit.

-----

**Can you think of an analogy between the process of tessellating pluses and randomly generating a world using rooms and hallways?
What is the plus and what is the tesselation on the Project 3 side?**

Answer: Tessellating pluses is similar to creating two different hallways, just in an ordered matter. By creating a randomizer
for where it can appear as well as its exact location, it can differenciate the size of the hallways.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating pluses.**

Answer: I would think of creating the different shapes, especially the hallways and the rooms. I would then randomize it through the randomized world within an empty map.

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: A hallway is a rectange of places that can be moved into while a room would be a giant square. The "room
should either be in the corner of two hallways or in the middle of an actual hallway.