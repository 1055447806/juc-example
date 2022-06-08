package com.wgy.juc.collections.custom;

/**
 * 自定义一个 LinkedList，元素排列为有序。
 */
public class PriorityLinkedList<E extends Comparable<E>> {


    private Node<E> first = null;
    private final Node<E> NULL = null;
    private static final String PLAIN_NULL = "null";
    private int size;

    public PriorityLinkedList() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(E e) {
        final Node<E> newNode = new Node<>(e);
        Node<E> prev = null;
        Node<E> current = first;
        while (current != null && e.compareTo(current.value) > 0) {
            prev = current;
            current = current.next;
        }
        if (prev != null) {
            prev.next = newNode;
        } else {
            first = newNode;
        }
        newNode.next = current;
        size++;
    }

    public static <E extends Comparable<E>> PriorityLinkedList<E> of(E... elements) {
        PriorityLinkedList<E> list = new PriorityLinkedList<>();
        for (E e : elements) {
            list.addFirst(e);
        }
        return list;
    }

    public boolean contains(E e) {
        Node<E> current = first;
        while (current != null) {
            if (current.value.equals(e)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new NoElementException("this LinkedList is already empty.");
        }
        Node<E> current = first;
        first = first.next;
        size--;
        return current.value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Node<E> current = first;
        while (current != null) {
            builder.append(current).append(", ");
            current = current.next;
        }
        builder.replace(builder.length() - 2, builder.length(), "]");
        return builder.toString();
    }

    private static class NoElementException extends RuntimeException {
        public NoElementException(String message) {
            super(message);
        }
    }


    private static class Node<E> {
        E value;
        Node<E> next;

        public Node(E value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value == null ?
                    PLAIN_NULL :
                    value.toString();
        }
    }

    public static void main(String[] args) {
        PriorityLinkedList<Integer> list = PriorityLinkedList.of(50, 80, 30, 60, 40, 100, 0, 1);
        System.out.println(list);
    }
}
