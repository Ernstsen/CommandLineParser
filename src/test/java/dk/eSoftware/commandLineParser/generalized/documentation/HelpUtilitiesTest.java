package dk.eSoftware.commandLineParser.generalized.documentation;

import dk.eSoftware.commandLineParser.generalized.configuratinos.ComplexConfigurationPartlyAnnotated;
import dk.eSoftware.commandLineParser.generalized.configuratinos.PartlyAnnotatedConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HelpUtilitiesTest {

    @Test
    public void shouldPrintHelpStringWith2Descriptions() {
        // Arrange

        // Act
        String result = HelpUtilities.generateHelpString(
                PartlyAnnotatedConfiguration.class
        );

        System.out.println(result);
        // Assert
        assertTrue(result.contains("\tbooleanValue1(boolean)"));
        assertTrue(result.contains(" boolean 1 help"));
        assertTrue(result.contains("\tbooleanValue2(boolean)"));
        assertTrue(result.contains("\tintegerValue1(int)"));
        assertTrue(result.contains(" integer 1 help"));
        assertTrue(result.contains("\tintegerValue2(int)"));
    }

    @Test
    public void shouldPrintHelpInTwoLevels() {
        // Arrange

        // Act
        String result = HelpUtilities.generateHelpString(
                ComplexConfigurationPartlyAnnotated.class
        );

        System.out.println(result);
        // Assert

        assertTrue(result.contains("\tinner(PartlyAnnotatedConfiguration)"));
        assertTrue(result.contains(" Inner configuration"));
        assertTrue(result.contains("\t\tbooleanValue1(boolean)"));
        assertTrue(result.contains(" boolean 1 help"));
        assertTrue(result.contains("\t\tbooleanValue2(boolean)"));
        assertTrue(result.contains("\t\tintegerValue1(int)"));
        assertTrue(result.contains(" integer 1 help"));
        assertTrue(result.contains("\t\tintegerValue2(int)"));
        assertTrue(result.contains("\tval(String)"));
        assertTrue(result.contains("Some value"));

    }
}