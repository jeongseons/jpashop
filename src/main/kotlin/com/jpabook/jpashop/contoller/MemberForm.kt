package com.jpabook.jpashop.contoller

import javax.validation.constraints.NotEmpty

data class MemberForm(
    @field:NotEmpty(message = "회원 이름은 필수입니다")
    val name:String = "",
    val city:String = "",
    val street:String = "",
    val zipcode:String = ""
) {
}