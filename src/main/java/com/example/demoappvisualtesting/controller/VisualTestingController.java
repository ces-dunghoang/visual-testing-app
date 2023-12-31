package com.example.demoappvisualtesting.controller;
import com.example.demoappvisualtesting.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/visual-testing")
public class VisualTestingController {

  private RabbitMQProducer producer;

  public VisualTestingController(RabbitMQProducer producer) {
    this.producer = producer;
  }

  // http://localhost:8080/api/v1/publish?message=hello
  @GetMapping
  public ResponseEntity<String> visualTesting(){
    String message = "visual-testing";
    producer.sendMessage(message);
    return ResponseEntity.ok("We will notify you when completed");
  }
}
