package mysite.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.PostVo;
import mysite.vo.UserVo;

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
        model.addAttribute("q", q);

        return "board/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String viewCreatePost() {
        return "board/write";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String createPost(Authentication authentication, PostVo vo) {
        UserVo authUser = (UserVo)authentication.getPrincipal();
        vo.setUserId(authUser.getId());
        Long uploadedPostId = boardService.addPost(vo);

        return "redirect:/board/" + uploadedPostId;
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public String viewPost(@PathVariable("postId") Long postId, Model model) {
        PostVo vo = boardService.getPost(postId);

        if (vo == null) {
            return "redirect:/board";
        }

        boardService.increasePostHit(postId);
        model.addAttribute("post", vo);
        return "board/view";
    }

    @RequestMapping(value = "/update/{postId}", method = RequestMethod.GET)
    public String viewUpdate(
        Authentication authentication,
        @PathVariable("postId") Long postId, Model model
    ) {
        UserVo authUser = (UserVo)authentication.getPrincipal();
        PostVo vo = boardService.getPost(postId, authUser.getId());

        if (vo == null) {
            return "redirect:/board";
        }

        model.addAttribute("post", vo);
        return "board/modify";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String viewUpdate(Authentication authentication, PostVo postVo) {
        UserVo authUser = (UserVo)authentication.getPrincipal();
        postVo.setUserId(authUser.getId());
        boardService.updatePost(postVo);

        return "redirect:/board/" + postVo.getId();
    }

    @RequestMapping(value = "/delete/{postId}", method = RequestMethod.GET)
    public String deletePost(Authentication authentication, @PathVariable("postId") Long postId) {
        UserVo authUser = (UserVo)authentication.getPrincipal();
        boardService.deletePost(postId, authUser.getId());
        return "redirect:/board";
    }
}
