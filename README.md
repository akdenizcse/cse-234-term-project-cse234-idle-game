[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/FbxDndiU)


# Idle Game
This is an idle game made with Kotlin using composables, the app includes weapons with levels, materials(upgrades) that earn coins for you, but you also have the option to deal damage to the slime enemy on the weapons tab to earn even more money.

User authentication is done through firebase and user data is stored with firestore database.

## User 
   - If you would like to register an account use a password longer than 5 characters as that is what firebase authentication desires.

## Authors
Batuhan Yılmaz - 20220808068 - BatY0
Emre Kemal Aksel - 20220808065 - Cherub26

## Task Allocation
//Finish this later

## Screens
Our Idle Game app has these screens:

01. [Login Screen](#1-login-screen)
02. [Register Screen](#2-register-screen)
03. [Forgot Password Screen](#3-forgot-password-screen)
04. [Weapons Tab](#4-weapons-tab)
05. [Upgrades Tab](#5-upgrades-tab)
06. [Store Tab](#6-store-tab)
07. [Settings](#7-settings)

### 1. Login Screen
In this screen the user can either choose to sign in to an already account, or they can click "Forgot Password?" to go to the forgot password page, or they can click "Don't have account? Sign Up?" if they don't have an account and would like to create one. On every launch of the app our login screen, register screen and forgot password screen backgrounds are randomized to add a little bit of variety to them.
<p width="100%">
  <img width="20%" src="https://github.com/akdenizcse/cse-234-term-project-cse234-idle-game/assets/110939979/212e3022-db7c-4e9c-b5b9-5fd4ff14b181">
</p>

### 2. Register Screen
In this screen the user can sign up, or if they already have an account they can click "Already have an account? Sign in!" to go to the login screen.
<p width="100%">
  <img width="20%" src="https://github.com/akdenizcse/cse-234-term-project-cse234-idle-game/assets/110939979/53ee167c-84ce-408d-9893-a9bb5b7234c8">
</p>

### 3. Forgot Password Screen
In this screen the user can reset their password if they already have an account and forgot their password, a firebase reset password email is sent once clicked. The user can click "Remember your Password?" to go back to the login screen, or they can click "Don't have account? Sign Up!" to go to the register screen to create an account.
<p width="100%">
  <img width="20%" src="https://github.com/akdenizcse/cse-234-term-project-cse234-idle-game/assets/110939979/e9f02ff3-8f19-4f05-ac9b-5144818952a7">
</p>

### 4. Weapons Tab
Once the user is logged in first thing first they will be directed to the weapons tab this is where the user can upgrade weapons and increase their levels, and they can also deal damage to the slime to earn more money. On the top left they can see their coins, gems and on the right they can see their total earned money per second and they can also click the gear icon to be brought into settings, these all can be seen regardless on which tab the user is in be it weapons, upgrades or store. On the bottom the user can use the navigation bar to navigate between the weapons, upgrades and store tabs.
<p width="100%">
  <img width="20%" src="https://github.com/akdenizcse/cse-234-term-project-cse234-idle-game/assets/110939979/fe393ab5-7984-4b06-a7dc-8be9344f6ba9">
</p>

### 5. Upgrades Tab
In the upgrades tab the user can upgrade their weapons material to increase their income.
<p width="100%">
  <img width="20%" src="https://github.com/akdenizcse/cse-234-term-project-cse234-idle-game/assets/110939979/9585fe26-5c26-48ef-9a5d-c9c9d11e9e08">
</p>

### 6. Store Tab
In the store tab the user can buy gems once they click on the gem buy buttons they will get a pop up and if they click buy they will buy the gems, they can use the gems on either time warps or multipliers. Time warps gets you a certain amount of hour worth of income instantly once bought. Income multipliers give you a global multiplier that works for every single weapon.
<p width="100%">
  <img width="20%" src="https://github.com/akdenizcse/cse-234-term-project-cse234-idle-game/assets/110939979/73816cb0-a0c3-4046-8573-24f6770a563b" style="margin-right: 50px;">
  <img width="20%" src="https://github.com/akdenizcse/cse-234-term-project-cse234-idle-game/assets/110939979/5567a247-e257-4623-9f68-7e7e025f9442">
</p>

### 7. Settings
On settings the user can logout if they want to or they can enable or disable sounds and music of the game.
<p width="100%">
  <img width="20%" src="https://github.com/akdenizcse/cse-234-term-project-cse234-idle-game/assets/110939979/5056e6a9-a21c-44e7-8199-a3dc3d2ac7fb">
</p>
