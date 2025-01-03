package mysite.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class FileManager {
    public String saveFile(String path, MultipartFile file, String filename) throws IOException {
        File uploadDirectory = new File(path);
        System.out.println("uploading file to " + path);

        if (
            (!uploadDirectory.exists() && !uploadDirectory.mkdirs())
            || file.isEmpty()
        ) {
            return null;
        }

        String originFilename = Optional.of(file.getOriginalFilename()).orElse("");
        int dotIndex = originFilename.lastIndexOf('.');
        String extName = dotIndex == -1
            ? ""
            : originFilename.substring(originFilename.lastIndexOf('.') + 1);

        String savedFilename = filename != null ? filename + "." + extName : generateSaveFilename(extName);

        byte[] data = file.getBytes();

        try (OutputStream os = new FileOutputStream(path + "/" + savedFilename)) {
            os.write(data);
        }

        return savedFilename;
    }

    private String generateSaveFilename(String extName) {
        Calendar calendar = Calendar.getInstance();

        return ""
               + calendar.get(Calendar.YEAR)
               + calendar.get(Calendar.MONTH)
               + calendar.get(Calendar.DATE)
               + calendar.get(Calendar.HOUR)
               + calendar.get(Calendar.YEAR)
               + calendar.get(Calendar.MINUTE)
               + calendar.get(Calendar.SECOND)
               + calendar.get(Calendar.MILLISECOND)
               + ("." + extName);
    }
}
