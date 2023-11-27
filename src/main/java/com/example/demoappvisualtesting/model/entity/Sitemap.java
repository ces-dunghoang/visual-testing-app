package com.example.demoappvisualtesting.model.entity;
// Sitemap.java
import java.util.List;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sitemaps")
public class Sitemap {
  @Id
  private String id;

  private String name;

  private String type;

  private Binary data;

  private List<String> urls; // List of URLs associated with the sitemap

  public Sitemap(String name, String type, Binary data, List<String> urls) {
    this.name = name;
    this.type = type;
    this.data = data;
    this.urls = urls;
  }
}

