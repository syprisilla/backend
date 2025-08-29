package com.board.Controller;

import com.board.DTO.BoardDTO;
import com.board.DTO.CommentDTO;
import com.board.service.BoardService;
import com.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("/save")
    public BoardDTO save(@RequestBody BoardDTO boardDTO) {  // @ModelAttribute → @RequestBody
        boardService.save(boardDTO);
        return boardDTO; // 저장된 데이터 그대로 리턴 (혹은 id만 리턴해도 됨)
    }

    @GetMapping("/")
    public List<BoardDTO> findAll() {
        return boardService.findAll();
    }

    // 게시글 단건 조회 + 댓글 목록 포함-> 수정 함.
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        // 1. 조회수 증가
        boardService.updateHits(id);

        // 2. 게시글 데이터 조회
        BoardDTO boardDTO = boardService.findById(id);

        // 3. 댓글 목록 조회
        List<CommentDTO> commentDTOList = commentService.findAll(id);

        // 4. JSON 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("board", boardDTO);
        response.put("comments", commentDTOList);

        // 5. ResponseEntity로 반환
        return ResponseEntity.ok(response);
    }

    //----------위에 postman으로 할 수 있도록 바꿈
    @PutMapping("/update/{id}")
    public BoardDTO update(@PathVariable Long id, @RequestBody BoardDTO boardDTO) {
        boardDTO.setId(id); // PathVariable로 넘어온 id를 DTO에 세팅
        return boardService.update(boardDTO); // 수정 후 수정된 데이터 리턴
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        boardService.delete(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("deletedId", id);
        return response; // JSON 응답
    }

    @GetMapping("/paging")
    public Page<BoardDTO> paging(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardService.paging(pageable);
    }

}