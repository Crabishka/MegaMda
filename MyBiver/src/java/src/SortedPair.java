package src;

public class SortedPair<K> {
    public K key;
    public K value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public K getValue() {
        return value;
    }

    public void setValue(K value) {
        this.value = value;
    }

    public SortedPair(K key, K value) {
        if (key.toString().compareTo(value.toString()) > 0){
            this.key = key;
            this.value = value;
        } else {
            this.key = value;
            this.value = key;
        }

    }

}
