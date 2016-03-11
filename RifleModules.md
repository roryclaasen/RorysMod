# Rifle Modules
## About
Modules are items that allow the rifle to be changed, from damage to color to the rifle cool down.

#### Any errors or issues with the modules?
Please let me know by [clicking here](https://github.com/GOGO98901/RorysMod/issues/new) to submit an issue.
#### Want a new module?
Check if I'm not about to add it already by looking at this [list](#planned-modules) then do one of the following below.<br>
NOTE: I may not add the module, but I probably will.
- Create a [pull request](https://github.com/GOGO98901/RorysMod/compare) with the new module implemented (the texture does not have to be added at this point)
- Create a [new issue](https://github.com/GOGO98901/RorysMod/issues/new) that says what should be added, remember to add the idea label

### Contents
1. [Base Modules](#base-modules)
2. [Capacitor Module](#capacitor-module)
3. [Coolant Module](#coolant-module)
4. [Lens Module](#lens-module)
5. [Phaser Module](#phaser-module)
6. [Overclock Module](#overclock-module)
7. [Explosion Module](#explosion-module)
8. [Igniter Module](#igniter-module)
9. [Formula](#formula)
10. [Planned Modules](#planned-modules)

## Base Modules
Each Module can stack up to 16, and will change the rifle's statistics depending on which ones are added. See each module for more information.

## Capacitor Module
The capacitor module allows the rifle to hold more power capacity, however it also increases power usage slightly.

## Coolant Module
This module will decrease the cool down time that it takes to fire a new laser but will also in some case reduce the power usage. Almost like an [overclocker](#overclock-module).

## Lens Module
This module allows the laser to change color. This feature does and does not work currently and so this item can only be spawned in.

## Phaser Module
This module changes the amount of damage that is given to an entity when hit. Without this module the laser will do no damage no matter what the [formula](#damage-given) says.

## Overclock Module
This module makes other modules more powerful with the cost of using more power when used.

## Explosion Module
This module allows the laser to create an explosion when the it hits either a block or an entity. If no explosion modules are present then it will not create an explosion no matter of what the [formula](#explosion-size) says.

## Igniter Module
This module sets the block/entity hit on fire

## Formula
The below formulas are what the mod will be using in the next release. These are subject to change.
#### Capacity Size
```
1000 + (1000 x number of capacitors) + (100 x number of overclockers)
```
#### Power Usage
```
10 + (75 x number of overclockers) + (13 x number of capacitors) - number of coolant + (60 x number of phasers) + (60 x number of explosion modules)

if usage is less than 10 then
	usage equals 10
end if
```
#### Damage Given
```
1 + (1.5 x (number of phasers + (1.2 x (number of overclockers + 1))))
```
### Explosion size
```
1 + (0.325 * number of explosion modules) + (0.1 * number of overclockers)
```
### Fire time
```
round up to whole number (number of igniter modules x 1.5)
```
# Planned Modules
Currently these are the modules I will be implementing in the future
- [x] Igniter Module (set block/entity on fire when hit)
