package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@AllArgsConstructor
@Log4j
public class BoardController {

	private BoardService boardService;

	@GetMapping("/list")
	public void list(Model model) {

		log.info("list");

		model.addAttribute("list", boardService.getList());
	}

	@GetMapping("/register")
	public void register() {

	}

	@PostMapping("/register")
	public String register(BoardVO boardVO, RedirectAttributes rttr) {

		log.info("register : " + boardVO);

		boardService.register(boardVO);

		rttr.addFlashAttribute("result", boardVO.getBno());

		return "redirect:/board/list";
	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, Model model) {

		log.info("get or modify : " + bno);
		model.addAttribute("board", boardService.get(bno));
	}

	@PostMapping("/modify")
	public String modify(BoardVO boardVO, RedirectAttributes rttr) {

		log.info("modify : " + boardVO);

		if (boardService.modify(boardVO)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {

		log.info("remove : " + bno);

		if (boardService.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
}
