import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Managers manager = new Managers();
        TaskManager taskManager = manager.getDefault();

        Task buyingCoffee = new Task("Купить кофе", "Зерновой", TaskStatus.NEW);
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        taskManager.add(buyingCoffee);
        taskManager.add(buyingJam);
        System.out.println("Список задач");
        taskManager.printTask();
        //taskManager.searchTaskById(2);
        Task buyingJamNew = new Task("Купить варенье", "Малиновое", 2, TaskStatus.IN_PROGRESS);
        taskManager.update(buyingJamNew);
        System.out.println("Обновленный список задач");
        taskManager.printTask();
        taskManager.removeTaskById(1);
        System.out.println("Обновленный список задач");
        taskManager.printTask();
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        taskManager.add(catFood);
        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, 3);
        Subtask foodSelection = new Subtask("Выбор корма", "Корм для толстых котов", TaskStatus.DONE, 3);
        Subtask bowlSelection = new Subtask("Выбор миски", "Миска стеклянная", TaskStatus.DONE, 3);
        taskManager.add(storeSelection);
        taskManager.add(foodSelection);
        taskManager.add(bowlSelection);
        Epic playingTennis = new Epic("Игра в теннис", "Начало в 19.00", TaskStatus.NEW);
        taskManager.add(playingTennis);
        Subtask racketSelection = new Subtask("Выбор ракетки", "Бренд Wilson", TaskStatus.IN_PROGRESS, 7);
        taskManager.add(racketSelection);
        System.out.println("Список эпиков");
        taskManager.printEpic();
        System.out.println("Обновление подзадачи");
        Subtask racketSelectionUpdated = new Subtask("Выбор ракетки", "Бренд HEAD", TaskStatus.NEW, 8, 7);
        taskManager.update(racketSelectionUpdated);
        System.out.println("Список эпиков с обновленными подзадачами");
        taskManager.printEpic();
        System.out.println("Обновление эпика");
        Epic playingTennisUpdated = new Epic("Игра в теннис", "Начало в 11.00", 7, TaskStatus.NEW, new ArrayList<>(List.of(8)));
        taskManager.update(playingTennisUpdated);
        Epic catFoodUpdated = new Epic("Покормить кота хорошим кормом", "Корм для толстых котов и миска", 3, TaskStatus.NEW, new ArrayList<>(List.of(4, 5, 6)));
        taskManager.update(catFoodUpdated);
        System.out.println("Список обновленных эпиков");
        taskManager.printEpic();
        //System.out.println("Список подзадач конкретного эпика");
        //taskManager.printingRequiredEpic(7);
        //System.out.println("Список всех подзадач");
        //taskManager.printSubtask();
        System.out.println("Удаление подзадачи");
        taskManager.removeSubtaskById(6);
        System.out.println("Список эпиков с обновленными подзадачами");
        taskManager.printEpic();
        taskManager.searchTaskById(2);
        taskManager.searchEpicById(7);
        taskManager.searchSubtaskById(8);
        taskManager.getHistory();

    }
}
