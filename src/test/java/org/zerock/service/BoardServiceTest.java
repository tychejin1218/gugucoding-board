package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTest {

	@Setter(onMethod_ = @Autowired)
	private BoardService boardService;

	@Test
	public void testExist() {
		log.info(boardService);
		assertNotNull(boardService);
	}

	@Test
	public void testRegister() {

		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("제목_010");
		boardVO.setContent("내용_010");
		boardVO.setWriter("작성자_010");

		boardService.register(boardVO);

		log.info("저장된 게시물의 번호  : " + boardVO.getBno());
	}

	@Test
	public void testGetList() {

		boardService.getList(new Criteria(2, 10))
		            .forEach(board -> log.info(board));
	}

	@Test
	public void testRead() {

		log.info(boardService.get(2L));
	}

	@Test
	public void testUpdate() {

		BoardVO boardVO = boardService.get(9L);

		if (boardVO == null) {
			return;
		}

		boardVO.setTitle("제목_수정_010");
		boardVO.setContent("내용_수정_010");
		boardVO.setWriter("작성자_수정_010");

		log.info("게시물 수정 여부 : " + boardService.modify(boardVO));
	}

	@Test
	public void testDelete() {

		log.info("게시물 삭제 여부 : " + boardService.remove(9L));
	}
}