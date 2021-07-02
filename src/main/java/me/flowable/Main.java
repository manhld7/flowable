package me.flowable;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(proxyBeanMethods = false)
public class Main {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner init(final RepositoryService repositoryService, final RuntimeService runtimeService,
			final TaskService taskService) {

		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				
				repositoryService.createDeployment()
                .addClasspathResource("hello-task.bpmn20.xml")
                .deploy();
				
				System.out.println(
						"Number of process definitions : " + repositoryService.createProcessDefinitionQuery().count());
				
				System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
				
				runtimeService.startProcessInstanceByKey("helloTask");
				
				System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
			}
		};
	}
}
