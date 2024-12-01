
Productive Slimes
=======

This is a simple mod for Minecraft that adds a new type of slime to the game. These slimes are called "productive slimes" and they are able to produce recourses for you.

Progress Tracking: [Trello](https://trello.com/b/Xtij0qyG/productive-slimes)

Official Wiki (WIP): [Wiki](https://coolerproyt.github.io/ProductiveSlimes-Wiki/#/Home)

## Compatability
- JEI
- REI
- Jade
- The One Probe

## Version
### Neoforge
- **1.2.1**
  - Changes
    - Added REI compat
    - Removed Lake Placed Feature in Slime Land to avoid crash on chunk loading
- **1.2.0**
  - Changes
    - Added `Fluid Tank`, `Slimy Grass Block`, `Slimy Dirt`, `Oak Leaves Slime`, `Slimy Stone` , `Slimy Deepslate`
    - Added new biome `Slime Land` to overworld
    - Added function to let player add variant of slimes by editing json file in config folder (Visit wiki for guide)
    - Added Village to `Slime Land`
    - Added new villager profession `Scientist`
    - Added Vanilla Slime spawn to `Slime Land`
    - Reworked on some Block Entity Textures.
    - Slime will follow player if the player is holding their growth item and it still able to grow
    - Vanilla Slime no longer attack player
    - Iron Golem no longer attack slime
    - Updated bucket texture
    - Update slime block texture opacity
    - Added Terrablender as dependency
  - Fixes
    - Fixed incorrect slime hitbox
- **1.1.2**
  - Changes
    - Added squish animation for slime
- **1.1.1**
  - Changes
    - Slime will drop slimeball on death now
    - Added `dna_item`tag to DNA
    - Added missing tag for some slimeball
    - Changed 2 input slot in `DNA Synthesizer` to accept `dna_item` tag only
- **1.1.0**
  - Changes
    - Added Energy Slime, Slimeball and Slime Block
    - Implement FE to `Soliding Station` and `Melting Station`
    - Added `Cable` and `Energy Generator`
    - Added new item `Energy Multiplier Upgrade`
    - Updated JEI to show energy required
    - Remove slime transformation by right clicking with blocks
    - New method to obtain slime variants
    - New texture for `Soliding Station`
    - Added new Block Entity `DNA Extractor`
    - Added new Block Entity `DNA Synthesizer`
    - Added new item `Slime DNA` for all slime except Energy Slime
    - Added new Recipe Type `Dna Extracting`
    - Added new Recipe Type `Dna Synthesizing`
    - Updated Guidebook (For more complete information please use JEI)
  - Bug Fix
    - Fixed Block unable to mine
    - Fixed JEI arrow rendered 1 pixel wrongly
    - Fixed slime unable to spawn on server
    - Fixed JEI/TOP showing wrong countdown
- **1.0.3**
  - Changes
    - Added  `slimeball` tag to all slimeball variant
    - Changed vanilla recipe that use slimeball to accept every slimeball variants
    - Changed particle
    - Added many new variants of slimes, chekc it out in Guidebook
- **1.0.2**
  - New
    - Added Slime Block for all tier of slime
    - Added Melting Recipe for All Slime Block Tier
    - Slime able to split on death
  - Bug Fix
    - Fixed slime go underground when growth
- **1.0.1**
  - Bug Fix
    - Liquid Soliding Station translation
    - Soliding Station capabilities
    - Prevented slime despawn when player is far away
- **1.0.0**
  - Initial Release