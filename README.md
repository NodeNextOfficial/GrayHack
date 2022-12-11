
# GrayHack
![](https://img.shields.io/github/downloads/graydev/GrayHack/total?style=flat-square)
![](https://img.shields.io/tokei/lines/github/graydev/GrayHack?style=flat-square)
![](https://img.shields.io/github/languages/code-size/graydev/GrayHack?style=flat-square)
![](https://img.shields.io/github/last-commit/graydev/GrayHack?style=flat-square)
![](https://img.shields.io/badge/daily%20commit-yes-blue?style=flat-square)
![](https://img.shields.io/discord/620600892718055434?style=flat-square)

Bloc Game utility mod for Fabric 1.18 and 1.19.

> Website: https://grayhack.org/  
> Discord: https://grayhack.org/discord

## Showcase
<details>
 <summary>Images</summary>

 ![](https://res.grayhack.org/images/ClickguiShowcase.jpg)

 ![](https://res.grayhack.org/images/RenderShowcase.jpg)

</details>

## Installation
### For normal people

Follow the Instructions on the [download page]().

### For (200 IQ) developers

Download the branch with the version you want to work on.  
Start A Command Prompt/Terminal in the main folder.  
Generate the needed files for your preferred IDE.  

***Eclipse***

  On Windows:
  > gradlew genSources eclipse
  
  On Linux:
  > chmod +x ./gradlew  
  >./gradlew genSources eclipse

  Start a new workspace in eclipse.
  Click File > Import... > Gradle > Gradle Project.
  Select the Main folder.
  
***IntelliJ***

  On Windows:
  > gradlew genIdeaWorkspace
  
  On Linux:
  > chmod +x ./gradlew  
  >./gradlew genIdeaWorkspace

  In idea click File > Open.
  Select build.gradle in the main folder.
  Select Open as Project.

***Other IDE's***

  Use [this link](https://fabricmc.net/wiki/tutorial:setup) for more information.
  It should be pretty similar to the eclipse and idea setup.
  
###### *To get the source code of Pre-1.17 versions, use [this]() commit and select the folder of the version you want.*

## Recommended Mods

Here are some nice to have mods that are compatible with GrayHack, none of these require Fabric API.

### [Multiconnect](https://github.com/Earthcomputer/multiconnect) or [ViaFabric](https://github.com/ViaVersion/ViaFabric)
Mods that allows you to connect to any 1.8-1.18 server from a 1.18 client.

### [Baritone](https://github.com/cabaletta/baritone)
Baritone allows you to automate tasks such as walking, mining or building.

### [Sodium](https://www.curseforge.com/minecraft/mc-mods/sodium), [Lithium](https://www.curseforge.com/minecraft/mc-mods/lithium) and [Phosphor](https://www.curseforge.com/minecraft/mc-mods/phosphor)
Fixes Mojang's spaghet.

## License

If you are distributing a custom version of GrayHack or a mod with ported features of GrayHack, you are **required** to disclose the source code, state changes, use a compatible license, and follow the [license terms](https://github.com/GrayDrinker420/GrayHack/blob/master/LICENSE).
