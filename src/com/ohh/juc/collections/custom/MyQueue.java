package com.ohh.juc.collections.custom;

/**
 * 自定义队列，实现方式是链表
 * 非线程安全
 * @param <E>
 */
public class MyQueue<E> {

    /**
     * 实现一个自定义的节点
     *
     * @param <E> 数据类型
     */
    private static class Node<E> {

        private E element;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            return element == null ? "" : element.toString();
        }
    }

    private Node<E> first, last;
    private int size;

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public E peekFirst() {
        return this.first == null ? null : this.first.element;
    }

    public E peekLast() {
        return this.last == null ? null : this.last.element;
    }

    public void addLast(E element) {
        Node<E> newNode = new Node<>(element, null);
        if (this.last != null) {
            this.last.next = newNode;
        } else {
            this.first = newNode;
        }
        this.last = newNode;
        this.size++;
    }

    public E removeFirst() {
        if (this.first == null) {
            throw new RuntimeException("No such element.");
        }
        Node<E> e = this.first;
        if (this.first.next != null) {
            this.first = this.first.next;
            e.next = null;
        } else {
            this.first = this.last = null;
        }
        this.size--;
        return e.element;
    }

    public static void main(String[] args) {
        MyQueue<String> queue = new MyQueue<>();
        queue.addLast("Hello");
        queue.addLast("World");
        queue.addLast("Java");
        System.out.println("queue.removeFirst() = " + queue.removeFirst());
        System.out.println("queue.removeFirst() = " + queue.removeFirst());
        System.out.println("queue.removeFirst() = " + queue.removeFirst());
        System.out.println("queue.removeFirst() = " + queue.removeFirst());
    }
}
