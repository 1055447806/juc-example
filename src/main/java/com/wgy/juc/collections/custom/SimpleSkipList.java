package com.wgy.juc.collections.custom;

import java.util.Random;

/**
 * 自定义一个简单的跳表
 */
public class SimpleSkipList {

    private static final byte HEAD_NODE = (byte) -1, DATE_NODE = 0, TAIL_NODE = -1;
    private Node head;
    private Node tail;
    private int size;
    private int height;
    private Random random;

    public SimpleSkipList() {
        head = new Node(null, HEAD_NODE);
        tail = new Node(null, TAIL_NODE);
        head.right = tail;
        tail.left = head;
        random = new Random(System.currentTimeMillis());
    }

    /**
     * 查找一个数，并返回一个最有可能的节点。
     */
    private Node find(Integer value) {
        Node current = head;
        //由最上层，循环向右找，当右侧节点的值大于 value 时，停止向右，进入下一层。
        while (true) {
            while (current.right.type != TAIL_NODE && current.right.value <= value) {
                current = current.right;
            }
            if (current.down == null)
                break; // 如果是最底层，就 break
            current = current.down;
        }
        // 最终返回的 current 节点，是一个的值 <= value 的节点，如果查找的数比所有节点都小，就返回 head 节点。
        return current;
    }

    public void add(Integer value) {
        Node nearNode = find(value);
        Node newNode = new Node(value);
        newNode.left = nearNode;
        newNode.right = nearNode.right;
        nearNode.right = newNode;
        nearNode.right.left = newNode;

        int currentLevel = 0;
        while (random.nextDouble() < 0.5d) {

            //处理插入节点的高度大于行高的情况
            if (currentLevel >= height) {
                height++;
                Node dumyHead = new Node(null, HEAD_NODE);
                Node dumyTail = new Node(null, TAIL_NODE);

                dumyHead.right = dumyTail;
                dumyHead.down = head;
                head.up = dumyHead;
                dumyTail.left = dumyHead;
                dumyTail.down = tail;
                tail.up = dumyTail;
            }

            while (nearNode != null && nearNode.up == null) {
                nearNode = nearNode.left;
            }

            nearNode = nearNode.up;
            Node upNode = new Node(value);

            //上层插入的节点
            upNode.left = nearNode;
            upNode.right = nearNode.right;
            upNode.down = newNode;

            //新节点
            newNode.up = upNode;

            //上层插入节点的左右节点
            nearNode.right.left = upNode;
            nearNode.right = upNode;

            //插入一层之后，插入的节点作为新节点，准备进行下一次操作
            newNode = upNode;
            currentLevel++;
        }
        size++;
    }

    public void dumpSkipList() {
        System.out.printf("Total height[%d]", height);
        Node temp = head;
        int i = height;
        while (i >= 0) {
            System.out.printf("height[%d] |", i);
            Node current = head.right;
            while (current.type != TAIL_NODE) {
                System.out.printf("->%d ", current.value);
            }
            System.out.printf("%n");
            temp = temp.down;
            i--;
        }
        System.out.println("================");
    }

    public boolean contains(Integer value) {
        return find(value).value.equals(value);
    }

    public Integer get(Integer value) {
        Integer res = find(value).value;
        return res.equals(value) ? res : null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node {
        private Integer value;
        private Node up, down, left, right;
        private byte type;

        public Node(Integer value) {
            this(value, DATE_NODE);
        }

        public Node(Integer value, byte type) {
            this.value = value;
            this.type = type;
        }
    }
}
