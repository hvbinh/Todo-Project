package PageObjects;

import CrossBrowsers.Browsers;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.How;

import java.util.List;

public class TodoObject {

    private By task_txt = By.xpath("//input[@class='new-todo']");
    private By countlb = By.xpath("//*[@class='todo-count']/strong");
    private By radio = By.xpath("//label[.='a']/preceding-sibling::input");
    private By todoList = By.xpath("//ul[@class='todo-list']");


    private WebElement radio(String task)
    {
         return Browsers.getElement(How.XPATH, String.format("//label[.='%s']/preceding-sibling::input",task));
    }


    private String url = "http://todomvc.com/examples/vanillajs";

    public void loadPage()
    {
        Browsers.driver.get(url);
    }

    public void addTask(String text) {
        Browsers.driver.findElement(task_txt).sendKeys(text);
        Browsers.driver.findElement(task_txt).sendKeys(Keys.ENTER);
    }
    public int countNumberLeft()
    {
        return Integer.parseInt(Browsers.driver.findElement(countlb).getText());
    }
    public void completedTask(String task)
    {
        radio(task).click();
    }
    public List<WebElement> listTask()
    {
        WebElement elementTable = Browsers.driver.findElement(todoList);
        return elementTable.findElements(By.tagName("li"));
    }
    public void updateTask(String task, String text)
    {
        List<WebElement> list =listTask();
        for(WebElement e:list)
        {
            if(e.getText().equalsIgnoreCase(task))
            {
                Actions builder = new Actions(Browsers.driver);
                builder.doubleClick(e).sendKeys(text);
                builder.doubleClick(Browsers.driver.findElement(task_txt)).sendKeys(Keys.ENTER);
            }
        }
    }
    public void clearTask()
    {
        Browsers.driver.findElement(task_txt).clear();
    }


}
