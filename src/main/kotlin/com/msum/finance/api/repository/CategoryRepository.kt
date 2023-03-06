package com.msum.finance.api.repository

import com.msum.finance.api.data.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CategoryRepository : JpaRepository<CategoryEntity, UUID>
