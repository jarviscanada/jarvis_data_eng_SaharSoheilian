package ca.jrvs.practice.dataStructure.list;


public class LinkedJList<E> implements JList<E> {

  // List size
  transient int size = 0;

  // Pointer to first node.
  transient Node<E> first;

  // Pointer to last node.
  transient Node<E> last;

  public LinkedJList() {
  }

  private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(E item, Node<E> nextNode, Node<E> prevNode) {
      this.item = item;
      this.next = nextNode;
      this.prev = prevNode;
    }
  }

  @Override
  public boolean add(E e) {
    if (e == null)
      throw new NullPointerException("Invalid object to add");

    Node<E> newNode = new Node<>(e, null, last);

    if (last == null)
      first = newNode;
    else
      last.next = newNode;

    last = newNode;
    size++;

    return true;
  }

  @Override
  public Object[] toArray() {
    Object[] obj = new Object[size];
    int index = 0;

    for (Node<E> node = first; node != null; node = node.next) {
      obj[index] = node.item;
      index++;
    }

    return obj;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return first == null;
  }

  @Override
  public int indexOf(Object o) {
    int index = 0;
    for (Node<E> node = first; node != null; node = node.next) {
      if (o.equals(node.item))
        return index;
      index++;
    }
    return -1;
  }

  @Override
  public boolean contains(Object o) {
    if (o == null)
      throw new NullPointerException("Invalid object");

    return indexOf(o) >= 0;
  }

  @Override
  public E get(int index) {
    if (index > size || index < 0)
      throw new IndexOutOfBoundsException("Invalid index");

    return findNode(index).item;
  }

  private Node<E> findNode(int index) {
    Node<E> node;

    // index is in the first half of the list
    if (index < (size >> 1)) {
      node = first;
      for (int i = 0; i < index; i++)
        node = node.next;

    } else { // index is in the second half of the list
      node = last;
      for (int i = 1; i < size - index; i++)
        node = node.prev;
    }

    return node;
  }

  @Override
  public E remove(int index) {
    if (index > size || index < 0)
      throw new IndexOutOfBoundsException("Invalid index");

    Node<E> node = findNode(index);
    E item = node.item;

    // create next node connections
    if (node.next == null){
      last = node.prev;
    } else {
      node.next.prev = node.prev;
    }

    // create previous node connections
    if (node.prev == null){
      first = node.next;
    } else {
      node.prev.next = node.next;
    }

    node.next = null;
    node.prev = null;
    node.item = null;
    size--;

    return item;
  }

  @Override
  public void clear() {
    Node<E> node, nextNode;
    node = first;

    while (node != null){
      nextNode = node.next;
      node.item = null;
      node.prev = null;
      node.next = null;
      node = nextNode;
    }

    first = last = null;
    size = 0;
  }
}
