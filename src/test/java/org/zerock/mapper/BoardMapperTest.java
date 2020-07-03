package org.zerock.mapper;

import java.util.List;

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
public class BoardMapperTest {

	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;

	@Test
	public void testGetList() {

		boardMapper.getList(new Criteria(2, 10))
		           .forEach(board -> log.info(board));
	}

	@Test
	public void testInsert() {

		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("제목_001");
		boardVO.setContent("내용_001");
		boardVO.setWriter("작성자_001");

		log.info(boardVO);

		boardMapper.insert(boardVO);
	}

	@Test
	public void testInsertSelectKey() {

		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("제목_002");
		boardVO.setContent("내용_002");
		boardVO.setWriter("작성자_002");

		log.info(boardVO);

		boardMapper.insertSelectKey(boardVO);
	}

	@Test
	public void testInsertSelectKey01() {

		for (int a = 0; a < 100; a++) {

			BoardVO boardVO = new BoardVO();
			boardVO.setTitle("제목_" + a);
			boardVO.setContent("내용_" + a);
			boardVO.setWriter("작성자_검색_" + a);

			boardMapper.insertSelectKey(boardVO);
		}
	}

	@Test
	public void testRead() {

		BoardVO boardVO = boardMapper.read(8L);

		log.info(boardVO);
	}

	@Test
	public void testDelete() {

		int deleteCount = boardMapper.delete(2L);

		log.info("DELETE COUNT : " + deleteCount);
	}

	@Test
	public void testUpdate() {

		BoardVO boardVO = new BoardVO();
		boardVO.setBno(8L);
		boardVO.setTitle("제목_수정_002");
		boardVO.setContent("내용_수정_002");
		boardVO.setWriter("작성자_수정_002");

		log.info(boardVO);

		int updateCount = boardMapper.update(boardVO);

		log.info("UPDATE COUNT : " + updateCount);
	}

	@Test
	public void testPaging() {

		Criteria cri = new Criteria();
		cri.setPageNum(3);
		cri.setAmount(10);

		List<BoardVO> list = boardMapper.getListWithPaging(cri);

		list.forEach(board -> log.info(board));
	}
	
	@Test
	public void testSearch() {

		Criteria cri = new Criteria();
		cri.setKeyword("검색");
		cri.setType("TC");

		List<BoardVO> list = boardMapper.getListWithPaging(cri);

		list.forEach(board -> log.info(board));
	}
}