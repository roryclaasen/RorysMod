# Rifle Modules
## About
Modules are items that allow the rifle to be changed, from damage to color to the rifle cool down.

#### Any errors or issues with the modules?
Please let me know by [clicking here](https://github.com/GOGO98901/RorysMod/issues/new) to submit an issue.
#### Want a new module?
Check if I'm not about to add it already by looking at this [list](#Planned Modules) then do one of the following bellow.<br>
NOTE: I may not add the module, but I probably will.
- Create a [pull request]() with the new module implemented (the texture does not have to be added at this point)
- Create a [new issue](https://github.com/GOGO98901/RorysMod/issues/new) that says what should be added

### Contents
1. [Base Modules](#Base Modules)
2. [Capacitor Module](#Capacitor Module)
3. [Coolant Module](#Coolant Module)
4. [Lens Module](#Lens Module)
5. [Phaser Module](#Phaser Module)
6. [Overclock Module](#Overclock Module)
7. [Explosion Module](#Explosion Module)
8. [Formula](#Formula)
9. [Planned Modules](#Planned Modules)

## Base Modules
Each Module can stack up to 16, and will change the rifles stats depending on which ones are added. See each module for more information.

## Capacitor Module
The capacitor module allows the rifle to hold more power capacity, however it also increases power usage slightly.

## Coolant Module
This module will decrease the cool down time that it takes to fire a new laser but will also in some case reduce the power usage. Almost like an onsite [overclocker](#Overclock Module).

## Lens Module
This module allows the laser to change color. This feature does and does not not work currently and so this item can only be spawned in.

## Phaser Module
This module changes the amount of damage that is given to an entity when hit. With out this module the laser will do no damage no matter what the [formula](#Damage) says.

## Overclock Module
This module makes other module more power full with the cost of using more power when used

## Explosion Module
This module allows the laser to create an explosion when the it hits either the block or an entity. If no explosion modules are present then it ill not create an explosion no matter of what the [formula](#Explosion size) says.

## Formula
The bellow formulas are what the mod will be using in the next release. These are subject to change
#### Capacity
```
1000 + (1000 x number of capacitors) + (100 x number of overclockers)
```
#### Usage
```
10 + (75 x number of overclockers) + (13 x number of capacitors) - number of coolant + (60 x number of phasers) + (60 x number of explosion modules)

if usage less than 10 then
	usage equals 10
end if
```
#### Damage
```
1 + (1.5 x (number of phasers x (1.2 x (number of overclockers + 1))))
```
### Explosion size
```
1 + (0.325 * number of explosion modules) + (0.1 * number of overclockers)
```
# Planned Modules
Currently these are the modules I will be implementing in the future
- [ ] Igniter Module (set block/entity on fire when hit)
