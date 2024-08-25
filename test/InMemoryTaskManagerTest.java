import managers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;



class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void setManager() {
        this.taskManager = manager.getDefault();
    }
}





