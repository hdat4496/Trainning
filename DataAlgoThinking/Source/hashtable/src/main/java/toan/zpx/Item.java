package toan.zpx;

public class Item {
    private String key;
    private String value;

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Item(String key, String value) {
        setKey(key);
        setValue(value);
    }

    public Item() {
        setKey(null);
        setValue(null);
    }
}
