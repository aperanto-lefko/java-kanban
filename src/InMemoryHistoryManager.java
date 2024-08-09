import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    //private HistoryMap historyMap = new HistoryMap();

    public Map<Integer, Node<Task>> historyMap = new HashMap<>();

    public Node<Task> head; //голова
    public Node<Task> tail = null; //хвост
    public Node<Task> task;

    public Node<Task> oldTail; //старый хвост

    @Override
    public void put(Integer key, Task value) {


        if (!historyMap.containsKey(key)) {

            oldTail = tail; //старый хвост
            Node<Task> node = new Node<>(oldTail, value, null);
            historyMap.put(key, node);
            tail = node;
            if (oldTail != null) {
                oldTail.next = node;
            } else {
                head = node;
            }

        } else {
            Node<Task> existingNode = historyMap.get(key);
            if (existingNode != head && existingNode != tail) {
                Node<Task> prevExistingNode = existingNode.prev;
                Node<Task> nextExistingNode = existingNode.next;
                historyMap.remove(key);
                prevExistingNode.next = nextExistingNode;
                nextExistingNode.prev = prevExistingNode;
                for (Node<Task> node : historyMap.values()) {
                    Node<Task> oldTailNode = node;
                    if (oldTailNode.next == null) {
                        Node<Task> tailNode = new Node<>(oldTailNode, value, null);
                        oldTailNode.next = tailNode;
                        historyMap.put(key, tailNode);
                        tail = tailNode;
                        return;
                    }
                }

            } else if (existingNode == head) {
                Node<Task> nextExistingNode = existingNode.next;
                historyMap.remove(key);
                head = nextExistingNode;
                nextExistingNode.prev = null;
                for (Node<Task> node : historyMap.values()) {
                    Node<Task> oldTailNode = node;
                    if (oldTailNode.next == null) {
                        Node<Task> tailNode = new Node<>(oldTailNode, value, null);
                        oldTailNode.next = tailNode;
                        historyMap.put(key, tailNode);
                        tail = tailNode;
                        return;
                    }
                }
            } else if (existingNode == tail) {
                Node<Task> prevExistingNode = existingNode.prev;
                historyMap.remove(key);
                Node<Task> tailNode = new Node<>(prevExistingNode, value, null);
                prevExistingNode.next = tailNode;
                historyMap.put(key, tailNode);
                tail = tailNode;

            }
        }
    }

    @Override
    public List<Task> printHistoryMap() {
        List<Task> historyList = new LinkedList<>();
        historyList.add(head.task);
        while (historyList.size() != historyMap.size()) {
            for (Node<Task> node : historyMap.values()) {
                Task lastNode = historyList.getLast();
                Node<Task> newNode = node;
                Node<Task> prevNewNode = newNode.prev;
                if (newNode.prev != null && prevNewNode.task.equals(lastNode)) {
                    historyList.add(newNode.task);
                }
            }
        }
        return historyList;
    }

    @Override
    public void removeFromHistory(Integer key) {
        if (historyMap.containsKey(key)) {
            Node<Task> deletedNode = historyMap.get(key);
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

    @Override
    public void add(Task task) {
        put(task.getTaskId(), task);
    }

    @Override //удаление из истории просмотра
    public void remove(int id) {
        removeFromHistory(id);
    }


    @Override
    public List<Task> getHistory() {
        return printHistoryMap();
    }

    @Override
    public void printHistory() {

        System.out.println("История просмотра");

        for (Object task : printHistoryMap()) {
            System.out.println(task);
        }
    }
}
