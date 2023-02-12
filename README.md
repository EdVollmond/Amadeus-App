# HOW TO LAUNCH
1.	Download the APK file of the application in the "releases" section and install it to your device.
2.	Host KoboldAI with PygmalionAI on a virtual machine.
	
	2.1.	Via Google Colab: [LINK](https://colab.research.google.com/github/koboldai/KoboldAI-Client/blob/main/colab/GPU.ipynb "GPU edition"). You need to set "Pygmalion6B" in the list and click "play" button on the left side of code block.
	
	[![img1](https://i.ibb.co/pvWtqx0/redme1.jpg "img1")](https://i.ibb.co/pvWtqx0/redme1.jpg "img1")
	
	If Google provides you with GPU or TPU computing units, then you need to wait until the model is fully loaded and copy URL address in the following format:
	[![](https://i.ibb.co/mypnPDT/image.png)](https://i.ibb.co/mypnPDT/image.png)
	
	2.2. Via Kaggle: [LINK](https://www.kaggle.com/code/noellenemoia/koboldai-pygmalion-6b "LINK"). You need to register on Kaggle and verivy your phone number. After that clik on "Copy and edit" button and run notebook via "play" button on the left side of code block.  Before starting, make sure that the GPU or TPU accelerator is selected in the settings on the right and the Internet connection is enabled.
	[![](https://i.ibb.co/597zkwX/image.png)](https://i.ibb.co/597zkwX/image.png)
	[![](https://i.ibb.co/jWmYY4V/image.png)](https://i.ibb.co/jWmYY4V/image.png)
	[![](https://i.ibb.co/HDhVxwJ/image.png)](https://i.ibb.co/HDhVxwJ/image.png)
	
	
	After that you need to wait until the model is fully loaded and copy URL address in the following format:
	[![](https://i.ibb.co/MhwsvDn/image.png)]([https://i.ibb.co/HDhVxwJ/image.png](https://i.ibb.co/MhwsvDn/image.png))
	
3. Paste URL address in the appropriate field of launch screen of the Amadeus app and tap "Connect".
[![](https://i.ibb.co/s5yGM8v/image.png)](https://i.ibb.co/s5yGM8v/image.png)

If everything is done correctly, then after loading you will be able to communicate with Amadeus.


# FEATURES
### Chat
You can write messages to Kurisu in the text input field and send it through "Send" button with envelope icon. Wait some time until in white field will appear her response.
1.  If you don't like the answer or you think it doesn't match the character, you can generate the answer again. Just tap on the "Menu" button in the upper-right part of the screen and select "Regenerate". After that, the "Send" button will change the icon to "Regeneration" and after clicking on it, the response will be generated.
2. If you think that your last message or messages were incorrect, you can completely remove them from the current dialog by selecting "Remove message" in the menu.
3. If you think that the current emotion does not correspond to the context of the dialogue, then you can tap on the character's face and select "Change emotion". Then choose the emotion that you think is more appropriate. This emotion will remain in the current chat and the character will "think" that this is exactly the emotion what she had.
4. If you want to start the dialog again, then you should select "Start new chat" from the right-top menu.The old dialog will be saved to the dialog history, which can also be opened via the menu.

### Settings
You can go to the application settings via the right-top menu.
1. **Enabling/disabling the ability of the character to end the dialogue**. If the character has finished the dialogue, then he turns away, and the send button disappears. In this case, you can either regenerate the response, delete your last message, or start a new dialog.
2. **Output max length.** Number of tokens to be generated. Higher values will take longer to generate.
3. **Context memory size.** Number of context tokens to submit to the AI for sampling.
4. **Temperature.** Randomness of sampling. Higher values can increase creativity, but make the output less meaningful. Lower values will make the output more predictable, but it may become more repetitive.
5. **Repetition penalty.** Used to penalize words that were already generated or belong to the context.
6. **Repetition penalty range.** If set higher than 0, only applies repetition penalty to the last few tokens of the story rather than applying it to the entire story. The slider controls the amount of tokens at the end of your story to apply it to.

After changing the settings, you can save them by tapping the big "Save changes" button. If you close the settings screen, the settings will not be saved. If you want to reset the settings to the default settings, then you should click on the "reset" button in the upper-right part of the screen and then click "Save changes".

### Character edit
You can edit your character's personality by tapping on her face and selecting "Edit character". The information here is very similar like character description for PygmalionAI or Character.ai . You can read more about creating a character's description in the [PygmalionAI Discord server](https://discord.gg/pygmalionai "PygmalionAI Discord server").
1. **Name.** Name of your character.
2. **Personality.** Short description of character. Base information about she.
3. **Greeting.** The standard greeting of the character.
4. **World scenario.** A brief description of the situation in which the dialogue takes place.
5. **Example dialogue.** An example or examples of how a character communicates with a user. Must be in the following format:
```
<START>
Character: (emotion: ...) ...
You: ...
Character: (emotion: ...) ...
```
6. **Extra greetings.** You can specify more additional options for how the character will start the dialogue.

Remember! Try not to write in too much detail in this page, it takes away context memory!

After editing the fields, you can save them by tapping the big "Save changes" button. If you close the editing screen, the changes will not be saved. If you want to reset the character personality to the default, then you should click on the "Reset" button in the upper-right part of the screen and then click "Save changes".

### Custom characters
At the moment, there is some possibility to change Kurisu Makise to any other VN character. To do this, you need to put 17 sprites with the emotions of the character in the "drawable" folder inside the .apk package. The files must have a name in the following format:
> charactername_emotion_emotionname.png

List of emotion names:
> neutral
> angry
> apathetic
> blushed
> displeased
> glad
> happy
> moody
> playful
> sad
> serious
> skeptical
> surprised
> thoughtful
> tired
> calm
> back

"Back" is an emotion when a character finishes a dialogue.

It's okay if the same sprite is used for different emotions. The main thing is that there should be 17 files.

The name of the character in the file name is written in lowercase. If there is a space in the name, it is replaced by underlining.

For example, if the character's name is **Amane Suzu**, then the sprite file where she **blushes** should be called like this:
> amane_suzu_emotion_blushed.png

After you have placed the files in the .apk package, you need to enter the name of the character in the "Name" field of the character editing screen and at the very bottom put the toggle switch "Custom character sprites" in the enabled position.
