package com.board.service;

import com.board.DTO.CommentDTO;
import com.board.Entity.BoardEntity;
import com.board.Entity.CommentEntity;
import com.board.repository.BoardRepository;
import com.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        //부모 엔티티(BoardEntity)조회
        Optional<BoardEntity> optionalBoardEntity=boardRepository.findById(commentDTO.getBoardId());
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity=optionalBoardEntity.get();
            CommentEntity commentEntity= CommentEntity.toSaveEntity(commentDTO,boardEntity);
            return commentRepository.save(commentEntity).getId();
        }else{
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity= boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList=commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        //Entity리스트->DTOList
        List<CommentDTO> commentDTOList=new ArrayList<>();
        for(CommentEntity commentEntity:commentEntityList){
            CommentDTO commentDTO= CommentDTO.toCommentDTO(commentEntity,boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
