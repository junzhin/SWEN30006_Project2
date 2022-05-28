


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
- Smart Player

- Legal Player (Follow the game Rules with a "Random selection of playing a cardd" Strategy)

### More Configurable Parameters through The Properties Folder
**There are two types of properties files used in this game program:**
1. Specific Game Options:
2. Overall Game Options(Choose which specific game option profile to run):

### Support Flexitble Intefact to alternative Bidding Strategies
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

- The report link is at 
    - <u>https://www.overleaf.com/3295722264jqmccgwbfwtm</u>
- Notion NoteBook Link:
    - <u> https://www.notion.so/Assignment2-e7975df0c393437e980677258291ae21</u>
- Design class diagram1: 
    - <u>https://lucid.app/lucidchart/41b6e425-57ba-4518-91ef-8c7e3b139cae/edit?viewport_loc=483%2C51%2C1008%2C505%2C0_0&invitationId=inv_56729a7d-4fed-4999-94fa-688308478745</u>
- Sequence class diagram1:
    - https://lucid.app/lucidchart/a486ac9e-5e93-4792-8dca-0f1f6d3ae74b/edit?viewport_loc=125%2C-7%2C2507%2C1411%2C0_0&invitationId=inv_70a7d190-70be-4575-9c35-87f0bbbd70f2#
    - https://lucid.app/lucidchart/5faca5b8-28cb-4a7a-b8e5-b56f8df94aa1/edit?viewport_loc=665%2C-319%2C3298%2C1856%2C0_0&invitationId=inv_f6d19d18-e7bf-4503-8728-de5841aa1364#
    - https://lucid.app/lucidchart/e2ced80a-b7e1-4662-bc70-3ccb2cb37ab9/edit?viewport_loc=-135%2C-231%2C2522%2C1419%2C0_0&invitationId=inv_6674eb0c-93b9-46dc-8738-c06021ba91bf#
    - https://lucid.app/lucidchart/6c86a49f-ab76-4d64-abb6-93bd85b60574/edit?viewport_loc=-184%2C-182%2C2294%2C1291%2C0_0&invitationId=inv_4718ab18-bec1-49f8-ba94-4c50a2e63f95#
    - https://lucid.app/lucidchart/dabfdd60-68c2-465d-83f6-e88707458119/edit?viewport_loc=-1309%2C-60%2C3832%2C2265%2C0_0&invitationId=inv_a7d06735-5539-4f5f-94cb-190294f5a241#
    - https://lucid.app/lucidchart/6c3dc7ba-58e0-438a-b63d-a2e5c89b65b1/edit?viewport_loc=-634%2C-46%2C2370%2C1359%2C0_0&invitationId=inv_c49e73c6-dc9a-4028-b0e6-a1a022a80dec#


---
## Software Installaition And Running:
- **If it is your first time to open this project, make sure you open the 
program folder at the correct directory level.**

-  Main function is at <u>src/oh_heaven/game/Oh_Heaven.java</u>
