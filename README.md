## Use
### Keybinds
By default, pressing the U key sends one of the loaded messages. The messages are sent in order, cycling through the list, to avoid repeating the same one over and over again.

The default keybind to toggle the use of `/shout` (primarily for Hypixel) is T.

The default keybind to reload the messages (if you've made changes to the file) is P.

All these can be changed like any other keybind (found at the bottom of the Controls menu).

### Messages
These are the preset messages loaded by default.

    Just go back to single-player. At least THERE you can play in Peaceful mode.
    Not even close",
    Hey, it's alright. Maybe someday you'll learn to left click.
    Wow... and I thought *I* was bad at the game. You're on a whole 'nother level.
    Seriously? Is that the best you can do? I'd rather fight bots.
    I didn't realize Minecraft was used to train Stormtroopers!
    you fool. you absolute buffoon. you really think you can challenge me in my own realm?
    What the jiminy crickets did you just flaming do to me, you little bozo? You’re done, kiddo.
    Just as the founding fathers intended.
    Oh, that’s right, yeah just go cry to your father you little weasel. That'll help.
    God save the Queen.
    You don't have to be a military general. It's just... it would help if you knew how to play.
    where were u when player die ? i was in minecraft playing game when chat show. "n00b is kill"
    ｇｇ ｅｚ
    You ruined my video game. I ruined your life.
    Sir this is a Wendy's

### Custom Messages
Aside from the preset messages, *Trash Talk* has the ability to load user-defined messages if one wishes to add more or remove certain ones. All of this is done from the `TTMessages.json` file found in `.minecraft/mods/`. Most people's `.minecraft` directory is in `C:/Users/[Username]/AppData/Roaming/`.

To add messages, simply edit that file. Be sure the `JSON` formatting is correct—there should be a comma after every message's last quotation mark, except for the last. You can verify the file's formatting using [this website](jsonlint.com/).

If you want to revert back to the old messages, simply delete the file. When the messages are reloaded (through the hotkey or through restarting the client) the file will be recreated with the preset messages.

## Changelog
### Anticipated Changes:
 - Having each message be part of a certain category (Ranged, Melee, Team, etc.) and having each category be bound to a different key. This will produce messages that better fit one's situation.
 - Adding other types of messages, like encouragement or apologies.

### 1.1
 - Added a ton of new messages.
 - The message order is now randomized on load.

### 1.0
 - Added several new messages.
 - Added the ability to load messages from an external file.
 - Added `README.md`
 - Added the ability to toggle `/shout` command.
 - Added the ability to reload messages ingame.

### 0.1
 - Added basic functionality. By default, pressing the U key sends a message. There are five preset messages.