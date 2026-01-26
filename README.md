# uzerai's AnimalHusbandry mod

> **⚠️ Warning: Early Access**    
> The game Hytale is in early access, and so is this project! Features may be
> incomplete, unstable, or change frequently. Please be patient and understanding as development
> continues.

## Introduction
A mod that introduces simple animal husbandry, built with minimal plugin interference from the core Hytale system.

Many thanks to [this hytale modding template](https://github.com/Build-9/Hytale-Example-Project); for the gradle setup.

## Current features:

- The main `Template_AnimalHusbandry_Enabled.json` file (under `src/main/resources/Server/Npc/Roles/Creatures/_Core/Templates`) which exposes
    all 3 main behaviours, Growth, Domestication and Breeding.

- Growth, governed primarily by the `AdultRole` parameter of the template, along with the `ChildToAdultTimeRange`. Customize your own assets with
    custom variables to enable them to grow into whatever `AdultRole` you'd like. Sensible defaults are enabled.

- Domestication, governed entirely by the `IsDomesticable` parameter of the template. This is the flag which, in addition to the configurable
    `FeedTimeout` parameter, enables feeding and custodianship of animals. This is a **requirement** for the Breeding functionality.

- Breeiding, governed by both the above domestication system, and `ChildRole` parameter of the template. When both are enabled, a successfully domesticated 
    animal, upon the next feeding, will enter a breeding state, looking for another NPC of the same Role which is also in a breeding state. When found,
    they approach eachother and spawn 1-2 NPCs of the `ChildRole`.