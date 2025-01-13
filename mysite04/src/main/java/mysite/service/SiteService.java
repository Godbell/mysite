package mysite.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mysite.component.FileManager;
import mysite.repository.SiteRepository;
import mysite.vo.SiteVo;
import mysite.web.SiteMetadata;

@Service
public class SiteService {
    private final SiteRepository siteRepository;
    private final FileManager fileManager;

    public SiteService(SiteRepository siteRepository, FileManager fileManager) {
        this.siteRepository = siteRepository;
        this.fileManager = fileManager;
    }

    public SiteVo getSiteFullInfo() {
        return siteRepository.getFull();
    }

    public SiteMetadata getSiteMetadata() {
        return siteRepository.getMetadata();
    }

    public void updateSiteInfo(
        String title,
        String welcome,
        String description,
        MultipartFile file,
        String uploadDir
    ) throws IOException {
        SiteVo vo = new SiteVo();

        if (file != null && !file.isEmpty()) {
            String filename = fileManager.saveFile(uploadDir, file, "profile");

            if (filename == null) {
                throw new RuntimeException("File upload failed.");
            }

            String filePath = "assets/images/" + filename;
            vo.setProfile(filePath);
        }

        vo.setTitle(title);
        vo.setWelcome(welcome);
        vo.setDescription(description);

        siteRepository.update(vo);
    }
}
