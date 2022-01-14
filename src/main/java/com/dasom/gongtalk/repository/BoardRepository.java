package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.board.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends CrudRepository<Board, Integer> {
}
