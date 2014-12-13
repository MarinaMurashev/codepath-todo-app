# Simple Todo Android App

This is an Android demo application for managing Todo items: add, edit, and delete items. See the [Todo App Tutorial](http://courses.codepath.com/snippets/intro_to_android/prework) for a step-by-step tutorial for creating this app.

Time spent: 14 hours spent in total

Completed user stories:

 * [x] Required: User add todo items one-by-one.
 * [x] Required: User can delete a todo item by long clicking on an item.
 * [x] Required: User can edit a todo item by clicking on an item.
 * [x] Optional: Use custom adapter for the items in the list.

Notes:

Spent time on the following:

* Not allowing user to submit blank items, both from the main screen and from the edit screen.
* Keyboard experience: keyboard should hide on the main screen when an item is successfully added, and when a user returns to that screen after editing an item.
* Passing the Item object as serializable extras instead of just string extras so that the MainActivity class doesn't have to know the details of how the EditActivity class will operate on the Item object.
* Styling the item list in the main activity, as well as styling the edit activity.

Walkthrough of all user stories:

![Video Walkthrough](codepath_todo_app_gif.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).