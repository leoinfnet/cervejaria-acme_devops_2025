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

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class CadastrarTiposTests {
    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeAll
    static void setupClass() {

        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }
    @Test
    public void cadastrarTipo() throws InterruptedException {
        driver.get("http://localhost:8091/");

        WebElement usernameField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("submit"));

        usernameField.sendKeys("administrador@acme.com");
        passwordField.sendKeys("123456");
        loginButton.click();
        Thread.sleep(2000);
        WebElement spanElement = driver.findElement(By.id("tiposLink"));
        assertTrue(spanElement.isDisplayed(), "Elemento <span> com o texto 'Marcas' não foi encontrado.");
        spanElement.click();
        assertEquals("http://localhost:8091/tipo-cervejas/index", driver.getCurrentUrl());
        WebElement adicionarTipo = driver.findElement(By.id("adicionarTipoCerveja"));
        adicionarTipo.click();
        Thread.sleep(2000);
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Adicionar']"));
        submitButton.click();
        Thread.sleep(2000);
        WebElement nomeCampo = driver.findElement(By.id("tipoCerveja"));


        //wait.until(ExpectedConditions.attributeContains(nomeCampo, "class", "is-invalid"));
        WebElement erroNome = driver.findElement(By.cssSelector(".invalid-feedback"));
        assertTrue(erroNome.isDisplayed(), "A mensagem de erro não foi exibida para o campo nome.");




    }
    @AfterEach
    void teardown() {
        driver.quit();
    }
}
