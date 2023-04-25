package com.jpabook.jpashop.domain.item

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("A")
data class Album(
    var artist:String = "",
    var etc:String = ""
):Item(){

}
