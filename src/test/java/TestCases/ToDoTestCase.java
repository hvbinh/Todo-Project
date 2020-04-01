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
        int count = todo.listTask().size();
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
