package org.zerock.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;

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
		boardMapper.getList()
		           .forEach(board -> log.info(board));
	}
	
	@Test
	public void testInsert(){
	
		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("제목_001");
		boardVO.setContent("내용_001");
		boardVO.setWriter("작성자_001");

		log.info(boardVO);
		
		boardMapper.insert(boardVO);
	}
	
	@Test
	public void testInsertSelectKey(){
	
		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("제목_002");
		boardVO.setContent("내용_002");
		boardVO.setWriter("작성자_002");

		log.info(boardVO);
		
		boardMapper.insertSelectKey(boardVO);
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
}
