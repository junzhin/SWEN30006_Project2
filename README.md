


---
# <img src="https://cdn.freebiesupply.com/logos/large/2x/the-university-of-melbourne-logo-svg-vector.svg" width=15% align=left> SWEN30006 Project 2 
This repository is created for SWEN30006 Project 2 Semester 1 2022, Group Project
---

## Problem Context
Off the back of their overwhelming success with Snakes and Ladders on a Plane (sales went into double digits!), NERDI are back to work on their new game. While NERDI have learnt some lessons (sadly, none related to object oriented design), they still need the help of you and your team to assist them in getting their Oh Heaven card game market ready.
 
 
NERDI have a fully running version of the game, but the design is not very extendible or maintainable, it is limited in terms of configurability, and needs more NPC (Non-Playable Cardplayer) options.

---

![](assets/16524482539111.jpg)
![](assets/16524482643928.jpg)


---
## Rules Of The Game:

The Oh Heaven game play is briefly described below. 
**Note that some of the elements listed below as specific values are configurable. The current program developed by NERDI already supports this behaviour.**

1. Oh Heaven is played with a standard fifty-two card deck with four suits and thirteen ranks in each suit.

2. The game involves four players who play independently, i.e. there are no teams. You can assume exactly four players. The aim is to get the highest score.

3. The game is made up of three rounds. Each round is made up of the following sequence:

	a. A hand of thirteen cards being dealt to each player, a trump suit is randomly selected (		displayed in the upper left of Figure 1) for the round, and a player being randomly selected to bid first and then start or lead.

	b. Each player then makes a bid as to how many tricks they think they can win. They will get 10 extra points if they make exactly this many tricks. The total bid cannot equal the thirteen, that is, the last player must bid such that it is not possible for every player to achieve their bid.

	c. The game play then proceeds as follows:

	- i. The player taking the lead can play any card they wish from their hand to the centre; this card provides the basis for a trick.

	- ii. Play proceeds clockwise from the lead with each player playing one card in turn to follow the lead.

	- iii. Following players must play a card of the same suit as that lead if they have one. If not, they may play any card they wish.
	- iv. Once every player has played one card, the winner is the player who has played the highest card of the trump suit if any, or the highest card of the lead suit if not.	
	- v. The winner will receive one point for winning the trick; if any cards remain, they go back to step i by leading a card to start a new trick.

	d. At the end of the round, any player who made exactly the number of tricks that they bid will receive an additional 10 points.

4. At the end of all rounds, the player (or players) with the highest score wins.
---

## Program Improvements And Extensions To The Original Version:
### Additional Npc Types
- Smart Player: the purpose of this smart player/agent in this program is to produce a interface that allows any future extended versions of "smart agent", as we apply the strategy pattern here. 
- Legal Player (Follow the game Rules with a "Random selection of playing a cardd" Strategy): This is a basic type of game agent that only follow the game rules and the strategy of randomly playing one card.

### More Configurable Parameters through The Properties Folder
**There are two types of properties files used in this game program:**
1. Specific Game Options:
    - Allow the settings of the game agent types if there are defined in the program.
2. Overall Game Options(Choose which specific game option profile to run):
    - Control the delay of thinking time in the time unit of ms.
### Support Flexitble Intefact to alternative Bidding Strategies
3. Another interface to any potential bidding strategies is also considered,  it is not implemented but mentioned in the report.
---

## Team Members
| Name | Student ID |
| ---- | ---- |
| Ziqiang Li | 1173898 |
| Junzhi Ning | 1086241 |
| Lujia Yang | 1174148 |

---
## Tutorial Time

Workshop 14 14:30 - 16:30
 

## Structures Of Folders: 
- `Oh_Heaven`:
	- `bin`: Game Framework Prototype And Files
	- `properties`:  Testing Puposes And Game Behaviour Control
	- `sprites`: Project Resourses, Including Images, Sound, Icons Etc
	- `src`: Main Structure Of The Game Program

---
## Report And Software Design Diagrams:
- Design class diagram1: 

![SMD-Project2-Design Class Diagram](assets/SMD-Project2-Design%20Class%20Diagram.png)

- Sequence class diagram:
![project2 playerfactory sequence diagram](assets/project2%20playerfactory%20sequence%20diagram.png)
![project2-refer of stratergy factory](assets/project2-refer%20of%20stratergy%20factory.png)
![Project2-InitialiseProperties](assets/Project2-InitialiseProperties.png)
![project2 dynamic playRound design diagram](assets/project2%20dynamic%20playRound%20design%20diagram.png)
![project2 dynamic legalStrategy playRound design diagram](assets/project2%20dynamic%20legalStrategy%20playRound%20design%20diagram.png)


---
## Software Installaition And Running:
- **If it is your first time to open this project, make sure you open the 
program folder at the correct directory level.**

-  Main function is at <u>src/oh_heaven/game/Oh_Heaven.java</u>
