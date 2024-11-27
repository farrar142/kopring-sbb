package com.site.sbb

import com.site.sbb.category.CategoryService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class SbbApplicationTests{

	@Autowired
	lateinit var categoryService: CategoryService
	@Test
	fun testJpa(){
		categoryService.getOrCreateCategories("1번게시판","2번게시판","3번게시판","4번게시판")
	}

}
