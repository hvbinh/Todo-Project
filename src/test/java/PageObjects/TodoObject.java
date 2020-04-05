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
    private By count_lb = By.xpath("//*[@class='todo-count']/strong");
    private By radio_btn = By.xpath("//label[.='a']/preceding-sibling::input");
    private By active_link = By.linkText("Active");
    private By completed_link = By.linkText("Completed");
    private By all_link = By.linkText("All");
    private By todoList = By.xpath("//ul[@class='todo-list']");
    private By remove_btn = By.xpath("//button[@class='destroy']");


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
        return Integer.parseInt(Browsers.driver.findElement(count_lb).getText());
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
    public boolean checkTaskInList(String task)
    {
        List<WebElement> list = listTask();
        for(WebElement e : list)
        {
            if(e.getText().equalsIgnoreCase(task))
            {
                return true;
            }
        }
        return false;
    }
    public String updateTask(String task, String text)
    {
        List<WebElement> list =listTask();
        for(WebElement e:list)
        {

            if(e.getText().equalsIgnoreCase(task))
            {
                String str = e.getText();
                Actions builder = new Actions(Browsers.driver);
                builder.doubleClick(e).sendKeys(text);
                builder.perform();
                return str +=text;
            }
        }
        return list.get(0).getText();
    }
    public int showActiveTasks()
    {
        Browsers.driver.findElement(active_link).click();
        List<WebElement> list = listTask();
        return list.size();

    }
    public int showCompletedTask()
    {
        Browsers.driver.findElement(completed_link).click();
        List<WebElement> list = listTask();
        return list.size();
    }
    public int showAllTask()
    {
        Browsers.driver.findElement(all_link).click();
        List<WebElement> list = listTask();
        return list.size();
    }
    public int uncheckCompletedTask(String task)
    {
        Browsers.driver.findElement(all_link).click();
        completedTask(task);
        return countNumberLeft();
    }
    public int uncheckActiveTask(String task)
    {
        Browsers.driver.findElement(all_link).click();
        completedTask(task);
        return countNumberLeft();
    }
    public void removeActiveTask(String task)
    {
        List<WebElement> list = listTask();
        Actions hover = new Actions(Browsers.getDriver());
        for(WebElement e: list)
        {
            if(e.getText().equalsIgnoreCase(task))
            {
                hover.moveToElement(e).
                        click(removeTask(e.getText()))
                        .perform();
                break;
            }
        }

    }
    public WebElement removeTask(String task)
    {
        return Browsers.find(How.XPATH, String.format("//div/label[.='%s']/following-sibling::button",task));
    }
    public void removeTaskOnAllList(String task)
    {
        Browsers.getDriver().findElement(all_link).click();
        List<WebElement> list = listTask();
        Actions hover = new Actions(Browsers.getDriver());
        for(WebElement e: list)
        {
            if(e.getText().equalsIgnoreCase(task))
            {
                hover.moveToElement(e).
                        click(removeTask(e.getText()))
                        .perform();
                break;
            }
        }
    }
    public void removeCompletedTask(String task)
    {
        Browsers.getDriver().findElement(completed_link).click();
        List<WebElement> list = listTask();
        Actions hover = new Actions(Browsers.getDriver());
        for(WebElement e: list)
        {
            if(e.getText().equalsIgnoreCase(task))
            {
                hover.moveToElement(e).
                        click(removeTask(e.getText()))
                        .perform();
                break;
            }
        }
    }

}
