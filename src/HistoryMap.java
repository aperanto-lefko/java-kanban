import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HistoryMap<K, V> {

    public HashMap<K, V> historyMap;

    public HistoryMap() {
        historyMap = new HashMap<>();
    }

    public Node<V> head; //голова
    public Node<V> tail = null; //хвост
    public Node<V> task;

    public Node<V> oldTail; //старый хвост

    public void put(K key, V value) {


        if (!historyMap.containsKey(key)) {

            oldTail = tail; //старый хвост
            Node<V> node = new Node<>(oldTail, value, null);
            historyMap.put(key, (V) node);
            tail = node;
            if (oldTail != null) {
                oldTail.next = node;
            } else {
                head = node;
            }

        } else {
            Node<V> existingNode = (Node<V>) historyMap.get(key);
            if (existingNode != head && existingNode != tail) {
                Node<V> prevExistingNode = existingNode.prev;
                Node<V> nextExistingNode = existingNode.next;
                historyMap.remove(key);
                prevExistingNode.next = nextExistingNode;
                nextExistingNode.prev = prevExistingNode;
                for (V node : historyMap.values()) {
                    Node<V> oldTailNode = (Node<V>) node;
                    if (oldTailNode.next == null) {
                        Node<V> tailNode = new Node<>(oldTailNode, value, null);
                        oldTailNode.next = tailNode;
                        historyMap.put(key, (V) tailNode);
                        tail = tailNode;
                        return;
                    } else continue;
                }

            } else if (existingNode == head) {
                Node<V> nextExistingNode = existingNode.next;
                historyMap.remove(key);
                head = nextExistingNode;
                nextExistingNode.prev = null;
                for (V node : historyMap.values()) {
                    Node<V> oldTailNode = (Node<V>) node;
                    if (oldTailNode.next == null) {
                        Node<V> tailNode = new Node<>(oldTailNode, value, null);
                        oldTailNode.next = tailNode;
                        historyMap.put(key, (V) tailNode);
                        tail = tailNode;
                        return;
                    }
                }
            } else if (existingNode == tail) {
                Node<V> prevExistingNode = existingNode.prev;
                historyMap.remove(key);
                Node<V> tailNode = new Node<>(prevExistingNode, value, null);
                prevExistingNode.next = tailNode;
                historyMap.put(key, (V) tailNode);
                tail = tailNode;

            }
        }
    }

    public List<V> printHistoryMap() {
        List<V> historyList = new LinkedList<>();
        historyList.add(head.task);
        while (historyList.size() != historyMap.size()) {
            for (V node : historyMap.values()) {
                V lastNode = historyList.getLast();
                Node<V> newNode = (Node<V>) node;
                Node<V> prevNewNode = newNode.prev;
                if (newNode.prev == null) {
                    continue;
                } else if (prevNewNode.task.equals(lastNode)) {
                    historyList.add(newNode.task);
                }
            }

        }
        return historyList;
    }

    public void removeFromHistory(K key) {
        if (historyMap.containsKey(key)) {
            Node<V> deletedNode = (Node<V>) historyMap.get(key);
            if (deletedNode != head && deletedNode != tail) {
                Node<V> prevDeletedNode = deletedNode.prev;
                Node<V> nextDeletedNode = deletedNode.next;
                historyMap.remove(key);
                prevDeletedNode.next = nextDeletedNode;
                nextDeletedNode.prev = prevDeletedNode;
            } else if (deletedNode == head) {
                Node<V> nextDeletedNode = deletedNode.next;
                historyMap.remove(key);
                head = nextDeletedNode;
                nextDeletedNode.prev = null;
            } else if (deletedNode == tail) {
                Node<V> prevDeletedNode = deletedNode.prev;
                historyMap.remove(key);
                tail = prevDeletedNode;
                prevDeletedNode.next = null;
            }
        }
    }
}



