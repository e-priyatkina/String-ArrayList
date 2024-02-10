import exception.ElementNotFoundException;
import exception.InvalidIndexException;
import exception.NullItemException;
import exception.StorageIsFullException;

import java.util.Arrays;

public class StringStringListImpl implements StringList {

    private final String[] elements;

    private int size;

    public StringStringListImpl() {
        elements = new String[10];
    }

    public StringStringListImpl(int initSize) {
        elements = new String[initSize];
    }

    private void validateItem(String item) {
        if (item == null) {
            throw new NullItemException();
        }
    }

    private void validateSize() {
        if (size == elements.length) {
            throw new StorageIsFullException();
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new InvalidIndexException();
        }
    }

    @Override
    public String add(String item) {
        validateSize();
        validateItem(item);
        elements[size++] = item;
        return item;
    }

    @Override
    public String add(int index, String item) {
        validateSize();
        validateIndex(index);
        validateItem(item);
        if (index == size) {
            elements[size++] = item;
            return item;
        }

        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = item;
        size++;
        return item;
    }

    @Override
    public String set(int index, String item) {
        validateIndex(index);
        validateItem(item);
        elements[index] = item;
        return item;
    }

    @Override
    public String remove(String item) {
        validateItem(item);

        int index = indexOf(item);
        if (index == -1) {
            throw new ElementNotFoundException();
        }

        if (index == size) {
            elements[size--] = null;
            return item;
        }

        System.arraycopy(elements, index + 1, elements, index, size - (index + 1));
        size--;
        return item;
    }

    @Override
    public String remove(int index) {
        validateIndex(index);

        if (index == size) {
            String element = elements[size--];
            elements[size--] = null;
            return element;
        }

        String element = elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - (index + 1));
        size--;
        return element;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        for (int i = (size - 1); i >= 0; i--) {
            if (elements[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        validateIndex(index);
        return elements[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        return Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public String[] toArray() {
        return Arrays.copyOf(elements, size);
    }
}
