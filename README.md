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
1. You can write messages to Kurisu in the text input field and send it through button with envelope icon. Wait some time until in white field will appear her response.
2.  If you don't like the answer or you think it doesn't match the character, you can generate the answer again. Just tap on the "menu" button in the upper-right part of the screen and select "regenerate". After that, the "send" button will change the icon to "regeneration" and after clicking on it, the response will be generated.
3. If you think that your last message or messages were incorrect, you can completely remove them from the current dialog by selecting "remove message" in the menu.
4. If you think that the current emotion does not correspond to the context of the dialogue, then you can tap on the character's face and select "change emotion". Then choose the emotion that you think is more appropriate. This emotion will remain in the current chat and the character will "think" that this is exactly the emotion what she had.
5. If you want to start the dialog again, then you should select "Start new chat" from the right-top menu.The old dialog will be saved to the dialog history, which can also be opened via the menu.

### Settings
You can go to the application settings via the right-top menu.
1. **Enabling/disabling the ability of the character to end the dialogue**. If the character has finished the dialogue, then he turns away, and the send button disappears. In this case, you can either regenerate the response, delete your last message, or start a new dialog.
2. **Output max length.** Number of tokens to be generated. Higher values will take longer to generate.
3. **Context memory size.** Number of context tokens to submit to the AI for sampling.
4. **Temperature.** Randomness of sampling. Higher values can increase creativity, but make the output less meaningful. Lower values will make the output more predictable, but it may become more repetitive.
5. **Repetition penalty.** Used to penalize words that were already generated or belong to the context.
6. **Repetition penalty range.** If set higher than 0, only applies repetition penalty to the last few tokens of the story rather than applying it to the entire story. The slider controls the amount of tokens at the end of your story to apply it to.

