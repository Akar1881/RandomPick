# RandomPick

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21.1-brightgreen)
![Java Version](https://img.shields.io/badge/Java-21-blue)
![Spigot API](https://img.shields.io/badge/Spigot-1.21.1--R0.1--SNAPSHOT-yellowgreen)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

---

## Overview

**RandomPick** is a simple yet fun Minecraft plugin for Spigot (1.21.1) servers that replaces the normal block drops with random items — adding surprise and excitement to mining and gathering.

- Break a block and get a random item instead!
- Items can be enchanted randomly with configurable chances.
- Occasionally, find enchanted books with random enchantments.
- Fully configurable via `config.yml`.
- Easily exclude unwanted items from random drops.
- Player-friendly messages with color support.

---

## Features

- **Random drops**: Blocks drop random items instead of their usual drops.
- **Enchantments**: Chance to get enchanted tools, weapons, armor.
- **Enchanted books**: Chance to receive enchanted books as drops.
- **Configurable chances**: Control drop replacement, enchantment, and book drop chances.
- **Unwanted items blacklist**: Prevent technical or unwanted items from appearing.
- **Player feedback**: Customizable messages on receiving drops.
- **Lightweight & efficient**: Minimal performance impact.

---

## Installation

1. Make sure your server is running Minecraft **1.21.1** with Spigot or Paper.
2. Download the latest [RandomPick.jar](#) from the releases.
3. Place the jar file in your server's `plugins` folder.
4. Start or reload your server to generate the default config.
5. Customize `plugins/RandomPick/config.yml` to fit your needs.
6. Restart or reload the server for config changes to take effect.

---

## Configuration (`config.yml`)

```yaml
# Chance in percent to replace block drops with random drops (0-100)
drop-chance: 100

# Chance in percent to add enchantments to random dropped items (that can be enchanted)
enchant-chance: 30

# Maximum number of random enchantments to add on enchanted items
max-enchants: 3

# Enable or disable dropping enchanted books as random drops
allow-enchanted-books: true

# Chance in percent to drop an enchanted book instead of a normal item
enchanted-book-chance: 10

# List of materials to exclude from random drops
unwanted-items:
  - AIR
  - CAVE_AIR
  - VOID_AIR
  - BEDROCK
  - BARRIER
  - COMMAND_BLOCK
  - COMMAND_BLOCK_MINECART
  - STRUCTURE_BLOCK
  - STRUCTURE_VOID
  - JIGSAW
  - DEBUG_STICK
  - LIGHT
  - REPEATING_COMMAND_BLOCK
  - CHAIN_COMMAND_BLOCK
  - KNOWLEDGE_BOOK
  - PLAYER_HEAD
  - PLAYER_WALL_HEAD
  - END_PORTAL_FRAME
  - NETHER_PORTAL
  - END_GATEWAY
  - SPAWNER

# Message sent to player when they receive a random drop
messages:
  receive-drop: "§aYou received a random drop!"
  receive-enchanted-drop: "§bLucky! You got an enchanted item!"
  receive-enchanted-book: "§dYou found a mysterious enchanted book!"

```
---

Commands

(Currently no commands implemented. Future updates may add /randompick reload.)


---

Permissions

No permissions required to trigger the random drops as this works automatically on block break.


---

Development & Contribution

Contributions are welcome!
Feel free to open issues or submit pull requests on GitHub.


---

Author

Akar1881
GitHub Profile


---

License

This project is licensed under the MIT License - see the LICENSE file for details.


---

Support

If you find issues or have suggestions, please create an issue on the GitHub repository or contact me.


---

Enjoy random surprises while mining! 🎉

