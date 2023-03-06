package com.msum.finance.api.repository

import com.msum.finance.api.data.entity.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LocationRepository : JpaRepository<LocationEntity, UUID>
