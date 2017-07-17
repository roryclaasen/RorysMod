# About

Modules are items that allow the [laser rifle](tools/rifle.md) to be customised, from damage to color to the rifle cool down.

#### Any errors or issues with the modules?

Please let me know by [clicking here](https://github.com/GOGO98901/RorysMod/issues/new) to submit an issue.

#### Want a new module?

NOTE: I may not add the module, but I probably will.

- Create a [pull request](https://github.com/GOGO98901/RorysMod/compare) with the new module implemented (the texture does not have to be added at this point)
- Create a [new issues](https://github.com/GOGO98901/RorysMod/issues/new) that says what should be added, remember to add the idea label

# Contents

- [Base Modules](#base-modules)
- [Capacitor Module](#capacitor-module)
- [Coolant Module](#coolant-module)
- [Lens Module](#lens-module)
- [Phaser Module](#phaser-module)
- [Overclock Module](#overclock-module)
- [Explosion Module](#explosion-module)
- [Igniter Module](#igniter-module)
- [Formula](#formula)
- [Crafting](#crafting)

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

This module allows the laser to create an explosion when the it hits either a block or an entity. If no explosion modules are present then it will not create an explosion no matter of what the [formula](#explosion-strength) says.

## Igniter Module

This module sets the block/entity hit on fire

## Formula

The below formulas are what the mod will be using in the next release. These are subject to change!

### Capacity Size

```
1000RF + (150RF x number of capacitors)
```

### Maximum energy receive

```
maxReceive = 50RF x tier of rifle
maxReceive += 10 x number of capacitors
```

### Power Usage

```
usage = 50RF
usage += 15RF x number of explosion modules
usage += 7RF x number of igniter modules
usage += 50RF x number of overclockers
usage += 5RF x number of phaser modules
```

### Damage Given

```
damage = 1 x number of phaser modules
damage += 0.75 x number of overclockers
damage -= 0.2 x number of coolant modules
```

### Explosion Strength

```
strength = 1 x number of explosion modules
strength += 0.5 x number of overclockers
```

### Fire Time

```
time = number of igniter modules
time += 0.75 x number of overclockers
time -= 0.2 x times number of coolant modules
```

### Cooldown Time

```
cooldown = 100
cooldown += 10 x number of overclockers
cooldown += 10 x number of phaser modules
cooldown += 10 x number of igniter modules
cooldown -= 25 x number of coolant modules
```

#### Cooldown update

The cooldown is updated once per tick with the following formula

```
cooldown -= 1
cooldown -= (number of coolant modules / 2)
if player is wet (rain or in water) then
    cooldown -= 2
endif
```

## Crafting

!!! note
	This section is still in development
