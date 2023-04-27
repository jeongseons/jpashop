package com.jpabook.jpashop.contoller

import javax.validation.constraints.NotEmpty

data class MemberForm(
    @field:NotEmpty(message = "회원 이름은 필수입니다")
    val name:String? = null,
    val city:String? = null,
    val street:String? = null,
    val zipcode:String? = null
) {
}