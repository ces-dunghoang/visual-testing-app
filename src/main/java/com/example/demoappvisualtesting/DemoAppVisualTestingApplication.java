package com.example.demoappvisualtesting;
import com.example.demoappvisualtesting.service.SitemapServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoAppVisualTestingApplication implements CommandLineRunner {
  @Resource
  SitemapServiceImpl sitemapService;

  public static void main(String[] args) {
    SpringApplication.run(DemoAppVisualTestingApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    sitemapService.init();
  }
}
