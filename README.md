# java-cli-menu
An clean, interactive command line parser/menu for Java CLI.

Idealy, a Java CLI based app can be trigger by ```java -cp <class> <params>```. But sometimes, a menu based configuration is useful and convinient. The interaction looks like:

```shell 1) --grammar,-g Grammar [Csv]           specify grammar name            It should be same with file name of your .g4   2) --startRule,-r  [token]           start rule name. &quot;token&quot; is a special value.   --  R): refresh menu;  X): exit   Your input [# value]: 1 some wrong values   &gt;! Bad
 Wrong arguments:
   -g: [Invalid_value]
       Only 'Csv' is acceptable!
 1) -g,--grammar []: Grammar name
 2) -,--startRule [token]: Start rule of this parser

 --
 R): refresh menu;  X): exit


Your input [# value]: 1 Http
  >! Only 'Csv' is acceptable!

Your input [# value]: 1 Csv
Your input [# value]: R
 1) -g,--grammar [Csv]: Grammar name
 2) -,--startRule [token]: Start rule of this parser

 --
 R): refresh menu;  X): exit

Your input [# value]: x

Back to main, let's continue.
```

Just try to run 

- [BeanBuilderSample.java](https://github.com/zhhe-me/java-cli-menu/blob/master/src/test/java/me/zhhe/cli/menu/sample/BeanBuilderSample.java)  (extract argument defintion via naming convention or Java Bean)
- [BasicBuilderSample.java](https://github.com/zhhe-me/java-cli-menu/blob/master/src/test/java/me/zhhe/cli/menu/sample/BasicBuilderSample.java) (set up argument definition via code)
- [ChainedBuilderSample.java](https://github.com/zhhe-me/java-cli-menu/blob/master/src/test/java/me/zhhe/cli/menu/sample/ChainedBuilderSample.java) (set up argument via chained/multiple builders)

with input above.

This project is on the way and changes will happen any time without notification until it gets GA.

**Here is flow Java CLI Menu likes to support:**

![java-cli-menu-flow-0](README.assets/java-cli-menu-flow-0.png)
