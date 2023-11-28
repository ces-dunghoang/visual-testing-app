package com.example.demoappvisualtesting.controller;
import com.example.demoappvisualtesting.model.entity.Sitemap;
import com.example.demoappvisualtesting.service.ISitemapService;
import com.example.demoappvisualtesting.service.SitemapServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/sitemaps")
@RequiredArgsConstructor
public class SitemapController {
  private final ISitemapService sitemapService;

  @GetMapping
  public List<Sitemap> getAllSitemaps() {
    return sitemapService.getAllSitemaps();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Sitemap> getSitemapById(@PathVariable String id) {
    Optional<Sitemap> sitemap = sitemapService.getSitemap(id);
    return sitemap.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSitemap(@PathVariable String id) {
    sitemapService.deleteSitemap(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
  public ResponseEntity<List<String>> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      List<String> urls = this.sitemapService.extractUrlsFromXml(file.getInputStream());
      sitemapService.storeLocal(file);
      sitemapService.store(file, urls);
      return ResponseEntity.status(HttpStatus.OK).body(urls);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
    }
  }
}

