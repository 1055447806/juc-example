package com.wgy.juc.collections.custom;

/**
 * 自定义一个 LinkedList
 */
public class LinkedList<E> {

    private Node<E> first = null;
    private final Node<E> NULL = null;
    private static final String PLAIN_NULL = "null";
    private int size;

    public LinkedList() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(E e) {
        final Node<E> newNode = new Node<>(e);
        newNode.next = first;
        first = newNode;
        size++;
    }

    public static <E> LinkedList<E> of(E... elements) {
        LinkedList<E> list = new LinkedList<>();
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
        LinkedList<String> list = LinkedList.of("hello", "world", "java");
        System.out.println(list);
        list.addFirst("scala");
        System.out.println(list);
        System.out.println("remove: " + list.removeFirst());
        System.out.println(list);
        System.out.println(list.contains("java"));
        System.out.println(list.size());
        System.out.println(list.isEmpty());
    }
}
