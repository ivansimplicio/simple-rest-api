package com.dev.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.dev.domain.Turma;
import com.dev.dto.turma.TurmaSaveDTO;
import com.dev.repository.TurmaRepository;
import com.dev.resource.exception.FieldMessage;

public class TurmaInsertValidator implements ConstraintValidator<TurmaInsert, TurmaSaveDTO>{

	@Autowired
	private TurmaRepository repo;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public boolean isValid(TurmaSaveDTO obj, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = null;
		if(map.get("id") != null) {
			uriId = Integer.parseInt(map.get("id"));
		}
		
		List<FieldMessage> errors = new ArrayList<>();
		
		Turma objAux = repo.findByDisciplina(obj.getDisciplina());
		
		if(uriId == null) {
			if(objAux != null) {
				errors.add(new FieldMessage("disciplina", "Disciplina já cadastrada."));
			}
		}else{
			if(objAux != null && !objAux.getId().equals(uriId)) {
				errors.add(new FieldMessage("disciplina", "Disciplina já cadastrada."));
			}
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