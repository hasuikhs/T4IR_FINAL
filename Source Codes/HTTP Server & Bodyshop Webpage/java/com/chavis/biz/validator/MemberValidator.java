package com.chavis.biz.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.chavis.biz.vo.MemberVO;

public class MemberValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public void validate(Object target, Errors errors) {
		MemberVO vo = (MemberVO) target;
		if (vo.getMember_id() == null || vo.getMember_id().trim().isEmpty()) {
			errors.rejectValue("member_id", "required");
		}
		if (vo.getMember_mname() == null || vo.getMember_mname().trim().isEmpty()) {
			errors.rejectValue("member_mname", "required", new Object[] { "member_mname" }, "input name");
		}
		if (vo.getMember_pw() == null || vo.getMember_pw().trim().isEmpty()) {
			errors.rejectValue("member_pw", "required");
		}
		if (vo.getMember_phonenumber() == null || vo.getMember_phonenumber().trim().isEmpty()) {
			errors.rejectValue("member_phonenumber", "required", new Object[] { "member_phonenumber" },
					"input member_phonenumber");
		}
	}
}
