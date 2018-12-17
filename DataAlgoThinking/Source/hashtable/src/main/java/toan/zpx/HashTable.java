package toan.zpx;

public class HashTable {
    private int INITIAL_BASE_SIZE = 50;
    private int PRIME_1 = 151;
    private int PRIME_2 = 157;

    private int baseSize;
    private int size;
    private int count;
    private Item[] items;
    private Item item_null = new Item();
    public HashTable() {
        baseSize = INITIAL_BASE_SIZE;
        size = nextPrime(baseSize);
        count = 0;
        items = new Item[size];
    }


    public void insert(String key, String value) {
        final int load = count * 100 / size;
        if (load > 70) {
            resizeUp();
        }

        Item newItem = new Item(key, value);
        int index = getHash(key, size, 0);
        Item item = items[index];
        int i = 1;

        while ((item != null) && (item != item_null)) {
            if (item.getKey().equals(key)) {
                items[index] = newItem;
                return;
            }

            index = getHash(key, size, i);
            item = items[index];
            i ++;
        }

        items[index] = newItem;
        count ++;
    }


    public String search(String key) {
        int index = getHash(key, size, 0);
        Item item = items[index];
        int i = 1;
        int itemCount = 0;

        while ((item != null) && (itemCount < count)) {
            if (item != item_null) {
                if (item.getKey().equals(key)) {
                    return item.getValue();
                }

                itemCount++;
            }

            index = getHash(key, size, i);
            item = items[index];
            i++;
        }

        return null;
    }


    public void delete(String key) {
        int load = count * 100 / size;
        if (load < 10) {
            resizeDown();
        }

        int index = getHash(key, size, 0);
        Item item = items[index];
        int i = 1;
        int itemCount = 0;
        while ((item != null) && (itemCount < count)) {
            if (item != item_null) {
                if (item.getKey().equals(key)) {
                    items[index] = item_null;
                    count --;
                    return;
                }

                itemCount += 1;
            }

            index = getHash(key, size, i);
            item = items[index];
            i++;
        }
    }


    private void resize(int baseSize) {
        if (baseSize < INITIAL_BASE_SIZE) {
            return;
        }

        int newSize = nextPrime(baseSize);

        Item[] newArray = new Item[newSize];

        for (int i = 0; i < this.size; i++) {
            if (this.items[i] != null && this.items[i] != item_null) {
                newArray[i] = this.items[i];
            }
        }

        this.baseSize = baseSize;

        this.size = newSize;

        this.items = newArray;
    }


    private void resizeUp() { resize(this.baseSize * 2); }

    private void resizeDown() { resize(this.baseSize /2); }


    private int hash(String key, int prime, int numBuckets) {
        long hash = 0;
        int lenght = key.length();
        for (int i = 0; i < lenght; i += 1) {
            hash += (long) Math.pow(prime, lenght - (i + 1)) * (int) key.charAt(i);
            hash %= numBuckets;
        }

        return (int) hash;
    }

    private int getHash(String key, int numBuckets, int attempt) {
        int hashA = hash(key, PRIME_1, numBuckets);
        int hashB = hash(key, PRIME_2, numBuckets);

        return (hashA + (attempt * (hashB + 1))) % numBuckets;
    }


    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }

        if (num < 4) {
            return true;
        }

        if ((num % 2) == 0) {
            return false;
        }
        double sqrt = Math.sqrt(num);
        for (int i = 3; i <= sqrt; i += 2) {
            if ((num % i) == 0) {
                return false;
            }
        }

        return true;
    }

    private int nextPrime(int num) {
        while (!isPrime(num)) {
            num++;
        }

        return num;
    }

}
