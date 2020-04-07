package TestCases;

import CrossBrowsers.Browsers;
import PageObjects.TodoObject;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ToDoTestCase {
    private TodoObject todo = new TodoObject();

    @BeforeClass
    public static void setUpBrowser()
    {
        Browsers.OpenBrowser("chrome");

    }


    @Test(priority = 1,description = "Verify that add task works correctly",dataProvider = "setTask")
    public void addTask(String task)
    {
        todo.loadPage();
        todo.addTask(task);
        int count = todo.listTask("all").size();
        Assert.assertEquals(todo.countNumberLeft(), count);
    }
    @Test(priority = 2,description = "Verify that remove task on all list works correctly",dataProvider = "removeTask")
    public void removeTask(String task)
    {
        todo.completedTask(task);
    }
    @Test(description = "Verify that update task on all list works correctly",dataProvider = "updateTask",enabled = false)
    public void updateTask(String oldTask, String newTask)
    {
        String e = todo.updateTask(oldTask,newTask);
        String text = oldTask+newTask;
        System.out.print("e="+e);
        Assert.assertEquals(e, text);

    }
    @Test(priority = 3,description = "Verify that active list shows correctly")
    public void showActiveList()
    {
        int i = todo.showActiveTasks();
        Assert.assertEquals(i, todo.countNumberLeft());
    }
    @Test(priority = 4,description = "Verify that completed list shows correctly")
    public void showCompletedList()
    {
        int i = todo.showCompletedTask();
        Assert.assertEquals(i, 3);
    }
    @Test(priority = 5,description = "Verify that completed list shows correctly")
    public void showAllList()
    {
        int i = todo.showAllTask();
        Assert.assertEquals(i, 5);
    }
    @Test(priority = 6,description = "Verify that 'count item left' wil increase after adding new task")
    public void checkAddCount()
    {
        todo.addTask("task 6");
        int i = todo.countNumberLeft();
        int j= todo.showActiveTasks();
        Assert.assertEquals(i, j);
    }
    @Test(priority = 7,description = "Verify that 'count item left' wil increase after uncheck completed task")
    public void checkCountAfterUncheckCompletedTask()
    {
        int countBefore = todo.countNumberLeft();
        int CountAfter=todo.uncheckCompletedTask("task 3");
        Assert.assertEquals(CountAfter, countBefore+1);
    }
    @Test(priority = 8,description = "Verify that 'count item left' wil decreased after Unselect active task")
    public void checkCountAfterUncheckActiveTask()
    {
        int countBefore = todo.countNumberLeft();
        int CountAfter=todo.uncheckActiveTask("task 3");
        Assert.assertEquals(CountAfter, countBefore-1);
    }
    @Test(priority = 9,description = "Verify that 'count item left' wil decreased after remove an active task")
    public void removeActiveTask()
    {
        int i = todo.showActiveTasks();
        todo.removeActiveTask("task 5");
        boolean check = todo.checkTaskInList("task 5");
        Assert.assertFalse(check);
        int j = todo.showActiveTasks();
        Assert.assertEquals(j,i-1);
    }
    @Test(priority = 10,description = "Verify that remove task on all list works correctly")
    public void removeTaskOnAllList()
    {
        todo.removeTaskOnAllList("task 1");
        boolean check = todo.checkTaskInList("task 1");
        Assert.assertFalse(check);
    }
    @Test(priority = 11,description = "Verify that remove task on completed list correctly")
    public void removeTaskOnCompletedTask()
    {
        todo.removeCompletedTask("task 2");
        boolean check = todo.checkTaskInList("task 2");
        Assert.assertFalse(check);
    }
    @Test(priority = 12,description = "Verify that clear completed task works correctly")
    public void removeAllCompletedTask()
    {
        int i = todo.clearAllCompletedTask();
        Assert.assertEquals(i, 0);

    }
    @Test(priority = 13,description = "Verify that all list will show after refreshing page")
    public void showAllTaskOnAllList()
    {
        int i = todo.listTask("all").size();
        todo.refreshPageOnAllList();
        int j = todo.listTask("all").size();
        Assert.assertEquals(i, j);
    }
    @Test(priority = 14,description = "Verify that active list will show after refreshing page")
    public void showAllTaskOnActiveList()
    {
        int i = todo.listTask("active").size();
        todo.refreshPageOnActiveList();
        int j = todo.listTask("active").size();
        Assert.assertEquals(i, j);
    }
    @Test(priority = 15,description = "Verify that completed list will show after refreshing page")
    public void showAllTaskOnCompletedList()
    {
        int i = todo.listTask("completed").size();
        todo.refreshPageOnCompletedList();
        int j = todo.listTask("completed").size();
        Assert.assertEquals(i, j);
    }
    @DataProvider(name = "setTask")
    public Object[][] setTask(){
        return new Object[][]
                {
                        {"task 1"},
                        {"task 2"},
                        {"task 3"},
                        {"task 4"},
                        {"task 5"},
                };

    }
    @DataProvider(name = "removeTask")
    public Object[][] removeTask(){
        return new Object[][]
                {
                        {"task 1"},
                        {"task 2"},
                        {"task 3"},
                };

    }
    @DataProvider(name = "updateTask")
    public Object[][] updateTask(){
        return new Object[][]
                {
                        {"task 4","my update"}
                };

    }

}
