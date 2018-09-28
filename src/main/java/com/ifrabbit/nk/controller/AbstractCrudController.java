package com.ifrabbit.nk.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import ir.boot.autoconfigure.web.exception.NotFoundException;
import ir.boot.autoconfigure.web.exception.ValidationException;
import ir.nymph.util.ReflectUtil;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;
import org.springframework.data.support.CrudService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author songjiawei
 */
public abstract class AbstractCrudController<SERVICE extends CrudService<DOMAIN, ID>, DOMAIN, ID extends Serializable> {

	protected SERVICE service;

	@Autowired public void setService(SERVICE service) {
		this.service = service;
	}

	@GetMapping("{id}") protected DOMAIN read(@PathVariable("id") ID id) {
		if (!this.supportRead()) {
			throw new NotFoundException("not found");
		}
		return this.service.get(id);
	}

	@PostMapping @ResponseStatus(CREATED) protected ID create(
			@RequestBody DOMAIN domain) {
		if (!this.supportCreate()) {
			throw new NotFoundException("not found");
		}
		checkCreate(domain);
		this.service.insert(this.preProcessCreate(domain));
		return getId(domain);
	}

	protected String getIdName() {
		return "id";
	}

	protected ID getId(DOMAIN domain) {
		if (domain instanceof Persistable) {
			return (ID) ((Persistable) domain).getId();
		}
		return (ID) ReflectUtil.getFieldValue(domain, getIdName());
	}

	@PutMapping("{id}") @ResponseStatus(NO_CONTENT) protected void modify(
			@PathVariable("id") ID id, @RequestBody DOMAIN domain) {
		if (!this.supportModify()) {
			throw new NotFoundException("not found");
		}
		checkModify(domain);
		domain = this.preProcessModify(id, domain);
		ID invokeId = getId(domain);
		if (null == invokeId) {
			throw new ValidationException("未指定ID");
		}
		if (!invokeId.equals(id)) {
			throw new ValidationException("ID不符");
		}
		this.service.updateIgnore(domain);
	}

	@DeleteMapping("{id}") @ResponseStatus(NO_CONTENT) protected void delete(
			@PathVariable("id") ID id) {
		if (!this.supportDelete()) {
			throw new NotFoundException("not found");
		}
		checkDelete(id);
		this.service.delete(id);
	}

	protected DOMAIN preProcessCreate(DOMAIN domain) {
		return domain;
	}

	protected DOMAIN preProcessModify(ID id, DOMAIN domain) {
		ReflectUtil.setFieldValue(domain, getIdName(), id);
		return domain;
	}

	protected void checkCreate(DOMAIN domain) {
	}

	protected void checkModify(DOMAIN domain) {
	}

	protected void checkDelete(ID id) {
	}

	protected boolean supportRead() {
		return true;
	}

	protected boolean supportCreate() {
		return true;
	}

	protected boolean supportModify() {
		return true;
	}

	protected boolean supportDelete() {
		return true;
	}
}
