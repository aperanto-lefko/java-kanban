import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HistoryMap<Integer, Task> {

    public Map<Integer, Task> historyMap;

    public HistoryMap() {
        historyMap = new HashMap<>();
    }

    public Node<Task> head; //голова
    public Node<Task> tail = null; //хвост
    public Node<Task> task;

    public Node<Task> oldTail; //старый хвост

    public void put(Integer key, Task value) {


        if (!historyMap.containsKey(key)) {

            oldTail = tail; //старый хвост
            Node<Task> node = new Node<>(oldTail, value, null);
            historyMap.put(key, (Task) node);
            tail = node;
            if (oldTail != null) {
                oldTail.next = node;
            } else {
                head = node;
            }

        } else {
            Node<Task> existingNode = (Node<Task>) historyMap.get(key);
            if (existingNode != head && existingNode != tail) {
                Node<Task> prevExistingNode = existingNode.prev;
                Node<Task> nextExistingNode = existingNode.next;
                historyMap.remove(key);
                prevExistingNode.next = nextExistingNode;
                nextExistingNode.prev = prevExistingNode;
                for (Task node : historyMap.values()) {
                    Node<Task> oldTailNode = (Node<Task>) node;
                    if (oldTailNode.next == null) {
                        Node<Task> tailNode = new Node<>(oldTailNode, value, null);
                        oldTailNode.next = tailNode;
                        historyMap.put(key, (Task) tailNode);
                        tail = tailNode;
                        return;
                    }
                }

            } else if (existingNode == head) {
                Node<Task> nextExistingNode = existingNode.next;
                historyMap.remove(key);
                head = nextExistingNode;
                nextExistingNode.prev = null;
                for (Task node : historyMap.values()) {
                    Node<Task> oldTailNode = (Node<Task>) node;
                    if (oldTailNode.next == null) {
                        Node<Task> tailNode = new Node<>(oldTailNode, value, null);
                        oldTailNode.next = tailNode;
                        historyMap.put(key, (Task) tailNode);
                        tail = tailNode;
                        return;
                    }
                }
            } else if (existingNode == tail) {
                Node<Task> prevExistingNode = existingNode.prev;
                historyMap.remove(key);
                Node<Task> tailNode = new Node<>(prevExistingNode, value, null);
                prevExistingNode.next = tailNode;
                historyMap.put(key, (Task) tailNode);
                tail = tailNode;

            }
        }
    }

    public List<Task> printHistoryMap() {
        List<Task> historyList = new LinkedList<>();
        historyList.add(head.task);
        while (historyList.size() != historyMap.size()) {
            for (Task node : historyMap.values()) {
                Task lastNode = historyList.getLast();
                Node<Task> newNode = (Node<Task>) node;
                Node<Task> prevNewNode = newNode.prev;
                if (newNode.prev != null && prevNewNode.task.equals(lastNode)) {
                    historyList.add(newNode.task);
                }
            }
        }
        return historyList;
    }

    public void removeFromHistory(Integer key) {
        if (historyMap.containsKey(key)) {
            Node<Task> deletedNode = (Node<Task>) historyMap.get(key);
            if (deletedNode != head && deletedNode != tail) {
                Node<Task> prevDeletedNode = deletedNode.prev;
                Node<Task> nextDeletedNode = deletedNode.next;
                historyMap.remove(key);
                prevDeletedNode.next = nextDeletedNode;
                nextDeletedNode.prev = prevDeletedNode;
            } else if (deletedNode == head) {
                Node<Task> nextDeletedNode = deletedNode.next;
                historyMap.remove(key);
                head = nextDeletedNode;
                nextDeletedNode.prev = null;
            } else if (deletedNode == tail) {
                Node<Task> prevDeletedNode = deletedNode.prev;
                historyMap.remove(key);
                tail = prevDeletedNode;
                prevDeletedNode.next = null;
            }
        }
    }
}



