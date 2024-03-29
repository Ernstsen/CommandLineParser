package dk.eSoftware.commandLineParser.generalized;

import dk.eSoftware.commandLineParser.NoSuchBuilderException;
import dk.eSoftware.commandLineParser.SingletonCommandLineParser;
import dk.eSoftware.commandLineParser.UnknownCommandException;
import dk.eSoftware.commandLineParser.WrongFormatException;
import dk.eSoftware.commandLineParser.generalized.configuratinos.*;
import dk.eSoftware.commandLineParser.generalized.documentation.HelpUtilities;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class GeneralConfigurationBuilderTest {

    @Test
    public void shouldReadValuesIntoConfigurationObject() {
        // Arrange
        final GeneralConfigurationBuilder<SimpleConfigurationClassBoxedTypes> builder =
                new GeneralConfigurationBuilder<>(SimpleConfigurationClassBoxedTypes.class);

        final SingletonCommandLineParser<SimpleConfigurationClassBoxedTypes> parser = new SingletonCommandLineParser<>(builder);

        String stringVariable1Expected = "heheTest";
        String stringVariable2Expected = "alsoTest";
        int integerValue1Expected = 12;
        int integerValue2Expected = 2342;
        Float floatValue1Expected = 2.4f;
        Float floatValue2Expected = 4.2f;

        // Act
        SimpleConfigurationClassBoxedTypes configuration = null;
        try {
            configuration = parser.parse((
                            "--stringVariable1=" + stringVariable1Expected + " " +
                                    "--stringVariable2 -" + stringVariable2Expected + " " +
                                    "--booleanValue1=true --booleanValue2 -FALSE " +
                                    "--integerValue1=" + integerValue1Expected + " " +
                                    "--integerValue2 -" + integerValue2Expected + " " +
                                    "--floatValue1=2.4 --floatValue2 -4.2"
                    ).split(" ")
            );
        } catch (NoSuchBuilderException | WrongFormatException | UnknownCommandException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        }

        // Assert
        assertNotNull(configuration);
        assertEquals(stringVariable1Expected, configuration.getStringVariable1());
        assertEquals(stringVariable2Expected, configuration.getStringVariable2());
        assertNull(configuration.getStringVariable3());
        assertTrue(configuration.isBooleanValue1());
        assertFalse(configuration.isBooleanValue2());
        assertEquals(integerValue1Expected, configuration.getIntegerValue1().intValue());
        assertEquals(integerValue2Expected, configuration.getIntegerValue2().intValue());
        assertEquals(floatValue1Expected, configuration.getFloatValue1());
        assertEquals(floatValue2Expected, configuration.getFloatValue2());
    }

    @Test
    public void shouldReadValuesIntoConfigurationObjectUsingBothNamesAndVariableNames() {
        // Arrange
        final GeneralConfigurationBuilder<SimpleConfigurationClassBoxedTypes> builder =
                new GeneralConfigurationBuilder<>(SimpleConfigurationClassBoxedTypes.class);

        final SingletonCommandLineParser<SimpleConfigurationClassBoxedTypes> parser = new SingletonCommandLineParser<>(builder);

        String stringVariable1Expected = "heheTest";
        String stringVariable2Expected = "alsoTest";
        int integerValue1Expected = 12;
        int integerValue2Expected = 2342;
        Float floatValue1Expected = 2.4f;
        Float floatValue2Expected = 4.2f;

        // Act
        SimpleConfigurationClassBoxedTypes configuration = null;
        try {
            configuration = parser.parse((
                            "--sv1=" + stringVariable1Expected + " " +
                                    "--stringVariable2 -" + stringVariable2Expected + " " +
                                    "--bv1=true --booleanValue2 -FALSE " +
                                    "--iv1=" + integerValue1Expected + " " +
                                    "--integerValue2 -" + integerValue2Expected + " " +
                                    "--fv1=2.4 --floatValue2 -4.2"
                    ).split(" ")
            );
        } catch (NoSuchBuilderException | WrongFormatException | UnknownCommandException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        }

        // Assert
        assertNotNull(configuration);
        assertEquals(stringVariable1Expected, configuration.getStringVariable1());
        assertEquals(stringVariable2Expected, configuration.getStringVariable2());
        assertNull(configuration.getStringVariable3());
        assertTrue(configuration.isBooleanValue1());
        assertFalse(configuration.isBooleanValue2());
        assertEquals(integerValue1Expected, configuration.getIntegerValue1().intValue());
        assertEquals(integerValue2Expected, configuration.getIntegerValue2().intValue());
        assertEquals(floatValue1Expected, configuration.getFloatValue1());
        assertEquals(floatValue2Expected, configuration.getFloatValue2());
    }

    @Test
    public void shouldReadValuesIntoConfigurationObjectWithPrimitiveTypes() {
        // Arrange
        final GeneralConfigurationBuilder<SimpleConfigurationClassPrimitiveTypes> builder =
                new GeneralConfigurationBuilder<>(SimpleConfigurationClassPrimitiveTypes.class);

        final SingletonCommandLineParser<SimpleConfigurationClassPrimitiveTypes> parser = new SingletonCommandLineParser<>(builder);

        int integerValue1Expected = 12;
        int integerValue2Expected = 2342;
        float floatValue1Expected = 2.4f;
        float floatValue2Expected = 4.2f;

        // Act
        SimpleConfigurationClassPrimitiveTypes configuration = null;
        try {
            configuration = parser.parse((
                            "--booleanValue1=true --booleanValue2 -FALSE " +
                                    "--integerValue1=" + integerValue1Expected + " " +
                                    "--integerValue2 -" + integerValue2Expected + " " +
                                    "--floatValue1=2.4 --floatValue2 -4.2"
                    ).split(" ")
            );
        } catch (NoSuchBuilderException | WrongFormatException | UnknownCommandException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        }

        // Assert
        assertNotNull(configuration);
        assertTrue(configuration.isBooleanValue1());
        assertFalse(configuration.isBooleanValue2());
        assertEquals(integerValue1Expected, configuration.getIntegerValue1());
        assertEquals(integerValue2Expected, configuration.getIntegerValue2());
        assertEquals(floatValue1Expected, configuration.getFloatValue1(), .001f);
        assertEquals(floatValue2Expected, configuration.getFloatValue2(), .001f);
    }

    @Test
    public void shouldReadValuesIntoConfigurationObjectWithMap() {
        // Arrange
        final GeneralConfigurationBuilder<ConfigurationWithMap> builder =
                new GeneralConfigurationBuilder<>(ConfigurationWithMap.class);

        final SingletonCommandLineParser<ConfigurationWithMap> parser = new SingletonCommandLineParser<>(builder);

        String stringVariable1Expected = "heheTest";

        // Act
        ConfigurationWithMap configuration = null;
        try {
            configuration = parser.parse((
                            "--val=" + stringVariable1Expected + " " +
                                    "--map.key1=1 " +
                                    "--map.key2 -12 "
                    ).split(" ")
            );
        } catch (NoSuchBuilderException | WrongFormatException | UnknownCommandException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        }

        // Assert
        assertNotNull(configuration);
        assertEquals(stringVariable1Expected, configuration.getOtherValue());
        final Map<String, Integer> map = configuration.getMap();
        assertNotNull(map);
        assertEquals(1, map.get("key1").intValue());
        assertEquals(12, map.get("key2").intValue());
    }

    @Test
    public void shouldThrowExceptionAsMultipleValuesSpecifiedToFieldInDifferentWays() {
        // Arrange
        final GeneralConfigurationBuilder<SimpleConfigurationClassBoxedTypes> builder =
                new GeneralConfigurationBuilder<>(SimpleConfigurationClassBoxedTypes.class);

        final SingletonCommandLineParser<SimpleConfigurationClassBoxedTypes> parser = new SingletonCommandLineParser<>(builder);

        String stringVariable1Expected = "heheTest";
        String stringVariable2Expected = "alsoTest";

        // Act
        try {
            parser.parse((
                            "--stringVariable1=" + stringVariable1Expected + " -" + stringVariable1Expected +
                                    " --stringVariable2 -" + stringVariable2Expected
                    ).split(" ")
            );
            fail("Should have thrown exception");
        } catch (NoSuchBuilderException | WrongFormatException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        } catch (UnknownCommandException expected) {

        }
    }

    @Test
    public void shouldThrowExceptionAsVariableHasNoValue() {
        // Arrange
        final GeneralConfigurationBuilder<SimpleConfigurationClassBoxedTypes> builder =
                new GeneralConfigurationBuilder<>(SimpleConfigurationClassBoxedTypes.class);

        final SingletonCommandLineParser<SimpleConfigurationClassBoxedTypes> parser = new SingletonCommandLineParser<>(builder);

        String stringVariable2Expected = "alsoTest";

        // Act
        try {
            parser.parse((
                            "--stringVariable1 --stringVariable2 -" + stringVariable2Expected
                    ).split(" ")
            );
            fail("Should have thrown exception");
        } catch (NoSuchBuilderException | WrongFormatException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        } catch (UnknownCommandException expected) {

        }
    }


    @Test
    public void shouldThrowExceptionAsMultipleValuesSpecifiedToFieldCommands() {
        // Arrange
        final GeneralConfigurationBuilder<SimpleConfigurationClassBoxedTypes> builder =
                new GeneralConfigurationBuilder<>(SimpleConfigurationClassBoxedTypes.class);

        final SingletonCommandLineParser<SimpleConfigurationClassBoxedTypes> parser = new SingletonCommandLineParser<>(builder);

        String stringVariable1Expected = "heheTest";

        // Act
        try {
            parser.parse((
                            "--stringVariable1 -" + stringVariable1Expected + " -" + stringVariable1Expected
                    ).split(" ")
            );
            fail("Should have thrown exception");
        } catch (NoSuchBuilderException | WrongFormatException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        } catch (UnknownCommandException expected) {

        }
    }

    @Test
    public void shouldThrowExceptionAsMultipleValuesSpecifiedToFieldEqualSign() {
        // Arrange
        final GeneralConfigurationBuilder<SimpleConfigurationClassBoxedTypes> builder =
                new GeneralConfigurationBuilder<>(SimpleConfigurationClassBoxedTypes.class);

        final SingletonCommandLineParser<SimpleConfigurationClassBoxedTypes> parser = new SingletonCommandLineParser<>(builder);

        String stringVariable1Expected = "heheTest";

        // Act
        try {
            parser.parse((
                            "--stringVariable1=" + stringVariable1Expected + "=" + stringVariable1Expected
                    ).split(" ")
            );
            fail("Should have thrown exception");
        } catch (NoSuchBuilderException | WrongFormatException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        } catch (UnknownCommandException expected) {

        }
    }

    @Test
    public void shouldThrowExceptionAsMultipleEqualSignsOccurs() {
        // Arrange
        final GeneralConfigurationBuilder<SimpleConfigurationClassBoxedTypes> builder =
                new GeneralConfigurationBuilder<>(SimpleConfigurationClassBoxedTypes.class);

        final SingletonCommandLineParser<SimpleConfigurationClassBoxedTypes> parser = new SingletonCommandLineParser<>(builder);

        String stringVariable1Expected = "heheTest";

        // Act
        try {
            parser.parse(
                    ("--stringVariable1=" + stringVariable1Expected + "=" + stringVariable1Expected).split(" ")
            );
            fail("Should have thrown exception");
        } catch (NoSuchBuilderException | WrongFormatException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        } catch (UnknownCommandException expected) {

        }
    }

    @Test
    public void shouldWriteValuesToLayeredConfiguration() {
        // Arrange
        final GeneralConfigurationBuilder<ComplexConfiguration> builder =
                new GeneralConfigurationBuilder<>(ComplexConfiguration.class);

        final SingletonCommandLineParser<ComplexConfiguration> parser = new SingletonCommandLineParser<>(builder);

        ComplexConfiguration configuration = null;
        // Act
        try {
            configuration = parser.parse(("--outerValue=1231 --inner.booleanValue1=false --inner.integerValue1 -13").split(" ")
            );
        } catch (NoSuchBuilderException | WrongFormatException | UnknownCommandException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        }

        // Assert
        assertEquals(configuration.getOuterValue(), "1231");
        assertFalse(configuration.getInner().isBooleanValue1());
        assertEquals(configuration.getInner().getIntegerValue1(), 13);
    }

    @Test
    public void shouldWriteValuesToDeeplyLayeredConfiguration() {
        // Arrange
        final GeneralConfigurationBuilder<TwoLayerComplexConfiguration> builder =
                new GeneralConfigurationBuilder<>(TwoLayerComplexConfiguration.class);

        final SingletonCommandLineParser<TwoLayerComplexConfiguration> parser = new SingletonCommandLineParser<>(builder);

        TwoLayerComplexConfiguration config = null;

        // Act
        try {
            config = parser.parse(("--complexConfiguration.inner.booleanValue1=true " +
                    "--complexConfiguration.inner.integerValue1 -12 --stringValue -Something").split(" ")
            );
        } catch (NoSuchBuilderException | WrongFormatException | UnknownCommandException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        }

        // Assert
        assertEquals(config.getStringValue(), "Something");
        assertTrue(config.getComplexConfiguration().getInner().isBooleanValue1());
        assertEquals(config.getComplexConfiguration().getInner().getIntegerValue1(), 12);
    }

    @Test
    public void shouldThrowExceptionAsSpecifiedParameterIsUnknown() {
        // Arrange
        final GeneralConfigurationBuilder<PrimitiveLongOnlyConfiguration> builder =
                new GeneralConfigurationBuilder<>(PrimitiveLongOnlyConfiguration.class);

        final SingletonCommandLineParser<PrimitiveLongOnlyConfiguration> parser = new SingletonCommandLineParser<>(builder);

        // Act
        try {
            parser.parse(("--nonexistent=true").split(" ")
            );
            fail("Should have thrown exception");
        } catch (FieldMappingException expected) {

        } catch (NoSuchBuilderException | WrongFormatException | UnknownCommandException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        }
    }

    @Test
    public void shouldThrowExceptionAsSpecifiedParameterIsOfUnsupportedType() {
        // Arrange
        final GeneralConfigurationBuilder<PrimitiveLongOnlyConfiguration> builder =
                new GeneralConfigurationBuilder<>(PrimitiveLongOnlyConfiguration.class);

        final SingletonCommandLineParser<PrimitiveLongOnlyConfiguration> parser = new SingletonCommandLineParser<>(builder);

        // Act
        try {
            parser.parse(("--longValue=1231").split(" ")
            );
            fail("Should have thrown exception");
        } catch (FieldMappingException expected) {

        } catch (NoSuchBuilderException | WrongFormatException | UnknownCommandException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        }
    }

    @Test
    public void shouldReturnHelpStringGeneratedFromSpecifiedClass() {

        final GeneralConfigurationBuilder<SimpleConfigurationClassPrimitiveTypes> builder = new GeneralConfigurationBuilder<>(
                SimpleConfigurationClassPrimitiveTypes.class
        );

        assertEquals(HelpUtilities.generateHelpString(SimpleConfigurationClassPrimitiveTypes.class), builder.help());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionAsUnableToInstantiate() {
        new GeneralConfigurationBuilder<>(
                ConfigurationWithoutDefaultConstructor.class
        );
    }

    @Test
    public void shouldOmitStaticField() {
        // Arrange
        final GeneralConfigurationBuilder<ConfigurationWithStaticField> builder =
                new GeneralConfigurationBuilder<>(ConfigurationWithStaticField.class);

        final SingletonCommandLineParser<ConfigurationWithStaticField> parser = new SingletonCommandLineParser<>(builder);

        // Act

        try {
            parser.parse(("--staticString=value").split(" "));
            fail("Should have thrown exception");
        } catch (FieldMappingException expected) {

        } catch (NoSuchBuilderException | WrongFormatException | UnknownCommandException e) {
            e.printStackTrace();
            fail("Did not expect parser to throw exception!");
        }
    }
}
