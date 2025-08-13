
### CONTINUE
- IMPLEMENT A WAY OF RESUME TURN ACTION AFTER RESOLVE ABILITY (EXAMPLE HIT AFTER ON ATTACK IS RESOLVED)
- CHECK HOW IT RESOLVES IN THE OPPONENT SIDE 

### TODO LIST
- implement mindbugs
- select target card for abilities
- get all colors in commons 
- indicative of attacker
- create refined strategy for opponent to check cards before selecting the attacker 
- add animations to play card and attack card (opponent and player) play card animation can control the delay of the opponent action
- add start menu
- extra row of cards when arena is too big
- rework ui

###  DONE
- player basics
- card skeleton
- opponent util methods 
- card seeder skeleton
- mechanic to get hand
- enums to set keywords
- fixed battle to handle Poison without hitting 2 times when Tough exists
- first version of the UI
- pass card from hand to battlefield (on drop move card to battlefield) 
- fixed player hand size
- fixed battlefield size
- create component for opponent hand
- create area for opponent arena inside battlefield
- display player hp
- display player mindbugs
- implement a way of updating the opponent
- implement a way of handling turn beginning and turn ending
- mechanic to sort deck 
- create mechanic to start battle
- implemented opponent attack 
- create mechanic to end turn and start opponent turn
- implement discard pile
- improved seeder
- fixed attack killing all equal cards on the arena
- refactored keywords (thank god)
- player take hit
- game over screen
- simplified can defend logic
- player now can defend opponent card
- implement buy card (player and opponent side)
- REFACTORED THE TURN CHANGE, GOT RID OF THAT NASTY ONPLAYCHANGE WORKAROUND 
- attack feature for player
- player doing direct hit when opponent has no defense
- implement a way of setting the defender, even when there is no card available to defend.
- UI redone
- added icon pack
- added first images for cards (deadly hamster and phone stealer otter, thanks wife <3)
- fix defend bug when a non-sneaky card could block sneaky cards
- implemented frenzy keyword
- fixed player being able to add cards while defending
- added game end checks on end turn method
- added game restart option
- added image for alchemist hedgehog
- simplified act method on opponent 
- added more logs
- fixed non frenzy cards hitting twice
- resizeable cards depending on screen size
- implemented Abilities
- frenzy cards didn't release player

## LINKS 
- initial design for card: https://www.figma.com/design/gE3nTNrI9oPwxc3TbKtfgN/card-game?node-id=0-1&node-type=canvas&t=tBMIXx0dpl2balp8-0