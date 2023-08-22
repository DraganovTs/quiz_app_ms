package com.homecode.questionservice.repository;

import com.homecode.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

   List<Question> findAllByCategory(String category);
}
