package com.dev.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev.domain.Aluno;
import com.dev.dto.aluno.AlunoSaveDTO;
import com.dev.repository.AlunoRepository;
import com.dev.resource.exception.FieldMessage;

public class AlunoInsertValidator implements ConstraintValidator<AlunoInsert, AlunoSaveDTO>{

	@Autowired
	private AlunoRepository repo;
	
	@Override
	public boolean isValid(AlunoSaveDTO obj, ConstraintValidatorContext context) {
		Aluno objAux = repo.findByMatricula(obj.getMatricula());
		
		List<FieldMessage> errors = new ArrayList<>();
		
		if(objAux != null) {
			errors.add(new FieldMessage("matricula", "Matrícula já cadastrada."));
		}
		
		for(FieldMessage e : errors) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
					.addPropertyNode(e.getFieldName())
					.addConstraintViolation();
			
		}
		return errors.isEmpty();
	}
}