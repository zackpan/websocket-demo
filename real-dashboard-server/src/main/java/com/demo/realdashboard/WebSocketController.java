package com.demo.realdashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WebSocketController {

    private final List<Task> tasks;

    @Autowired
    private SimpMessagingTemplate template;

    public WebSocketController() {
        tasks = new ArrayList<>();
    }

    @MessageMapping("/add_new_task")
    @SendTo("/tasks/added_task")
    public Task addTask(@RequestBody Task task) {
        tasks.add(task);
        return task;
    }
    
    @PostMapping(value = "/publish")
	public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
		this.publish();
	}    

    public void publish() {
        System.out.println("publish");
        for (Task task : tasks) {
            this.template.convertAndSend("/tasks/added_task", task);        
        }
        
    }    
}