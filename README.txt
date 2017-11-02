Varun Rai
raiv95@csu.fullerton.edu

Simple four function calculator with four TextViews for displaying numbers similar to a
stack. Five Nested linear layouts inside a parent linear layout for 5 columns. The last
column is dedicated to the drop and enter functionality. Styles for the TextView and 
the Buttons were created with the parent set to the Button and TextView widgets.

An arraylist is used to store numbers if the size of the stack is greater than 4. When an item
is pushed to the stack, it also gets added at the beginning of the arraylist. Same goes for when 
the stack is popped.

If a user presses any of the operator buttons, there has to be at least two items in the 
stack, if not then a Toast message will alert the user to enter another value.

Another feature of the app is that if the current value of the first text view is equal to the 
top of the stack, and the user enters a value, that value doesn't get appended to the current text view.
It gets set as the new value of the first text view. And the other values shift up to the other text views.

The app basically does what it's supposed to do, however due to not extensively testing the
app, there may still be a minor bug involving the stack and displaying the numbers.