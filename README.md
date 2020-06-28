# wheel-of-fortune
JavaFX Project for Wheel of Fortune
"wheel of fortune"type game, consisting of two main panels:

•the upper one - containing empty fields for the letters of the phrase/words to beguessed

•the lower one - representing the circle divided into at least 10 parts with differentprize amounts and the "play"button starting the draw;

•there should also be a display showing the current player’s score

In addition, the application will contain a settings button that will open a windowconsisting of two lists (ListView) containing the previous phrases loaded in the file, theright one describing phrases that can be used in the game, and buttons that allow you totransfer parameters between lists. The window will also contain a component that allowsyou to enter a new phrase.

The game:

1. the player starts in the settings, choosing which phrases can be drawn

2. from the pool of selected phrases, one is drawn. Masked letters corresponding tothe phrase’s length are shown.

3. the player hits the ”play” button, which starts the draw animation

4. after drawing the field and the corresponding prize, a dialog box opens, allowingthe player to enter the guessed letter

5. if the phrase actually contains that letter, the player receives the drawn prize.Otherwise, he loses one of three opportunities.
