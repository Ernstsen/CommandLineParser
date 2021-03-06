package dk.eSoftware.commandLineParser;

/**
 * Thrown when {@link CommandLineParser} were unable to find the specified {@link CommandLineParser.ConfigBuilder}
 */
public class NoSuchBuilderException extends Exception {

    NoSuchBuilderException(String message) {
        super(message);
    }
}
