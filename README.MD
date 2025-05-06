# Item Tool
[中文](./README_CN.md) | English

[![GitHub License](https://img.shields.io/github/license/RethinkQAQ/ItemTool)](http://www.gnu.org/licenses/lgpl-3.0.html)


## Introduce

First and foremost, heartfelt thanks to Fallen for the amazing [mod template](https://github.com/Fallen-Breath/fabric-mod-template).

This is a client-side mod that displays various data about item entities. Since it works by parsing the item entity's data, you need to have permission level 2 or higher to use it.
This is my first attempt at developing a relatively complete mod. I apologize for any shortcomings, and I hope this mod can be helpful to you.

## Features

- Displays the velocity (x/y/z) of the item entity

- Displays the horizontal speed (x and z components) of the item entity

- Shows the stack count of the item entity

- Indicates whether the item entity is in contact with the ground

- Displays the item entity's age (time since spawning)

- Shows the remaining lifespan of the item entity (vanilla item entities despawn after 6000 gt)

- Displays the item entity's pickup delay

- Shows the item entity's portal cooldown

- Indicates whether the item entity is being processed in the current tick (the game may skip simulate item entities in some ticks)