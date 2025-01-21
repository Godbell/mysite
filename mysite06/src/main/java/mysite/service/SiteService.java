package mysite.service;

import java.io.IOException;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import mysite.component.FileManager;
import mysite.repository.SiteRepository;
import mysite.vo.SiteVo;
import mysite.web.SiteMetadata;

@Service
@PropertySource("classpath:/mysite/config/web/fileupload.properties")
public class SiteService {
    private final SiteRepository siteRepository;
    private final FileManager fileManager;
    private final Environment env;

    public SiteService(SiteRepository siteRepository, FileManager fileManager, Environment env) {
        this.siteRepository = siteRepository;
        this.fileManager = fileManager;
        this.env = env;
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
        HttpServletRequest req
    ) throws IOException {
        SiteVo vo = new SiteVo();

        if (file != null && !file.isEmpty()) {
            String filename = fileManager.saveFile(
                req.getServletContext().getRealPath(env.getProperty("fileupload.location")), file, "profile"
            );

            if (filename == null) {
                throw new RuntimeException("File upload failed.");
            }

            String filePath = env.getProperty("fileupload.resourceUrl") + filename;
            vo.setProfile(filePath);
        }

        vo.setTitle(title);
        vo.setWelcome(welcome);
        vo.setDescription(description);

        siteRepository.update(vo);
    }
}
