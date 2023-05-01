package com.jpabook.jpashop.contoller

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class MemberController(private val memberService: MemberService) {

    @GetMapping(value = ["/members/new"])
    fun createForm(model: Model):String {
        model.addAttribute("memberForm", MemberForm())
        return "members/createMemberForm"
    }

    @PostMapping(value = ["/members/new"])
    fun create(@Valid form: MemberForm, result:BindingResult):String {

        if(result.hasErrors()){
            return "members/createMemberForm"
        }

        val address = Address(form.city, form.street, form.zipcode)

        val member = Member(null, form.name, address)

        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model):String {
        model.addAttribute("members", memberService.findMembers())
        return "members/memberList"
    }

}