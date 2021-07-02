package me.flowable.controller;

import java.util.ArrayList;
import java.util.List;

import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.flowable.service.FlowableService;

@RestController
public class FlowableController {

	@Autowired
	private FlowableService flowableService;

	@GetMapping("/hello")
	public String sayHello() {
		return "hello world!";
	}

	@PostMapping("/process")
	public void startProcessInstance() {
		flowableService.startProcess();
	}

	@RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
		List<Task> tasks = flowableService.getTasks(assignee);
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for (Task task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName()));
		}
		return dtos;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	static class TaskRepresentation {

		private String id;
		private String name;

	}
}
