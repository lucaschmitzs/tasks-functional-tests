package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
		// WebDriver driver = new ChromeDriver();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://172.30.0.1:4444/wd/hub"), cap);
		
		driver.navigate().to("http://172.30.0.1:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		return driver;
	}

	@Test
	public void adicionarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		
		try {
			// Clicar em Add todo
			driver.findElement(By.id("addTodo")).click();
			
			// Escrever a descrição
			driver.findElement(By.id("task")).sendKeys("Teste via selenium");
			
			// Inserir data
			driver.findElement(By.id("dueDate")).sendKeys("01/01/2050");
			
			// Clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			// Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		} finally {
			// Fechar o browser
			driver.quit();			
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		
		try {
			// Clicar em Add todo
			driver.findElement(By.id("addTodo")).click();
			
			// Inserir data
			driver.findElement(By.id("dueDate")).sendKeys("01/01/2050");
			
			// Clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			// Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);
		} finally {
			// Fechar o browser
			driver.quit();			
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		
		try {
			// Clicar em Add todo
			driver.findElement(By.id("addTodo")).click();
			
			// Escrever a descrição
			driver.findElement(By.id("task")).sendKeys("Teste via selenium");
			
			// Clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			// Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);
		} finally {
			// Fechar o browser
			driver.quit();			
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		
		try {
			// Clicar em Add todo
			driver.findElement(By.id("addTodo")).click();
			
			// Escrever a descrição
			driver.findElement(By.id("task")).sendKeys("Teste via selenium");
			
			// Inserir data
			driver.findElement(By.id("dueDate")).sendKeys("01/01/2010");
			
			// Clicar em salvar
			driver.findElement(By.id("saveButton")).click();
			
			// Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		} finally {
			// Fechar o browser
			driver.quit();			
		}
	}
}
