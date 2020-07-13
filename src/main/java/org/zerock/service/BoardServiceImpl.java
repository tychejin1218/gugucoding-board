package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
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

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper boardAttachMapper;

	@Transactional
	@Override
	public void register(BoardVO boardVO) {

		log.info("register......" + boardVO);

		boardMapper.insertSelectKey(boardVO);

		if (boardVO.getAttachList() == null || boardVO.getAttachList()
		                                              .size() <= 0) {
			return;
		}

		boardVO.getAttachList()
		       .forEach(attach -> {
			       attach.setBno(boardVO.getBno());
			       boardAttachMapper.insert(attach);
		       });
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		return boardMapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		return boardMapper.getTotalCount(cri);
	}

	@Override
	public BoardVO get(Long bno) {
		return boardMapper.read(bno);
	}

	@Transactional
	@Override
	public boolean modify(BoardVO boardVO) {

		log.info("modify......" + boardVO);

		boardAttachMapper.deleteAll(boardVO.getBno());

		boolean modifyResult = boardMapper.update(boardVO) == 1;

		if (modifyResult && boardVO.getAttachList()
		                           .size() > 0) {

			boardVO.getAttachList()
			       .forEach(attach -> {
				       attach.setBno(boardVO.getBno());
				       boardAttachMapper.insert(attach);
			       });
		}

		return modifyResult;
	}

	@Transactional
	@Override
	public boolean remove(Long bno) {

		log.info("remove...." + bno);

		boardAttachMapper.deleteAll(bno);

		return boardMapper.delete(bno) == 1;
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {

		log.info("get Attach list by bno" + bno);

		return boardAttachMapper.findByBno(bno);
	}
}