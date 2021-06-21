package ca.jrvs.practice.dataStructure.tree;

import java.util.Comparator;
import java.util.Objects;

/**
 * A simplified BST implementation
 *
 * @param <E> type of object to be stored
 */
public class JBSTree<E> implements JTree<E> {

  /**
   * The comparator used to maintain order in this tree map
   * Comparator cannot be null
   */
  private Comparator<E> comparator;

  /**
   * root of tree
   */
  private Node<E> root;

  /**
   * Create a new BST
   *
   * @param comparator the comparator that will be used to order this map.
   * @throws IllegalArgumentException if comparator is null
   */
  public JBSTree(Comparator<E> comparator) {
    this.comparator = comparator;
  }

  /**
   * Insert an object into the BST.
   * Please review the BST property.
   *
   * @param object item to be inserted
   * @return inserted item
   * @throws IllegalArgumentException if the object already exists
   */
  @Override
  public E insert(E object) {
    if (search(object) != null)
      throw new IllegalArgumentException("Object already exists");

    Node<E> newNode = new Node<>(object, null);

    if (root == null)
      root = newNode;
    else
      insertNode(root, newNode);

    return object;
  }

  private void insertNode(Node<E> current, Node<E> newNode) {
    // insert at left subtree
    if (comparator.compare(newNode.value, current.value) < 0) {
      if (current.left == null) {
        current.left = newNode;
        newNode.parent = current;
      } else
        insertNode(current.left, newNode);

    } else { //insert at right subtree
      if (current.right == null) {
        current.right = newNode;
        newNode.parent = current;
      } else
        insertNode(current.right, newNode);
    }
  }

  /**
   * Search and return an object, return null if not found
   *
   * @param object to be found
   * @return the object if exists or null if not
   */
  @Override
  public E search(E object) {
    Node<E> current = root;

    while(current != null) {
      if (comparator.compare(object, current.value) > 0)
        current = current.right;
      else if(comparator.compare(object, current.value) < 0)
        current = current.left;
      else
        return object;
    }
    return null;
  }

  /**
   * Remove an object from the tree.
   *
   * @param object to be removed
   * @return removed object
   * @throws IllegalArgumentException if the object not exists
   */
  @Override
  public E remove(E object) {
    if (search(object) == null)
      throw new IllegalArgumentException("Object doesn't exist");

    // find the node
    Node<E> node = root;
    boolean isLeftChild = false;

    while (comparator.compare(node.value, object) != 0) {
      if (comparator.compare(node.value, object) < 0) {
        node = node.left;
        isLeftChild = true;
      }
      else {
        node = node.right;
        isLeftChild = false;
      }
    }

    // find out if the node has no, one, or two children
    if (node.left == null && node.right == null) {
      if (node == root)
        root = null;
      else {
        if (isLeftChild)
          node.parent.left = null;
        else
          node.parent.right = null;
        node.parent = null;
      }

    } else if (node.right == null) {
      if (node == root)
        root = node.left;
      else {
        if (isLeftChild)
          node.parent.left = node.left;
        else
          node.parent.right = node.left;

        node.parent = null;
        node.left = null;
      }

    } else if (node.left == null) {
      if (node == root)
        root = node.right;
      else {
        if (isLeftChild)
          node.parent.left = node.right;
        else
          node.parent.right = node.right;

        node.parent = null;
        node.right = null;
      }

    } else {

    }

    return object;
  }

  private Node<E> getNode(Node<E> current, E object) {
    // we know the object exists in the tree
    int cmp = comparator.compare(current.value, object);

    if (cmp == 0)
      return current;
    else if (cmp < 0)
      return getNode(current.left, object);
    else
      return getNode(current.right, object);
  }


  /**
   * traverse the tree recursively
   *
   * @return all objects in pre-order
   */
  @Override
  public E[] preOrder() {
    return null;
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects in-order
   */
  @Override
  public E[] inOrder() {
    return null;
  }

  /**
   * traverse the tree recursively
   *
   * @return all objects pre-order
   */
  @Override
  public E[] postOrder() {
    return null;
  }

  /**
   * traverse through the tree and find out the tree height
   * @return height
   * @throws NullPointerException if the BST is empty
   */
  @Override
  public int findHeight() {
    return 0;
  }

  static final class Node<E> {

    E value;
    Node<E> left;
    Node<E> right;
    Node<E> parent;

    public Node(E value, Node<E> parent) {
      this.value = value;
      this.parent = parent;
    }

    public E getValue() {
      return value;
    }

    public void setValue(E value) {
      this.value = value;
    }

    public Node<E> getLeft() {
      return left;
    }

    public void setLeft(Node<E> left) {
      this.left = left;
    }

    public Node<E> getRight() {
      return right;
    }

    public void setRight(Node<E> right) {
      this.right = right;
    }

    public Node<E> getParent() {
      return parent;
    }

    public void setParent(Node<E> parent) {
      this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Node)) {
        return false;
      }
      Node<?> node = (Node<?>) o;
      return getValue().equals(node.getValue()) &&
          Objects.equals(getLeft(), node.getLeft()) &&
          Objects.equals(getRight(), node.getRight()) &&
          getParent().equals(node.getParent());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getValue(), getLeft(), getRight(), getParent());
    }
  }
}
