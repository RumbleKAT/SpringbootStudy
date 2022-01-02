package com.rumblekat.board.repository;

import com.rumblekat.board.entity.Board;
import com.rumblekat.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {
    
    @Autowired
    private ReplyRepository replyRepository;
    
    @Test
    public void insertReply(){
        IntStream.rangeClosed(1,100).forEach(i->{
            long bno = (long)(Math.random() * 100) + 1;
            Board board = Board.builder().gno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply....."+i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    public void readReply1(){
        Optional<Reply> result = replyRepository.findById(1L);
        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());

        //left outer Join이 두번 들어간다.
    }
}