package com.example.demoappvisualtesting.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visual-testing")
@RequiredArgsConstructor
public class VisualTestingController {
  @GetMapping
  public String getVisualTesting() {
    String command = "npx playwright test";
    try {
      // Create a process builder
      ProcessBuilder processBuilder = new ProcessBuilder(command.split("\\s+"));// Redirect error stream to output stream
      processBuilder.redirectErrorStream(true);
      // Start the process
      Process process = processBuilder.start();
      // Read the output of the command
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
      // Wait for the process to finish
      int exitCode = process.waitFor();
      System.out.println("Exited with error code " + exitCode);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return "Visual testing ok";
  }
}


