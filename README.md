# About Gus
Gus is a lightweight, cross-platform Java library that provides GUI for simple text applications. The three classes it provides can be used to output text, read text from the user either by request (polling) or by events. It can be thought of as a multifunctional console.

Gus provides the following types of GUI-windows:

  - [OutputFrame] - Display text
  - [Window] - Display and read text
  - [Console] - Display and read text by polling

The latest version of Gus is 1.0 and was released on 25.07.14.

## The Console
The main attraction in the library is the Console-class. It provides methods for outputting text, reading text by polling and several utility methods for reading different types of inputs. The examples below use this class. The examples should be read in chronological order as each example may rely on information from previous examples.

### Preparing a console for use
Before you can use the Console-class, you must import it:
```Java
import com.sakratt.gus.Console;
```
You can now create a new console:
```Java
Console console = new Console();
```
Optionally, you can give the console a title:
```Java
Console console = new Console("My Console");
```

### Working with output

#### Printing text
Outputting text in a console is similar to the standard way of printing something in Java. Using the Console-object from the previous example:
```Java
System.out.println("Hello world.");
```
Becomes:
```Java
console.println("Hello world");
```
Similarly:
```Java
console.print("I don't have a new line at the end");
console.printf("I am a % that is %.\n", "string", "formatted");
```

#### Empty lines
When working with a GUI like this, it is often useful to output a few empty lines. You could do something like:
```Java
console.println();
console.println();
console.println();
console.println("\n\n\n\n\n\n\n\n\n");
```
But that can often be tedious and hard to read. Even if you do use a loop, it is alot of unnecessary code for such a simple thing. Instead, use:
```Java
console.printNewLines(5);
```
It is easier to read and uses fewer lines of code to achieve the same goal.

#### Clearing output
If you want to get rid of what you already have outputted, you can use this method:
```Java
console.clearOutput();
```

#### Slowly displaying output
Although not often useful, you have the option to gradually display anything you print:
```Java
console.setDisplaySpeed(50); // 50 ms between each character
console.println("I am displayed one character at a time.");
```
Be cautious, even though it might look cool, it can be very annoying.

### Reading input
When reading input with the console the user is only allowed to enter input when you request it. This means that when requesting input the code will not continue to be executed until something has been inputted.

The console provides several methods for easily reading different types of input, but at its core, all input is text. Different types of input means parsing the text to get the desired type.

#### Reading text
To read a string:
```Java
String input = console.getString();
```
To better understand how this works, try running:
```Java
console.println("Please enter your name.");
String name = console.getString();
console.println("Hello, " + name + ".");
```
The first line of output will be displayed, then the execution will pause until the user enters something in the console. Lastly, the greeting will be printed. 

#### Reading an integer
When you want to read an integer, what you really want to do is read a string and parse it:
```Java
String input = console.getString();
int myInt = Integer.parseInt(input);
```
Fortunately, you don't have do this every time. Simply use:
```Java
int myInt = console.getInt();
```
This works fine as long you enter an actual integer. If you try entering a non-numerical character you will get an exception. To prevent invalid input, use this method:
```Java
int myInt = console.getInt("That is not an integer.");
```
This will attempt to parse the string the user enters. If it was successful it will return the integer, but if not, it will display the given error message and let the user try again. This process repeats until a valid integer has been entered.

You can also restrict the input to a specific range by inserting two more parameters:
```Java
int age = getInt(0, 124, "That is not a valid age!");
```
This will let the user only enter an integer with a value in the open interval [0, 124].

#### Reading a double
Reading a double works exactly like reading an integer, except the methods have different names:
```Java
double d1 = console.getDouble();
double d2 = console.getDouble("That is not a decimal number");
double d3 = console.getDouble(5.4, 10.2, "That is not a valid value");
```

#### Reading characters
Reading characters also works the same as when reading integers, except you can't read a character without providing an error message:
```Java
char c1 = console.getChar("That is not a character");
char c2 = console.getChar('a', 'z', "That is not a lower case letter.");
```

#### Reading booleans
To read a boolean from the console:
```Java
boolean myBool = console.getBoolean("I didn't understand. Enter yes or no.");
```
This works by comparing the answer to a few preset English words for saying yes and no. You can provide your own strings for parsing the input to a boolean:
```Java
String[] yes = {"yes", "1", "true", "yep"}
String[] no = {"no", 0, "false", "nope");
boolean myBool = console.getBoolean(yes, no, "I don't understand. Try again.");
```
The method will ignore the case, so you don't have to repeat every word in upper case.

#### Reading text matching a pattern (regex)
Like when reading an integer within a range, sometimes you want to read values that matches specific requirements. You can do this with strings as well, using a regex:
```Java
console.println("Enter two digits followed by a letter.");
String regex = "[0-9][0-9][a-zA-Z]";
String input = console.getString(regex, "Invalid value.");
```
This will let the user enter a value until it matches the given regex.

### Waiting for the user
Another type of input is a request for the user to enter a key. The difference between this kind of input and input from the previous examples is that the user does not have to hit the return key. Instead of submitting the input, it is instantly received when the user presses a key. The most basic form of this kind of input:
```Java
console.println("Enter your name.");
String name = console.getString();
console.waitForKey();
console.println("Your name is " + name + "!");
```
What happens here is that the third line will pause the execution. The moment the user presses a key the execution will resume. If you want to customize the prompt to the user:
```Java
console.waitForKey("Press a key on your keyboard to proceed.");
```
There are also methods for prompting the user to press specific keys, but these will not covered here.

## OutputFrame
The output frame looks like the console, except is has no input area. Its sole purpose is to output text, and it does this the same way the console does.

## Window
The window is almost identical to the console, the only difference being it does not use polling input. The window only receives input when the user submits any, and by default the user is never prevented from doing this.

License
----

MIT
