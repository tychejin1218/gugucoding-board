package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class BoardServiceImpl implements BoardService {

	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;

	@Override
	public void register(BoardVO boardVO) {

		log.info("register : " + boardVO);

		boardMapper.insertSelectKey(boardVO);
	}

	@Override
	public List<BoardVO> getList() {
		log.info("boardServiceImpl ============");
		return boardMapper.getList();
	}

	@Override
	public BoardVO get(Long bno) {
		
		return boardMapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO boardVO) {
		
		log.info("modify : " + boardVO);

		return boardMapper.update(boardVO) == 1;
	}

	@Override
	public boolean remove(Long bno) {

		log.info("remove : " + bno);

		return boardMapper.delete(bno) == 1;
	}
}