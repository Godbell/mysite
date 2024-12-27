package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.service.BoardService;
import mysite.vo.BoardVo;

@RequestMapping("/board")
@Controller
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getBoard(
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "q", required = false) String q,
        Model model
    ) {
        BoardVo boardVo = boardService.getBoard(page, q);
        model.addAttribute("list", boardVo.getPosts());
        model.addAttribute("postsCountPerPage", boardVo.getPostsCountPerPage());
        model.addAttribute("totalCount", boardVo.getTotalCount());
        model.addAttribute("currentPage", boardVo.getCurrentPage());

        return "board/list";
    }
}
