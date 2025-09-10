package lk.ijse.gdse72.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }
}