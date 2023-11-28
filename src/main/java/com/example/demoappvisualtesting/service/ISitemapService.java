package com.example.demoappvisualtesting.service;
import com.example.demoappvisualtesting.model.entity.Sitemap;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface ISitemapService {
  void init();

  Sitemap store(MultipartFile file, List<String> urls) throws IOException;

  void storeLocal(MultipartFile file) throws IOException;

  Optional<Sitemap> getSitemap(String id);

  List<Sitemap> getAllSitemaps();

  void deleteSitemap(String id);

  List<String> extractUrlsFromXml(InputStream inputStream) throws IOException;
}
