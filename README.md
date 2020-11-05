# TodoList app for Miquido
 

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies) 
* [Screenshot](#screenshot) 
* [Download](#download) 
## General info
TodoList is example of using Firebase Cloud Firestore with MVP pattern.

#**Goals Complete**#\
*:ok_hand: Single item should consist of title (with text limit 30 characters), description (with text limit
200 characters), automatically added date of creation and optional icon url (typed or
pasted by user).\
*:ok_hand: Item creation form screen should be accessible by floating action button.\
*:ok_hand: Every item on the list should contain an icon (eventually placeholder when it is null), title
and creation date.\
*:ok_hand:Clicking on the specific item should redirect to the item edition form.\
*:ok_hand:Long clicking on the specific item should ask if the user wants to delete the item.\
*:ok_hand:Use pagination to load items (page size 30). Next page should be loaded when the user
reaches the bottom of the list.\
*:ok_hand:List should be automatically updated when a collection is modified on another device or
through the firebase console (you can reset pagination to initial state).\
*:ok_hand:Loading animation should be displayed during data fetching.\
*:ok_hand:Errors should be handled and the proper error message should be displayed on the
screens

## Technologies
Project is created with:

* Depedency injection with Dagger2
* Firebase Cloud Firestore
* Some Test with Esspresso, Mockito
* Navigation Jetpack 
* RxJava 
* MVP
* simple UI/UX 
* Kotlin

## Screenshot
![Recordit GIF](https://media.giphy.com/media/Nxou4MHti7aGdhx9Kn/giphy.gif)

## Download 
**https://anioncode.pl/miquido/TodoList.apk**

## License open source 

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)

- **[MIT license](http://opensource.org/licenses/mit-license.php)**
- Copyright 2020 © <a href="https://github.com/Lukieoo" target="_blank">Paweł Krzyściak</a>.
