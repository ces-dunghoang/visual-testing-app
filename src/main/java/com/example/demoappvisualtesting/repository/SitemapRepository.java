package com.example.demoappvisualtesting.repository;
// SitemapRepository.java
import com.example.demoappvisualtesting.model.entity.Sitemap;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SitemapRepository extends MongoRepository<Sitemap, String> {
}

