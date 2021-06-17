package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;

public class ArrayJLists<E> implements JList<E> {

  // default initial capacity
  private static final int DEFAULT_CAPACITY = 10;

  /**
   * The array buffer into which the elements of the ArrayList are stored.
   * The capacity of the ArrayList is the length of this array buffer.
   */
  transient Object[] elementData; // non-private to simplify nested class access

  /**
   * The size of the ArrayList (the number of elements it contains).
   */
  private int size;

  public ArrayJLists(int initialCapacity) {
    if (initialCapacity > 0) {
      this.elementData = new Object[initialCapacity];
    } else {
      throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
    }
  }

  public ArrayJLists(){
    this(DEFAULT_CAPACITY); // calls previous constructor with initCapacity
  }

  /**
   * Appends the specified element to the end of this list (optional
   * operation).
   *
   * Double elementData size if elementData is full.
   */
  @Override
  public boolean add(E e) {
    if (e == null)
      throw new NullPointerException("Invalid object to add");

    int oldCapacity = elementData.length;
    if (size + 1 > oldCapacity) {
      elementData = Arrays.copyOf(elementData, oldCapacity << 1);
    }
    elementData[size++] = e;

    return true;
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(elementData, size);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0; //size == 0 ? true : false;
  }

  @Override
  public int indexOf(Object o) {
    if (o == null) {
      for (int i = 0; i < size; i++)
        if (elementData[i] == null)
          return i;
    } else {
      for (int i = 0; i < size; i++)
        if (o.equals(elementData[i]))
          return i;
    }
    return -1;
  }

  @Override
  public boolean contains(Object o) {
    if (o == null)
      throw new NullPointerException("null object is not accepted");

    return indexOf(o) >= 0; // indexOf(o) >= 0 ? true : false;
  }

  @Override
  public E get(int index) {
    if (index < 0 || index > size)
      throw new IndexOutOfBoundsException("Invalid index: " + index);

    return (E) elementData[index];
  }

  @Override
  public E remove(int index) {
    if (index < 0 || index > size)
      throw new IndexOutOfBoundsException("Invalid index: " + index);

    E element = (E) elementData[index];
    System.arraycopy(elementData, index + 1, elementData, index,
        size -index + 1);
    elementData[--size] = null;

    return element;
  }

  @Override
  public void clear() {
//    Stream.of(elementData).forEach(e-> e = null);
    for(int i = 0; i < size; i++) {
      elementData[i] = null;
    }
    size = 0;
  }
}
