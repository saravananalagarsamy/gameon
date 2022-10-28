package com;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.text.CaseUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class GameOn {

    public static void main(String[] args){
        if (args.length > 0) {
            GameOn game = new GameOn();
            game.reservation(args[0],args[1]);
        }

    }

    public void reservation(String username, String password){
        ClassLoader classLoader = getClass().getClassLoader();
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--window-size=1920,1200");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.avalonaccess.com/UserProfile/LogOn");
        driver.findElement(By.id("UserName")).click();
        driver.findElement(By.id("UserName")).sendKeys(username);
        //driver.findElement(By.id("UserName")).sendKeys("Mahendran.suryanarayanan@gmail.com");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys(password);
        //driver.findElement(By.cssSelector("#submit-sign-in > span:nth-child(1)")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.avalonaccess.com/Information/Information/Amenities");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("reserve")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        LocalDate currentDate = LocalDate.now();
        currentDate = currentDate.plusDays(8);
        DayOfWeek dow = currentDate.getDayOfWeek();
        int dom = currentDate.getDayOfMonth();
        int doy = currentDate.getDayOfYear();
        Month m = currentDate.getMonth();
        String mont = CaseUtils.toCamelCase(m.toString().substring(0, 3), true);
        System.out.println(dom + m.toString().substring(0, 3));
        int y = currentDate.getYear();

        driver.findElement(By.id("resv-date")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement dropdown = driver.findElement(By.xpath("//body/div[@id='ui-datepicker-div']/div[1]/div[1]/select[1]"));
        dropdown.findElement(By.xpath("//option[. = '" + mont + "']")).click();
        driver.findElement(By.linkText(String.valueOf(dom))).click();
        System.out.println("date selected ");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        {
            Select dropdownStartTime = new Select(driver.findElement(By.id("SelStartTime")));
            dropdownStartTime.selectByValue("6:00 PM ");//(By.xpath("//option[. = '4:00 PM ']")).click();
            System.out.println("start Time selected");
        }

        {
            Select dropdownEndTime = new Select(driver.findElement(By.id("SelEndTime")));
            dropdownEndTime.selectByValue("7:00 PM ");//findElement(By.xpath("//option[. = '5:00 PM ']")).click();
            System.out.println("start Time selected");
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.id("EventTitle")).sendKeys("badminton");
        driver.findElement(By.id("NumberOfPeople")).clear();
        driver.findElement(By.id("NumberOfPeople")).sendKeys("4");
        driver.findElement(By.cssSelector("#submit-new-reservation > span")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if(driver.findElement(By.cssSelector(".field-validation-error")).isDisplayed()){
            System.out.println("Sorry unable to book reservation");
        }
        else {
            System.out.println("Reservaton successful");
        }
        driver.quit();
    }
}
