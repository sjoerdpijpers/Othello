# Othello
Adversarial search algorithm for the game Othello

Description of the project can be found in "project1 othello.pdf".

For convenience, players are represented with integers, specifically
* 1 = player 1 = the human/your AI’s opponent = black.
* 2 = player 2 = your AI = white.
Similarly, the game board itself is represented in GameState by a 2-dimensional integer array, where the values should either be: 0 (empty), 1 (player 1), or 2 (player 2).

## How to run the project?

java Othello human DumAI (or you might have to include ‘–cp .’ i.e. write java –cp . Othello human DumAI).

This command will create a default 8x8 game board, where a human is player 1 and DumAI is the AI/player 2. The ’human’ argument is a special keyword which activates mouse clicks for a human player. If you would like to play two logics against each other you just have to replace the keyword ’human’ with the IOthelloAI implementation of your choice. The game parameters also allow you to define the size of the game board, e.g. if I would like to play ’AI1’ against ’AI2’ on a 6x6 game board (where ‘AI1’ starts) you can run the game in the following way:

java Othello AI1 AI2 6

In order to run my IOthelloAI implementation against the DumAI implementation on a 4x4 board, run the follwing command:

java Othello OthelloAI16 DumAI 4


