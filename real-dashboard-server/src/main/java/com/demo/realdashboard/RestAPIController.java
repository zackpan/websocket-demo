package com.demo.realdashboard;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class RestAPIController {
    
    @PostMapping(value = "/start")
	public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
		this.publish();
	}    

    public void publish() {
        System.out.println("publish");
    }       
}
