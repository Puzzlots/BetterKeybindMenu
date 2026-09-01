# Puzzle Loader Example Mod
> The example mod for [Puzzle Loader Cosmic](https://github.com/PuzzlesHQ/puzzle-loader-cosmic)

## How To Test Client & Server
For Client testing in the dev env, you can use the `gradle runModdedClient` task
For Server testing in the dev env, you can use the `gradle runModdedServer` task

## how to add in to CR

- the keybind class needs to replace `finalforeach.cosmicreach.settings.Keybind`
- keyBindRegistry has most of the replacements for ControlSettings (was made in 0.5.26)
- for the lang key i made a scripts to move them to a new file 
- you don't need any of the files in `assets.base.textures.ui.keys.dev` or `assets.base.backup`
- the new gamecontrollerdb.txt fix controller support for xbox x controllers on linux