# java-cli-menu
An concise, interactive and versatile command line parser/menu for Java CLI.

Idealy, a Java CLI based app can be trigger by ```java -cp <class> <params>```. But sometimes, a menu based configuration is more useful and convinient. The interaction looks like:

```sh
 Wrong arguments:
   -g: [Invalid_value]
       Only 'Csv' is acceptable!
 1) -g,--grammar []: Grammar name
 2) -,--startRule [token]: Start rule

 --
 R): refresh menu;  X): exit


Your input [# value]: 1 Http
  >! Only 'Csv' is acceptable!

Your input [# value]: 1 Csv
Your input [# value]: R
 1) -g,--grammar [Csv]: Grammar name
 2) -,--startRule [token]: Start rule

 --
 R): refresh menu;  X): exit

Your input [# value]: x

Back to main, let's continue.
```

Just try to run 

- [BeanBuilderWithAnnotationSample.java](https://github.com/zhhe-me/java-cli-menu/blob/master/src/test/java/me/zhhe/cli/menu/sample/BeanBuilderWithAnnotationSample.java)  (extract argument defintion via annotation + naming convention or Java Bean) **Recommanded**
- [BeanBuilderSample.java](https://github.com/zhhe-me/java-cli-menu/blob/master/src/test/java/me/zhhe/cli/menu/sample/BeanBuilderSample.java)  (extract argument defintion via naming convention or Java Bean) **No configuration**
- [BasicBuilderSample.java](https://github.com/zhhe-me/java-cli-menu/blob/master/src/test/java/me/zhhe/cli/menu/sample/BasicBuilderSample.java) (set up argument definition via code) **Everything in your control**
- [ChainedBuilderSample.java](https://github.com/zhhe-me/java-cli-menu/blob/master/src/test/java/me/zhhe/cli/menu/sample/ChainedBuilderSample.java) (set up argument via chained/multiple builders)

with input above.

Here is code from BeanBuilderWithAnnotationSample.java:

```java
public class BeanBuilderWithAnnotationSample {

    // Annotation can help supply extra info, like arg name, description
    @Option(name = "g", description = "Grammar name")
    private String grammar;

    // use field name when neither name nor longArgName is specified.
    @Option(description = "Start rule")
    private String startRule;

    // matched by naming convention
    public void setGrammar(final String value) {
        if (!"Csv".equals(value))
            throw new IllegalArgumentException("Only 'Csv' is acceptable!");
        grammar = value;
    }

    // ignored due to setStartRule2
    public void setStartRule(final String value) {
        startRule = value;
    }

    // Annotation is preferred to reflection/naming convention
    @Setter("startRule")
    public void setStartRule2(final String value) {
        startRule = "2-" + value;
    }

    public static void main(final String... args) {
        final BeanBuilderWithAnnotationSample sample = new BeanBuilderWithAnnotationSample();
        // mock arguments
        final String[] mockedArgs = {"-g", "Invalid_value", "--startRule", "token"};

        new BeanMenuBuilder().bean(sample).build(mockedArgs).render();

        // continue your business after all setting is done.
        System.out.println("\nBack to main, let's continue.");
    }
}
```



_This project is on the way and changes will happen any time without notification until it gets GA._

**Here is flow Java CLI Menu likes to support:**

![java-cli-menu-flow-0](README.assets/java-cli-menu-flow-0.png)
