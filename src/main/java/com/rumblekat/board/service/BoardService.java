package com.rumblekat.board.service;

import com.rumblekat.board.dto.BoardDTO;
import com.rumblekat.board.dto.PageRequestDTO;
import com.rumblekat.board.dto.PageResultDTO;
import com.rumblekat.board.entity.Board;
import com.rumblekat.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno); //댓글까지 삭제기능

    void modify(BoardDTO boardDTO);

    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder().email(dto.getWriterEmail())
                .build();

        Board board = Board.builder()
                .gno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }

    default BoardDTO entityToDTO(Board board,Member member,Long replyCount){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getGno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
        return boardDTO;
    }
}
