        package test.java;

        import org.openqa.selenium.*;
        import org.openqa.selenium.chrome.ChromeDriver;
        import org.openqa.selenium.interactions.Actions;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.testng.Assert;
        import org.testng.Reporter;
        import org.testng.annotations.*;
        import org.testng.asserts.Assertion;

        import static org.hamcrest.MatcherAssert.assertThat;
        import static org.hamcrest.Matchers.*;


        import java.awt.*;
        import java.io.PrintStream;
        import java.nio.charset.StandardCharsets;
        import java.time.Duration;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

//        @Listeners(test.java.Listener.class)

public class HomeWorkTestNG {

    WebDriver driver;
    WebElement webElement;
    WebElement webElementOnNextPage;
    WebElement webElementResult;
    String webElementTextToBeCopied;
    Actions act;
    PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    @DataProvider(name = "InputData")
    public Object[][] InputData() {
        return new Object[][] {
                {"QA вакансии"},
                {"разработчики зарплаты"},
                {"рейтинг it компаний"}
        };
    }

    @BeforeMethod(alwaysRun = true)
    public void BeforeEachTest() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Downloads\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to("https://dou.ua/");
        webElement = driver.findElement(By.id("txtGlobalSearch"));
        act = new Actions(driver);
    }

    @Test (groups = {"Positives"})
    public void DouSearch1(){
        webElement.sendKeys("QA вакансии");
        webElement.sendKeys(Keys.ENTER);
        webElementResult = driver.findElement(By.xpath("//div[@class='gsc-wrapper']//div[@class='gs-title']/a[@class='gs-title']"));
        out.println(webElementResult.getText());
        String resultHeader = webElementResult.getText();
        assertThat(resultHeader, anyOf(containsString("QA"), containsString("вакансии"), containsString("Вакансии")));
    }

    @Test (groups = {"Positives"}, dataProvider = "InputData")
    public void DouSearch1WithDataProvider(String keyword) {
        webElement.sendKeys(keyword);
        Reporter.log("Keyword enterd is" + keyword);
        webElement.sendKeys(Keys.ENTER);
        Assert.assertTrue(driver.findElement(By.className("gsc-resultsbox-visible")).isDisplayed());
    }

    @Test (groups = {"Positives"})
    public void DouSearch2() {
        webElementTextToBeCopied = driver.findElement(By.xpath("//span[@class = 'link-title']")).getText().replace("&nbsp;", " ");
        webElement.sendKeys(webElementTextToBeCopied);
        webElement.sendKeys(Keys.ENTER);
        WebElement exWaiter = (new WebDriverWait(driver, Duration.ofSeconds(60)).until(ExpectedConditions.presenceOfElementLocated
                (By.className("gsc-result-info"))));
        String textResult = driver.findElement(By.className("gsc-result-info")).getText();
        out.println(textResult);
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(textResult);
        int start = 0;
        int numResult = 0;
        while (matcher.find(start)) {
            String value = textResult.substring(matcher.start(), matcher.end());
            numResult = Integer.parseInt(value);
            System.out.println(numResult);
            start = matcher.end();
            break;
        };
        Assert.assertNotEquals(numResult, 0);
    }

    @Test (groups = {"Positives"})
    public void DouSearch3( ) {
        String placeholderName = webElement.getAttribute("placeholder");
        Assert.assertTrue(placeholderName.equals("пошук"), "Placeholder name is correct");
    }

    @Test (groups = {"Negatives"})
    public void DouSearch4(){
        act.click(webElement);
        webElement.sendKeys(Keys.ENTER);
//        Object actual = driver.getCurrentUrl();
//        Object expected = "https://dou.ua/";
//        System.out.println(driver.getCurrentUrl());
//        Assert.assertNotSame(actual, expected, "The url is changed");
        webElementOnNextPage = driver.findElement(By.xpath("//input[@id = 'gsc-i-id1']"));
        String attributeValue = webElementOnNextPage.getAttribute("style");
        Assert.assertTrue(attributeValue.contains("https://www.google.com/cse/static/images/1x/ru/branding.png"));
    }

    @Test(groups = {"Negatives"})
    public void DouSearch5(){
        webElement.sendKeys("lghhbnjlhлормитошроkjhgfty578");
        webElement.sendKeys(Keys.ENTER);
        out.println(driver.findElement(By.xpath("//div[@class='gs-snippet']")).getText());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='gs-snippet']")).getText().equals("Ничего не найдено"));
    }

    @AfterMethod(alwaysRun = true)
    public void AfterTest(){
        driver.close();
    }


    //    @Test (groups = {"Positives"})
//    public void DouSearch3additional() throws InterruptedException {
//        webElement.sendKeys("QA вакансии");
//        webElement.sendKeys(Keys.ENTER);
//        webElementOnNextPage = driver.findElement(By.xpath("//td[@id=\"gs_tti50\"]/input"));
//        webElementOnNextPage.sendKeys(" Харьков");
//        WebElement dynamicElement = (new WebDriverWait(driver, Duration.ofSeconds(30))).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class=\"gsc-search-button gsc-search-button-v2\"]")));
//        WebElement button = driver.findElement(By.xpath("//button[@class=\"gsc-search-button gsc-search-button-v2\"]"));
//        button.click();
////        WebElement dynamicElement2 = (new WebDriverWait(driver, Duration.ofSeconds(50))).until(ExpectedConditions.elementToBeClickable(button));
//        Thread.sleep(10000);
//        webElementResult = driver.findElement(By.className("gsc-result-info"));
//        out.println(webElementResult.getText());
//        List<WebElement> webElementHeaders = driver.findElements(By.xpath("//div[@class='gsc-wrapper']//div[@class='gs-title']/a[@class='gs-title']"));
//        List<String> headers = new ArrayList<>();
//        for (WebElement webElementHeader: webElementHeaders) {
//            headers.add(webElementHeader.getText());
////            out.println(webElementHeader.getText().contains(city));
////            CharSequence city = "Харьков";
////            Assert.assertTrue(webElementHeader.getText().contains(city));
//        }
//        for (String header : headers) {
//            String[] words = header.split(" ");
//            CharSequence city = "Харьков";
//            System.out.println(words.toString().contains(city));
//        }
//    }
}
