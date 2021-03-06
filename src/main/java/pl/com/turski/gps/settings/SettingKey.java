package pl.com.turski.gps.settings;

/**
 * User: Adam
 */
public enum SettingKey {
    SERVER_URL("http://192.168.1.2:8080/_ah/api"), GPS_LOCATOR_IDENTIFIER("1"), LOCALIZATION_FREQUENCY("120000");

    private String defValue;

    private SettingKey(String defValue) {
        this.defValue = defValue;
    }

    public String getKey() {
        return name();
    }

    public String getDefValue() {
        return defValue;
    }
}
