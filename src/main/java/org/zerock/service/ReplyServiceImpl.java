package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class ReplyServiceImpl implements ReplyService {

	private ReplyMapper replyMapper;

	@Override
	public int register(ReplyVO vo) {

		return replyMapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {

		return replyMapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {

		return replyMapper.update(vo);
	}

	@Override
	public int remove(Long rno) {

		return replyMapper.delete(rno);
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
	
		return replyMapper.getListWithPaging(cri, bno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {

		return new ReplyPageDTO(replyMapper.getCountByBno(bno), replyMapper.getListWithPaging(cri, bno));
	}
}