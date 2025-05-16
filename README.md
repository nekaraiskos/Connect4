# Connect4
Development of Connect4 board game in Java with 3 difficulties of a working AI opponent and rewatchable games history. 

The AI opponent utilizes the Minimax algorithm with different depth depending on the chosen difficulty.

Games history is saved in JSON format

## How to Install and Run

At least JDK version 17 is needed to run the program https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

Clone repo's main branch and save it inside a folder of your choice. Open the project's folder with a text editor of your choice.
I recommend Visual Studio Code which was used while developing.

Currently, there is no executable to run the program, you have to run file src/connect4/Connect4.java which contains "main"

## How to play

This game has 3 accessible menus:

-New Game: When you press it and choose one of the submenus with the difficulty, a new round of the game will start
-1st Player : Use it to pick if the player or the AI plays first.
-History : Press it for a new window to show up with your completed games history. Double click on any of the saved games
	for the playback of that game. You can interrupt it if you want to start a new game, but not to play another game playback.
	For that you have to wait until it finishes.

-Menu "Help" is deactivated.

In order to start playing when you first open the game you have to press "New game" and pick one of the difficulties. Then it will start.
The default settings have the "AI" as 1st Player.

To place a disk as the player, either press the column's corresponding number on the keyboard(0-6 from left to right) or double click the column on which you want your disk placed.