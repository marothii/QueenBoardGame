[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/f0r53tPY)

# Queen Board Game

## Description

The following game is played by two players. Consider a chessboard and a white queen marked with (white circle) symbol. Initially, the queen is placed randomly in the top row or the rightmost column (column h). Players move in turn. In a move, a player must move the queen. The queen must step at least one square and can move only left, down, or diagonally left down. The winner of the game is the player who moves the queen to the bottom-left (a1) square of the board.



Initial Position = **(0,7)**\
Goal Position = **(7,0)**

## Path for Winning Game


LAYER_2' move [from]: 0 7\
PLAYER_2' move [to]: 1 6

PLAYER_1' move [from]: 1 6\
PLAYER_1' move [to]: 2 5

PLAYER_2' move [from]: 2 5\
PLAYER_2' move [to]: 3 4

PLAYER_1' move [from]: 3 4\
PLAYER_1' move [to]: 4 3

PLAYER_2' move [from]: 4 3\
PLAYER_2' move [to]: 5 2

PLAYER_1' move [from]: 5 2\
PLAYER_1' move [to]: 6 1

PLAYER_2' move [from]: 6 1\
PLAYER_2' move [to]: 7 0

PLAYER_2 won !!


## Requirements
Building the project requires JDK 21 or JDK 22 and [Apache Maven](https://maven.apache.org/what-is-maven.html).
## Usage


Created with Mac-OS , using Java 21.

&nbsp;

 In project's root directory, run the following commands :

* mvn package

* java -jar ./target/homework-template-project-1.0.jar

&nbsp;



Developed By:

Marothi Amber Nkadimeng