package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.Comment;

public interface UserCommentRepository extends JpaRepository<Comment, Integer > {

}
