package com.site.sbb.comment

import com.site.sbb.DataNotFoundException
import com.site.sbb.answer.Answer
import com.site.sbb.question.Question
import com.site.sbb.user.SiteUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional


@Service
class CommentService(val commentRepository: CommentRepository) {
    fun getComment(id:Int):Comment{
        val comment = commentRepository.findById(id)
        if (comment.isPresent) return comment.get()
        throw DataNotFoundException("comment not found")
    }

    fun createComment(question: Question?,answer: Answer?,author:SiteUser,content:String):Comment{
        val comment = Comment()
        if (question!=null)comment.question=question
        if (answer!=null)comment.answer=answer
        comment.author=author
        comment.content=content
        comment.createDate= LocalDateTime.now()
        commentRepository.save(comment)
        return comment
    }

    fun deleteComment(comment:Comment){commentRepository.delete(comment)}

    fun getCommentList(question: Question?,answer: Answer?):List<Comment>{
        if (question!=null)return commentRepository.findByQuestion(question)
        else if (answer!=null)return commentRepository.findByAnswer(answer)
        throw DataNotFoundException("comment not found")
    }

    fun getUserCommentList(user: SiteUser,page:Int):Page<Comment>{
        val sorts = ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("createDate"))
        val page = PageRequest.of(page,10,Sort.by(sorts))
        return commentRepository.findByAuthor(user,page)
    }
}