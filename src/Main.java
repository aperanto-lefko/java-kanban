import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        Task buyingCoffee = new Task("Купить кофе", "Зерновой", TaskStatus.NEW);
        Task buyingJam = new Task("Купить варенье", "Малиновое", TaskStatus.NEW);
        manager.add(buyingCoffee);
        manager.add(buyingJam);
        System.out.println("Список задач");
        manager.printTask();
        //manager.searchTaskById(2);
        Task buyingJamNew = new Task("Купить варенье", "Малиновое", 2, TaskStatus.IN_PROGRESS);
        manager.update(buyingJamNew);
        System.out.println("Обновленный список задач");
        manager.printTask();
        manager.removeTaskById(1);
        System.out.println("Обновленный список задач");
        manager.printTask();
        Epic catFood = new Epic("Покормить кота", "Корм для толстых котов и миска", TaskStatus.NEW);
        manager.add(catFood);
        Subtask storeSelection = new Subtask("Выбор магазина", "Магазин около дома", TaskStatus.NEW, 3);
        Subtask foodSelection = new Subtask("Выбор корма", "Корм для толстых котов", TaskStatus.DONE, 3);
        Subtask bowlSelection = new Subtask("Выбор миски", "Миска стеклянная", TaskStatus.DONE, 3);
        manager.add(storeSelection);
        manager.add(foodSelection);
        manager.add(bowlSelection);
        Epic playingTennis = new Epic("Игра в теннис", "Начало в 19.00", TaskStatus.NEW);
        manager.add(playingTennis);
        Subtask racketSelection = new Subtask("Выбор ракетки", "Бренд Wilson", TaskStatus.IN_PROGRESS, 7);
        manager.add(racketSelection);
        System.out.println("Список эпиков");
        manager.printEpic();
        System.out.println("Обновление подзадачи");
        Subtask racketSelectionUpdated = new Subtask("Выбор ракетки", "Бренд HEAD", TaskStatus.NEW, 8, 7);
        manager.update(racketSelectionUpdated);
        System.out.println("Список эпиков с обновленными подзадачами");
        manager.printEpic();
        System.out.println("Обновление эпика");
        Epic playingTennisUpdated = new Epic("Игра в теннис", "Начало в 11.00", 7, TaskStatus.NEW, new ArrayList<>(List.of(8)));
        manager.update(playingTennisUpdated);
        Epic catFoodUpdated = new Epic("Покормить кота хорошим кормом", "Корм для толстых котов и миска", 3, TaskStatus.NEW, new ArrayList<>(List.of(4, 5, 6)));
        manager.update(catFoodUpdated);
        System.out.println("Список обновленных эпиков");
        manager.printEpic();
        System.out.println("Список подзадач конкретного эпика");
        manager.printingRequiredEpic(7);
        System.out.println("Список всех подзадач");
        manager.printSubtask();

    }
}
