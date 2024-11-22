package com.site.sbb.question

import com.site.sbb.DataNotFoundException
import com.site.sbb.answer.Answer
import com.site.sbb.user.SiteUser
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional


@Service
class QuestionService(
    val questionRepository: QuestionRepository
) {
    fun getList(page:Int,kw: String):Page<Question>{
        val sorts:List<Sort.Order> = ArrayList<Sort.Order>().plus(Sort.Order.desc("createDate"))
        val pageable: Pageable = PageRequest.of(page,10,Sort.by(sorts))
        val spec = search(kw)
        return this.questionRepository.findAll(spec,pageable)
    }

    fun getQuestion(id:Int):Question{
        val qr =  this.questionRepository.findById(id)
        if (qr.isPresent) return qr.get()
        throw DataNotFoundException("question not found")
    }
    fun create(subject:String,content:String,author:SiteUser):Question{
        val q = Question()
        q.subject=subject
        q.content=content
        q.author=author
        questionRepository.save(q)
        return q
    }
    fun modify(question: Question,subject: String,content: String){
        question.subject=subject
        question.content=content
        question.modifyDate= LocalDateTime.now()
        questionRepository.save(question)
    }

    fun delete(question: Question){
        questionRepository.delete(question)
    }
    fun vote(question: Question,siteUser: SiteUser){
        question.voter.add(siteUser)
        questionRepository.save(question)
    }
    fun search(kw: String): Specification<Question> {
         return object : Specification<Question>{
            override fun toPredicate(
                q: Root<Question>,
                query: CriteriaQuery<*>?,
                cb: CriteriaBuilder
            ): Predicate {
                var predicate = cb.conjunction()
                if (kw.isNotEmpty()) predicate = this.search(q,query,cb)
                return predicate
            }
             fun search(
                 q: Root<Question>,
                 query: CriteriaQuery<*>?,
                 cb: CriteriaBuilder):Predicate{
                 val searchKw = "%$kw%"
                 val a : Join<Question, Answer> = q.join("answerList",JoinType.LEFT)
                 val u1 : Join<Question,SiteUser> = q.join("author",JoinType.LEFT)
                 val u2: Join<Answer,SiteUser> = a.join("author",JoinType.LEFT)
                 return cb.or(
                     cb.like(q.get("subject"),searchKw),
                     cb.like(q.get("content"),searchKw),
                     cb.like(a.get("content"),searchKw),
                     cb.like(u1.get("username"),searchKw),
                     cb.like(u2.get("username"),searchKw)
                 )
             }
        }
    }
}