package br.com.acme.cervejariaacme.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class SeleniumTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    static void setupClass() {

        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }
    @Test
    public void testPageTitle(){
        driver.get("http://localhost:8091/");
        assertEquals("Cervejaria ACME",driver.getTitle());


    }
    //            WebElement textoEsperado = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Texto esperado']")));
    @Test
    public void testLoginErrado() throws InterruptedException {
        driver.get("http://localhost:8091/");

        WebElement usernameField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("submit"));

        usernameField.sendKeys("administrador2@teste.com.br");
        passwordField.sendKeys("123456");
        Thread.sleep(3000);
        loginButton.click();
        Thread.sleep(3000);
        assertEquals("http://localhost:8091/login", driver.getCurrentUrl());



    }
    @Test
    public void testLoginBemSucedido () throws InterruptedException {
        driver.get("http://localhost:8091/");

        WebElement usernameField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("submit"));

        usernameField.sendKeys("administrador@acme.com");
        passwordField.sendKeys("123456");
        Thread.sleep(3000);
        loginButton.click();
        Thread.sleep(3000);
        WebElement logoutButton = driver.findElement(By.id("logout"));
        assertTrue(logoutButton.isDisplayed(), "Botão de logout deve estar visível após login");

    }
    @AfterEach
    void teardown() {
        driver.quit();
    }
}
