package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;
import mysite.vo.PostVo;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Long addPost(PostVo vo) {
        return boardRepository.insert(vo).getId();
    }

    public PostVo getPost(Long id) {
        return boardRepository.findById(id);
    }

    public PostVo getPost(Long id, Long userId) {
        return boardRepository.findByIdAndUserId(id, userId);
    }

    public void updatePost(PostVo vo) {
        boardRepository.update(vo);
    }

    public void deletePost(Long id, Long userId) {
        boardRepository.deleteByIdAndUserId(id, userId);
    }

    public BoardVo getBoard(Integer page, String keyword) {
        return boardRepository.findAll(page, 5, keyword);
    }

    public void increasePostHit(Long postId) {
        boardRepository.increaseHitByPostId(postId);
    }
}
