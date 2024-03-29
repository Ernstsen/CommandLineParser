package dk.eSoftware.commandLineParser.generalized.documentation;

import dk.eSoftware.commandLineParser.Configuration;
import dk.eSoftware.commandLineParser.generalized.annotations.Help;
import dk.eSoftware.commandLineParser.generalized.annotations.Name;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Utility class for generating a CLI-friendly help-string from an annotated implementation of the
 * {@link Configuration} interface
 */
public class HelpUtilities {

    public static String generateHelpString(Class<? extends Configuration> subjectClass) {
        return subjectClass.getSimpleName() + ": \n" + generateFieldsString(1, subjectClass);
    }

    private static String generateFieldsString(int level, Class<? extends Configuration> subjectClass) {
        final StringBuilder sb = new StringBuilder();

        for (Field field : subjectClass.getDeclaredFields()) {
            if(Modifier.isStatic(field.getModifiers())){
                continue;
            }

            for (int i = 0; i < level; i++) {
                sb.append("\t");
            }
            String helpString = getHelpString(field);
            sb.append(getName(field)).append("(").append(field.getType().getSimpleName()).append("): ").append(helpString).append("\n");

            if (Configuration.class.isAssignableFrom(field.getType())) {
                //noinspection unchecked
                sb.append(generateFieldsString(level + 1, (Class<? extends Configuration>) field.getType()));
            }

        }

        return sb.toString();
    }

    private static String getName(Field field) {
        final Name annotation = field.getAnnotation(Name.class);
        return annotation != null ? annotation.name() : field.getName();
    }

    private static String getHelpString(Field field) {
        final Help annotation = field.getAnnotation(Help.class);
        return annotation != null ? annotation.helpString() : "";
    }
}
