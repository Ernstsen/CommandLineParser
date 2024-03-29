package dk.eSoftware.commandLineParser.generalized.configuratinos;

import dk.eSoftware.commandLineParser.Configuration;

@SuppressWarnings("unused")
public class ConfigurationWithoutDefaultConstructor implements Configuration {
    private String value;

    public ConfigurationWithoutDefaultConstructor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
