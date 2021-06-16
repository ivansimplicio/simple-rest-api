package com.dev.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.dev.domain.Usuario;
import com.dev.dto.usuario.UsuarioInputDTO;
import com.dev.repository.UsuarioRepository;
import com.dev.resource.exception.FieldMessage;
import com.dev.service.validation.utils.BR;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, UsuarioInputDTO>{

	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public boolean isValid(UsuarioInputDTO obj, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = null;
		if(map.get("id") != null) {
			uriId = Integer.parseInt(map.get("id"));
		}
		List<FieldMessage> errors = new ArrayList<>();
		
		if(!BR.isValidCPF(obj.getCpf())) {
			errors.add(new FieldMessage("cpf", "CPF inválido."));
		}
		
		// teste para quando está inserindo um usuário (id=null)
		if(uriId == null) {
			if(repo.findByCpf(obj.getCpf()) != null) {
				errors.add(new FieldMessage("cpf", "CPF já cadastrado."));
			}
			if(repo.findByEmail(obj.getEmail()) != null) {
				errors.add(new FieldMessage("email", "Email já cadastrado."));
			}
		}
		// teste para quando está atualizando um usuário (id=valor)
		else {
			Usuario user = repo.findByCpf(obj.getCpf());
			if(user != null && !user.getId().equals(uriId)) {
				errors.add(new FieldMessage("cpf", "CPF já cadastrado."));
			}
			user = repo.findByEmail(obj.getEmail());
			if(user != null && !user.getId().equals(uriId)) {
				errors.add(new FieldMessage("email", "Email já cadastrado."));
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