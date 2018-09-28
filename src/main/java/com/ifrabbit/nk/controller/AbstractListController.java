package com.ifrabbit.nk.controller;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.CrudService;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author songjiawei
 */
public abstract class AbstractListController<SERVICE extends CrudService<DOMAIN, ID>, DOMAIN extends Persistable<ID>, DTO extends DOMAIN, ID extends Serializable>
    extends AbstractCrudController<SERVICE, DOMAIN, ID> {


  @GetMapping
  List<DOMAIN> list(Sort sort, DTO condition) {
    return this.processListResult(this.service.findAll(sort, condition));
  }

  protected List<DOMAIN> processListResult(List<DOMAIN> result) {
    return result;
  }
}
