package com.nt.service;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

import com.nt.entity.CustomerRegister;
import com.nt.entityDTO.RequestDTO;
import com.nt.entityDTO.ResponseDTO;

public interface ICustomerService {

	public ResponseDTO RegisterCustomer(RequestDTO requestDTO);

	public ResponseDTO FetchById(Long id);

	public String SaveAndUpdate(RequestDTO requestDTO);

	public List<ResponseDTO> FetchAllData();

	public String UserDetailUpdate(Long id, RequestDTO requestDto);

	public Page<ResponseDTO> FetchPageByPage(int page, int size, String sortField, String sortOrder);
}
