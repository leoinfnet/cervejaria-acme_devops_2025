package br.com.acme.cervejariaacme.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class CadastrarMarcaTests {
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
    @DisplayName("Deve cadastrar uma marca de cerveja")
    public void cadastrarMarca() throws InterruptedException  {
        driver.get("http://localhost:8091/");

        login();
        Thread.sleep(3000);

        //WebElement spanElement = driver.findElement(By.xpath("//span[text()='Marcas']"));
        int quantidadeLinhas = navegarAteCadastrar();
        WebElement tabela;
        java.util.List<WebElement> linhas;

        WebElement nome = driver.findElement(By.id("nomeMarca"));
        WebElement pais = driver.findElement(By.id("paisMarca"));
        WebElement email = driver.findElement(By.id("emailMarca"));
        WebElement dataFundacao = driver.findElement(By.id("dtfundacaoMarca"));


        nome.sendKeys("Marca Teste");
        pais.sendKeys("Brasil");
        email.sendKeys("marca@acme.com");
        dataFundacao.sendKeys("2020-05-05");
        Actions actions = new Actions(driver);
        actions.moveByOffset(100, 100).click().perform(); // Clique na posição (100, 100)

        Thread.sleep(2000);

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Adicionar']"));
        submitButton.click();
        Thread.sleep(3000);
        tabela = driver.findElement(By.id("tiposCervejasTable"));
        linhas = tabela.findElements(By.tagName("tr"));
        int novaQuantidadeLinhas = linhas.size() - 1;
        assertEquals(novaQuantidadeLinhas, quantidadeLinhas +1);
    }
    @Test
    @DisplayName("Deve testar as validações")
    public void testaValidacoes() throws InterruptedException  {
        driver.get("http://localhost:8091/");

        login();
        Thread.sleep(3000);

        //WebElement spanElement = driver.findElement(By.xpath("//span[text()='Marcas']"));
        int quantidadeLinhas = navegarAteCadastrar();
        WebElement tabela;
        java.util.List<WebElement> linhas;

        WebElement nome = driver.findElement(By.id("nomeMarca"));
        WebElement pais = driver.findElement(By.id("paisMarca"));
        WebElement email = driver.findElement(By.id("emailMarca"));
        WebElement dataFundacao = driver.findElement(By.id("dtfundacaoMarca"));


        //nome.sendKeys("Marca Teste");
        pais.sendKeys("Brasil");
        email.sendKeys("marca@acme.com");
        dataFundacao.sendKeys("2020-05-05");
        Actions actions = new Actions(driver);
        actions.moveByOffset(100, 100).click().perform(); // Clique na posição (100, 100)

        Thread.sleep(2000);

        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Adicionar']"));
        submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.attributeContains(By.id("nomeMarca"), "class", "is-invalid"));
        WebElement campoNome = driver.findElement(By.id("nome")); // Substitua pelo ID do campo 'nome'
        String classeCampo = campoNome.getAttribute("class");
        log.info(classeCampo);
        //Thread.sleep(2000);

//        WebElement campoTexto = driver.findElement(By.id("nomeMarca"));
//        WebElement elementoPai = campoTexto.findElement(By.xpath(".."));
//
//        log.info(campoTexto.getAttribute("class"));
//        String classes = elementoPai.getAttribute("class");
//        log.info(classes);
//
//        WebElement campoComErro = driver.findElement(By.id("nomeMarca")); // Localizando novamente o campo
//
//        // Verificando se a classe foi adicionada dinamicamente
//        String classeCampo = campoComErro.getAttribute("class");
//        assertTrue(classeCampo.contains("is-invalid"), "A classe 'is-invalid' não foi adicionada ao campo.");
//        Thread.sleep(3000);


    }

    private int navegarAteCadastrar() {
        WebElement spanElement = driver.findElement(By.xpath("//span[text()='Marcas']/ancestor::a"));
        assertTrue(spanElement.isDisplayed(), "Elemento <span> com o texto 'Marcas' não foi encontrado.");
        spanElement.click();
        assertEquals("http://localhost:8091/marcas/index", driver.getCurrentUrl());


        WebElement tabela = driver.findElement(By.id("tiposCervejasTable"));
        java.util.List<WebElement> linhas = tabela.findElements(By.tagName("tr"));
        int quantidadeLinhas = linhas.size() - 1;

        WebElement cadastrarMarca = driver.findElement(By.xpath("//a[text()='Adicionar Marca de Cerveja']"));
        assertTrue(cadastrarMarca.isDisplayed(), "Elemento <span> com o texto 'Marcas' não foi encontrado.");
        cadastrarMarca.click();
        assertEquals("http://localhost:8091/marcas/adicionarForm", driver.getCurrentUrl());
        return quantidadeLinhas;
    }

    private void login() {
        WebElement usernameField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("submit"));

        usernameField.sendKeys("administrador@acme.com");
        passwordField.sendKeys("123456");
        loginButton.click();
    }


    @AfterEach
    void teardown() {
        driver.quit();
    }
}
