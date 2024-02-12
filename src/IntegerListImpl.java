import exception.ElementNotFoundException;
import exception.InvalidIndexException;
import exception.NullItemException;
import exception.StorageIsFullException;

import java.util.Arrays;

public class IntegerListImpl implements IntegerList {
    private Integer[] elements;

    private int size;

    public IntegerListImpl() {
        elements = new Integer[10];
    }

    public IntegerListImpl(int initSize) {
        elements = new Integer[initSize];
    }

    private void validateItem(Integer item) {
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
    public Integer add(Integer item) {
        grow();
        validateItem(item);
        elements[size++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        grow();
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
    public Integer set(int index, Integer item) {
        validateIndex(index);
        validateItem(item);
        elements[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
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
    public Integer remove(int index) {
        validateIndex(index);

        if (index == size) {
            Integer element = elements[size--];
            elements[size--] = null;
            return element;
        }

        Integer element = elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - (index + 1));
        size--;
        return element;
    }

    @Override
    public boolean contains(Integer item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = (size - 1); i >= 0; i--) {
            if (elements[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        validateIndex(index);
        return elements[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
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
    public Integer[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    public void grow() {
        elements = Arrays.copyOf(elements, size + size / 2);
    }

    private static void sortInsertion(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

    public static boolean contains(int[] arr, int element) {
        int min = 0;
        int max = arr.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (element == arr[mid]) {
                return true;
            }

            if (element < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }
}
