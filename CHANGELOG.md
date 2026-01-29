# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [v1.1.0] - 2026-01-29
### :sparkles: New Features
- [`b7736bc`](https://github.com/uzerai/hytale-animal-husbandry/commit/b7736bceddfe8e1953e786b1440742edefa04973) - **ignore-culling**: add optional ability for domesticated animals to ignore culling *(commit by [@uzerai](https://github.com/uzerai))*

### :bug: Bug Fixes
- [`79808e1`](https://github.com/uzerai/hytale-animal-husbandry/commit/79808e1dbe284e7855fdab3302e9ffe6b93ad01e) - **breeding**: update breeding behaviour checks to no longer falsely imply animals were going to breed when one was on cooldown *(commit by [@uzerai](https://github.com/uzerai))*
- [`34b9f0a`](https://github.com/uzerai/hytale-animal-husbandry/commit/34b9f0a1f4ee585fbcd1b7591732cc4962e9629d) - **timeouts**: update time range for growth so that the moment can't be 'skipped' if the entity isn't loaded when the age sensor window is open, update cooldowns to be the same duration range, makes things consistent *(commit by [@uzerai](https://github.com/uzerai))*

### :wrench: Chores
- [`67190eb`](https://github.com/uzerai/hytale-animal-husbandry/commit/67190eb5b26b0843e73141ac5d3e16b8e15f8d1a) - **ci**: reinstate release-publish.yml *(commit by [@uzerai](https://github.com/uzerai))*
- [`c717d80`](https://github.com/uzerai/hytale-animal-husbandry/commit/c717d80f4a388455da68081501934556dd9a276f) - **todos**: add todo comments to reference back to at another time *(commit by [@uzerai](https://github.com/uzerai))*
- [`3c92122`](https://github.com/uzerai/hytale-animal-husbandry/commit/3c92122bce6ae0d9191e64e502f5a282cb85f17b) - **ci**: remove stupid sha1 hashing idea, forgot that github provides Sha256 hashes in their release UI *(commit by [@uzerai](https://github.com/uzerai))*
- [`8d5b803`](https://github.com/uzerai/hytale-animal-husbandry/commit/8d5b803b1dcf4f0eff625a21e82fd13c85008357) - **ci**: update post-release version update action *(commit by [@uzerai](https://github.com/uzerai))*
- [`ccc07cb`](https://github.com/uzerai/hytale-animal-husbandry/commit/ccc07cbfa5aaac3a167d8a4eba6569d1ab37423d) - **gradle**: update version manually this time, since post-release didn't run *(commit by [@uzerai](https://github.com/uzerai))*


## [v1.0.2] - 2026-01-28
### :wrench: Chores
- [`ca7ce43`](https://github.com/uzerai/hytale-animal-husbandry/commit/ca7ce437ecd6a53a95d22f6f1920248d320c601b) - **gradle**: update gradle dependency to read from official repository for build time instead of using locall install *(commit by [@uzerai](https://github.com/uzerai))*
- [`2174c1f`](https://github.com/uzerai/hytale-animal-husbandry/commit/2174c1f409620d38fd3d596dbb1cc1d9730584eb) - **ci**: add ci functionality, initial release management stuff *(commit by [@uzerai](https://github.com/uzerai))*
- [`896d310`](https://github.com/uzerai/hytale-animal-husbandry/commit/896d31093e19d264944b03a2f878f1f575266f24) - **gradle**: update gradle wrapper version *(commit by [@uzerai](https://github.com/uzerai))*
- [`72365d3`](https://github.com/uzerai/hytale-animal-husbandry/commit/72365d393be9efb39c68c296ca4eeef48c2e1346) - **project-name**: rename to animal-husbandry to remain consistent in naming *(commit by [@uzerai](https://github.com/uzerai))*
- [`7ffde8a`](https://github.com/uzerai/hytale-animal-husbandry/commit/7ffde8a3b3ac948c170583beb518e6843d185409) - **ci**: skip hytale installation folder check when building in CI *(commit by [@uzerai](https://github.com/uzerai))*

[v1.0.2]: https://github.com/uzerai/hytale-animal-husbandry/compare/v1.0.1...v1.0.2
[v1.1.0]: https://github.com/uzerai/hytale-animal-husbandry/compare/v1.0.3...v1.1.0
