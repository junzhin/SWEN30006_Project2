


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
## Rules of the game:

	The Oh Heaven game play is briefly described below. 
	Note that some of the elements listed below as specific values are configurable. The current program developed by NERDI already supports this behaviour.

	1. Oh Heaven is played with a standard fifty-two card deck with four suits and thirteen ranks in each suit.

	2. The game involves four players who play independently, i.e. there are no teams. You can assume exactly four players. The aim is to get the highest score.

	3. The game is made up of three rounds. Each round is made up of the following sequence:

		a. A hand of thirteen cards being dealt to each player, a trump suit is randomly selected (		displayed in the upper left of Figure 1) for the round, and a player being randomly selected to bid first and then start or lead.

		b. Each player then makes a bid as to how many tricks they think they can win. They will get 10 extra points if they make exactly this many tricks. The total bid cannot equal the thirteen, that is, the last player must bid such that it is not possible for every player to achieve their bid.

		c. The game play then proceeds as follows:

			i. The player taking the lead can play any card they wish from their hand to the centre; this card provides the basis for a trick.

			ii. Play proceeds clockwise from the lead with each player playing one card in turn to follow the lead.

			iii. Following players must play a card of the same suit as that lead if they have one. If not, they may play any card they wish.

			iv. Once every player has played one card, the winner is the player who has played the highest card of the trump suit if any, or the highest card of the lead suit if not.	

			v. The winner will receive one point for winning the trick; if any cards remain, they go back to step i by leading a card to start a new trick.

		d. At the end of the round, any player who made exactly the number of tricks that they bid will receive an additional 10 points.

	4. At the end of all rounds, the player (or players) with the highest score wins.
---

## Program Improvements and Extensions to the original verssion:
### Additional NPC types
- Smart Player

- Legal Player (Follow the game Rules with a "Random selection of playing a cardd" Strategy)


---

## Team members
| Name | Student ID |
| ---- | ---- |
| Ziqiang Li | 1173898 |
| Junzhi Ning | 1086241 |
| Lujia Yang | 1174148 |

---
## Tutorial Time

Workshop 14 14:30 - 16:30
 

## Structures of folders: 
- `Oh_Heaven`:
	- `lib`: Game Framework Prototype and files
	- `sprites`:  Testing puposes and Game behaviour control
	- `properties`: Project resourses, including images, sound, icons etc
	- `src`: Main structure of the game program

---
## Report and Software Design Diagrams:

The report link is at https://www.overleaf.com/3295722264jqmccgwbfwtm
Notion Link: https://www.notion.so/Assignment2-e7975df0c393437e980677258291ae21

---
## Software Installaition and Running:
If it is your first time to open this project, make sure you open the program folder at the correct directory level.
<pre>
<code>   
</code></pre>
---
## Others:
