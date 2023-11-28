package com.example.demoappvisualtesting.service;
import com.example.demoappvisualtesting.model.entity.Sitemap;
import com.example.demoappvisualtesting.repository.SitemapRepository;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.jdom2.Namespace;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.FileAlreadyExistsException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

@Service
@RequiredArgsConstructor
public class SitemapServiceImpl implements ISitemapService {
  private final SitemapRepository sitemapRepository;

  private final Path root = Paths.get("uploads");

  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  public Sitemap store(MultipartFile file, List<String> urls) throws IOException {
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    Binary fileData = new Binary(file.getBytes());
    Sitemap sitemap = new Sitemap(fileName, file.getContentType(), fileData, urls);
    return sitemapRepository.save(sitemap);
  }

  public void storeLocal(MultipartFile file) throws IOException {
    try {
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }
      throw new RuntimeException(e.getMessage());
    }
  }

  public Optional<Sitemap> getSitemap(String id) {
    return sitemapRepository.findById(id);
  }

  public List<Sitemap> getAllSitemaps() {
    return sitemapRepository.findAll();
  }

  public void deleteSitemap(String id) {
    sitemapRepository.deleteById(id);
  }

  public List<String> extractUrlsFromXml(InputStream inputStream) throws IOException {
    List<String> urls = new ArrayList<>();
    try {
      SAXBuilder saxBuilder = new SAXBuilder();
      Document document = saxBuilder.build(inputStream);
      Element rootElement = document.getRootElement();
      Namespace namespace = rootElement.getNamespace(); // Assuming the XML has a namespace

      List<Element> urlElements = rootElement.getChildren("url", namespace);
      for (Element urlElement : urlElements) {
        Element locElement = urlElement.getChild("loc", namespace);
        if (locElement != null) {
          String url = locElement.getTextNormalize();
          urls.add(url);
        }
      }
    } catch (Exception e) {
      // Handle parsing exceptions
      throw new IOException("Error extracting URLs from XML", e);
    }

    return urls;
  }
}

