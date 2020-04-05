package CrossBrowsers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Browsers {
    public static WebDriver driver;
    public static WebDriverWait wait;
    private static long TIMEOUT =60;
    public static WebDriver getDriver()
    {
        return driver;
    }

    public static void OpenBrowser(String BrowserName)
    {
        switch (BrowserName.toLowerCase()) {
            case "chrome": {
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                break;
            }
            case "chrome-headless": {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
                break;
            }
            case "chrome-mobile":
            {
                Map<String, String> mobileEmulation = new HashMap<>();

                //mobileEmulation.put("deviceName", "Nexus 5");
                mobileEmulation.put("deviceName", "iPad Pro");


                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

                driver = new ChromeDriver(chromeOptions);
                break;
            }
            case "coccoc":
            {
                System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");

                ChromeOptions options = new ChromeOptions();
                options.setBinary("C:\\Users\\admin\\AppData\\Local\\CocCoc\\Browser\\Application\\browser.exe");
                driver = new ChromeDriver(options);
                break;
            }
            case "edge":
            {
                System.setProperty("webdriver.edge.driver","drivers/MicrosoftWebDriver.exe");
                driver = new EdgeDriver();
                break;
            }
            case "firefox":
            {
                driver = new FirefoxDriver();
                break;
            }
            case "firefox-headless":
            {
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                firefoxBinary.addCommandLineOptions("--headless");

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);

                driver = new FirefoxDriver(firefoxOptions);
                break;
            }
            case "ie":
            {
                driver = new InternetExplorerDriver();
                break;
            }
            default:
                throw new IllegalStateException("undefine browser: "+BrowserName);
        }
        wait = new WebDriverWait(driver, TIMEOUT);
    }


    public static void visit(String url)
    {
        driver.get(url);
    }
    public static WebElement getElement(How by, String locator)
    {
        return driver.findElement(by.buildBy(locator));
    }
    public static void click(How by, String locator)
    {
        getElement(by,locator).click();
    }
    public  static void refreshPage()
    {
        driver.navigate().refresh();
    }
    public static void backToPreviousPage()
    {
        driver.navigate().back();
    }
    public static void fill(How by,String locator, String withText)
    {
        getElement(by, locator).sendKeys(withText);
    }
    public static WebElement find(How how,String locator)
    {
        return driver.findElement(how.buildBy(locator));
    }
    public static void check(How by,String locator)
    {

        if(!getElement(by,locator).isSelected())
        {
            click(by,locator);
        }

    }
    public static void uncheck(How by,String locator)
    {

        if(getElement(by,locator).isSelected())
        {
            click(by,locator);
        }

    }
    public static void waitTime(long time)
    {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);

    }
    public static void dropdownList(How by,String locatorDropdown, String option)
    {
        Select make = new Select(getElement(by,locatorDropdown));
        int options = make.getOptions().size();
        System.out.print("there are: "+options+" option\n");
        System.out.print(make.getFirstSelectedOption().getText()+"\n");
        make.selectByVisibleText(option);
        System.out.print(make.getFirstSelectedOption().getText());


    }
    // tu viet
    public static void dragAndDrop(How by1,String locator1, How by2, String locator2) throws InterruptedException {

        WebElement source = getElement(by1,locator1);
        WebElement target = getElement(by2,locator2);
        //click(by1,locator1);
        driver.switchTo().frame(getElement(How.XPATH,"//*[@id=\"content\"]/iframe"));
        Actions builder = new Actions(driver);
        waitTime(30);
        builder.dragAndDrop(source,target).perform();
        // Action drapdrop = builder.clickAndHold(source).moveToElement(target).release(target).build();
        //drapdrop.perform();
    }
    public static void table(How by, String locator)
    {
        WebElement tableBasic = getElement(by, locator);
        List<WebElement> rows = tableBasic.findElements(By.xpath("//*[@class='flextable']/div"));
        List<WebElement> cols = tableBasic.findElements(By.xpath("//*[@class='flextable']/div[1]/div"));
        System.out.println("rows ="+rows.size());
        System.out.println("Cols ="+cols.size());
       /* for(WebElement row : rows)
        {
            List<WebElement> cols = row.findElements(By.tagName("div"));
            for(WebElement col : cols)
            {
                System.out.print(col.getText() + "   ");
            }
            System.out.println();
        }*/
        for(int row =1;row<=rows.size();row++)
        {
            for(int col=1;col<=cols.size();col++)
            {
                String cell = getElement(How.XPATH,String.format("//*[@class='flextable']/div[%d]/div[%d]",row,col)).getText();
                System.out.print(cell+"\t\t");
            }
            System.out.println("\n");
        }
    }
    public static void captureScreenShort()
    {
        try{
            TakesScreenshot ts=(TakesScreenshot)driver;

            File source=ts.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(source,new File("C:\\Users\\admin\\Desktop\\picture\\hinh.png"));
            // FileUtils.copyFile(source,new File(""));
            //FileUtils.copyFile();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void dropDownList1(How by,String locator)
    {
        WebElement dropDown = getElement(by,locator);
        Select make = new Select(dropDown);
        if(make.isMultiple())
        {
            System.out.println("this is multi select");
        }
        else
        {
            System.out.println("this is not multi select");
        }
        // int n = make.getOptions().size();
        int n = driver.findElements(By.tagName("option")).size();
        System.out.println("have: "+n);
        make.selectByVisibleText("Option 1");
        //make.selectByIndex(1);
        //make.selectByValue("");


    }
    public static void check1(How by, String locator)
    {
        if(!getElement(by,locator).isSelected())
        {
            click(by,locator);
        }
    }
    public static void uncheck1(How by, String locator)
    {
        if(getElement(by,locator).isSelected())
        {
            click(by,locator);
        }
    }
    public static List<WebElement> all(How by, String locator)
    {
        return driver.findElements(by.buildBy(locator));
    }
    public static void captureScreenShort1()
    {
        File screenShot =  ( (TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShot, new File(".\\target\\screenshot-"+ System.currentTimeMillis()+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getText(How by,String locator)
    {

        return getElement(by,locator).getText();
    }
    public static void waitVisibleElement(How by,String locator)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by.buildBy(locator)));
    }
    public static void waitInvisibleElement(How by,String locator)
    {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by.buildBy(locator)));
    }

}
