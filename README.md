## Use
### Keybind
By default, pressing the U key sends one of the loaded messages. The messages are sent in order, cycling through the list, to avoid repeating the same one over and over again.

The key used to send a message can be changed like any other keybind (it's found at the bottom of the Controls menu).

### Messages
These are the preset messages loaded by default.

    Just go back to single-player. At least THERE you can play in Peaceful mode.
    Not even close
    Hey, it's alright. Maybe someday you'll learn to left click.
    Wow... and I thought *I* was bad at the game. You're on a whole 'nother level.
    Seriously? Is that the best you can do? I'd rather fight bots.
    I didn't realize Minecraft was used to train Stormtroopers!

### Custom Messages
Aside from the preset messages, *Trash Talk* has the ability to load user-defined messages if one wishes to add more or remove certain ones. All of this is done from the `TTMessages.json` file found in `.minecraft/mods/`. Most people's `.minecraft` directory is in `C:/Users/[Username]/AppData/Roaming/`.

To add messages, simply edit that file. Be sure the `JSON` formatting is correct—there should be a comma after every message's last quotation mark, except for the last. You can verify the file's formatting using [this website](jsonlint.com/).

If you want to revert back to the old messages, simply delete the file. When the messages are reloaded (through the hotkey or through restarting the client) the file will be recreated with the preset messages.

## Changelog
### Anticipated Changes:
 - Having each message be part of a certain category (Ranged, Melee, Team, etc.) and having each category be bound to a different key. This will produce messages that better fit one's situation.
 - Adding other types of messages, like encouragement or apologies.

### 1.0:
 - Added several new messages.
 - Added the ability to load messages from an external file.
 - Added `README.md`
 - Added the ability to toggle `/shout` command.
 - Added the ability to reload messages ingame.

### 0.1: 
 - Added basic functionality. By default, pressing the U key sends a message. There are five preset messages.