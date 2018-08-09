# java-cli-menu
An interactive menu for Java CLI.

Idealy, a Java command app can be trigger by ```java -cp <class> <params>```. But sometimes, a menu based configuration is useful and convinient. The interaction looks like:

```shell 1) --grammar,-g Grammar [Csv]           specify grammar name            It should be same with file name of your .g4   2) --startRule,-r  [token]           start rule name. &quot;token&quot; is a special value.   --  R): refresh menu;  X): exit   Your input [# value]: 1 some wrong values   &gt;! Bad
 1) --grammar,-g Grammar [Csv]
          specify grammar name

          It should be same with file name of your .g4

 2) --startRule,-r  [token]
          start rule name. "token" is a special value.

 --
 R): refresh menu;  X): exit


Your input [# value]: 1 some wrong values
  >! Bad

Your input [# value]: 1 1 			# right input

Your input [# value]: R 			# refresh menu to update menu with latest input

Your input [# value]: X				# complete configuration. start run your logic

...
```

Just try to run CliMenuApp.java with input above.

This project is on the way and changes will happen any time without notification until it gets GA.

**Here is flow Java CLI Menu likes to support:**

![java-cli-menu-flow-0](README.assets/java-cli-menu-flow-0.png)
