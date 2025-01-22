package mysite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mysite.repository.GuestbookRepository;
import mysite.vo.GuestbookVo;

@Service
@AllArgsConstructor
public class GuestbookService {
    private GuestbookRepository guestbookRepository;

    public List<GuestbookVo> getContentsList() {
        return guestbookRepository.findAll();
    }

    public void deleteContents(Long id, String password) {
        guestbookRepository.deleteByIdAndPassword(id, password);
    }

    public void addContents(GuestbookVo vo) {
        guestbookRepository.insert(vo);
    }
}