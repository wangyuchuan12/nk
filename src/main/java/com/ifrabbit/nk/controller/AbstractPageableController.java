package com.ifrabbit.nk.controller;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.CrudService;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author songjiawei
 */
public abstract class AbstractPageableController<SERVICE extends CrudService<DOMAIN, ID>, DOMAIN, DTO extends DOMAIN, ID extends Serializable>
    extends AbstractCrudController<SERVICE, DOMAIN, ID> {


  @GetMapping
 protected Page<DOMAIN> list(@PageableDefault(size = 20) Pageable pageable, DTO condition) {
      System.out.println("启用了list方法....");
    return this.service.findAll(pageable, condition);
  }

}
