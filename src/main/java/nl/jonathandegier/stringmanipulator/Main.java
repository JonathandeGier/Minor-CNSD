package nl.jonathandegier.stringmanipulator;


import nl.jonathandegier.stringmanipulator.controllers.StringController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] agrs) {

        // make context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.refresh();

        // set profile
        context.getEnvironment().setActiveProfiles("caps"); // reverse | caps

        // scan the application to register all the beans
        context.scan("nl.jonathandegier.stringmanipulator");

        // get a bean and do stuff with it
        StringController controller = context.getBean("stringController", StringController.class);

        System.out.println("Manipulated string: \"Hello World!\"");
        System.out.println(controller.manipulate("Hello World!") + "\n");

        System.out.println("Words in string: \"Hello World!\"");
        System.out.println(controller.wordsInString("Hello World!") + "\n");

        System.out.println("Words in string: \"Hello World!\"");
        System.out.println(controller.wordsInString("Hello World!") + "\n");

        System.out.println("Words in string: \"A random sentence\"");
        System.out.println(controller.wordsInString("A random sentence"));
    }
}
