package com.homecode.questionservice.repository;

import com.homecode.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

   List<Question> findAllByCategory(String category);

   @Query(value = "SELECT q.id FROM questions q where q.category = :category ORDER BY RAND() Limit :numQuestions", nativeQuery = true)
   List<Long> findRandomQuestionsByCategory(@Param("category") String categoryName,@Param("numQuestions") Integer numQuestions);
}
