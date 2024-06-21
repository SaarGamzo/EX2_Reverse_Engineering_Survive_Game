# Mobile Security course - Ex No. 2 - Reverse Engineering Survive Game apk

<img src="https://github.com/SaarGamzo/EX2_Reverse_Engineering_Survive_Game/assets/88244818/c4df448a-c699-4c7a-b579-997326e85cea" width="400" height="800">

## Description
* The game mechanics involve user interactions based on the user's ID number.
* The 7th digit of the ID number (starting to count from 0 of course) determines the survival place from this array: https://pastebin.com/raw/T67TVJG9
![image](https://github.com/SaarGamzo/EX2_Reverse_Engineering_Survive_Game/assets/88244818/231a5c76-73d0-44ef-996e-a145e1ffc13e)

* Each digit from the user's ID number is being saved into an array (size = 9) after modulo 4 action (digit % 4), which means the saved values can be: 0,1,2,3. 
* After the user click on Start Game button he being navigated to the Activity_Game screen, which shows 4 arrows (up,right,down,left).
* Each arrow have an integer value (0,1,2,3).
* The user should click 9 times on the arrows with the correct saved sequence in order to "Survive", if a wrong arrow will be clicked - the user will get a failure message.
### Values of arrows:
* Up arrow = 2
* Right arrow = 1
* Down arrow = 3
* Left arrow = 0

### Example:
* ID = 123456789
* Survive place will be the 7 position character (which is 8) = Michigan
* Array of id digits modulo 4 will be: [1,2,3,0,1,2,3,0,1]
* Arrow correct sequence will be: [Right, Up, Down, Left, Right, Up, Down, Left, Right]
<img src="https://github.com/SaarGamzo/EX2_Reverse_Engineering_Survive_Game/assets/88244818/778c89f4-5c61-44de-91bd-38d227ba009c" width="400" height="800">

## Steps towards solution
1. **Download apk and decompile to have all needed resources**
2. **Create new empty project on android studio**
3. **Import classes & res folder**
4. **Adjust manifest to match project package name and needed SDK version**
5. **Add debug messages to every method, every variable before & after each change to track the flow**
6. **Add internet permissions to the application**
7. **Fix needed used values such as url string (needed to remove some characters so the url will be the correct one**
8. **Follow the code & application flow with debug messages and find the correct needed sequence in order to survive**
