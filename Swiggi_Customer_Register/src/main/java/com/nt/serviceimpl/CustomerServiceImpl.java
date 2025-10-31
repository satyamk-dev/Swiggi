package com.nt.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.entity.CustomerRegister;
import com.nt.entityDTO.RequestDTO;
import com.nt.entityDTO.ResponseDTO;
import com.nt.exception.CustomerIdNotFoundException;
import com.nt.repository.CustomerRepository;
import com.nt.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public ResponseDTO RegisterCustomer(RequestDTO requestDTO) {
		logger.info("Registration Service Layer calling and starting ");
		CustomerRegister customer = new CustomerRegister();// Data going to Data Base
		BeanUtils.copyProperties(requestDTO, customer);
		logger.debug("Real Entity getting data: {}", customer);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashPassword = encoder.encode(customer.getPassword());
		customer.setPassword(hashPassword);
		CustomerRegister saveData = customerRepository.save(customer);
		logger.debug("RegisterCustomer Registration successfully saveData: {}", saveData);
		ResponseDTO responseDto = new ResponseDTO();// Data Return To Api
		BeanUtils.copyProperties(saveData, responseDto);
		logger.info("ResponseDto getting Data for responding data are : {}" , responseDto);
		logger.info("Registration Service Layer closing ");
		return responseDto;
	}

	@Cacheable(value = "customerID" , key="#id")
	@Override
	public ResponseDTO FetchById(Long id) {
		logger.info("FetchById Service Layer calling and starting");

		CustomerRegister findedData = customerRepository.findById(id)
				.orElseThrow(() -> new CustomerIdNotFoundException("Enter Customer Id"));
		logger.debug("Fetching Data from DB : {} " , findedData);
		ResponseDTO responseDto = new ResponseDTO();
		BeanUtils.copyProperties(findedData, responseDto);
		logger.debug("Mapped entity to ResponseDTO: {}", responseDto);
		logger.info("FetchById getting data successfully",id);
		return responseDto;
	}

	@Override
	public String SaveAndUpdate(RequestDTO requestDTO) {
		logger.info("SaveAndUpdate Service Layer calling and starting ");
		if (requestDTO.getId() == null) {
			CustomerRegister customerRegister = new CustomerRegister();
			BeanUtils.copyProperties(requestDTO, customerRegister);
			CustomerRegister save = customerRepository.save(customerRegister);
			logger.info("SaveAndUpdate Service Layer save new User and Close ");
			return "User are Register SuccessFully " + save.getId();
		} else {
			logger.info("SaveAndUpdate Service Layer calling for update the Data ");
			Optional<CustomerRegister> byId = customerRepository.findById(requestDTO.getId());
			if (byId.isPresent()) {
				CustomerRegister customerRegister = byId.get();
				BeanUtils.copyProperties(requestDTO, customerRegister);
				CustomerRegister updatedData = customerRepository.save(customerRegister);
				logger.info("SaveAndUpdate Service Layer updating the Data successfully and close ");
				return updatedData.getId() + " are Updataed Name is : " + updatedData.getFirstName();
			} else {
				logger.warn("SaveAndUpdate Service Layer Don't Enter wrong Id ");
				logger.error("SaveAndUpdate Service Layer Id Not Founded");
				return "User with ID " + requestDTO.getId() + " not found!";
			}
		}
	}

	@Cacheable(value = "AllCustomers")
	@Override
	public List<ResponseDTO> FetchAllData() {
		logger.info("FetchAllData Service Layer calling and and starting ....");
		List<CustomerRegister> all = customerRepository.findAll();
		logger.debug("fetched {} records from Repository :", all.size());
		List<ResponseDTO> customerList = new ArrayList<>();
		for (CustomerRegister customer : all) {
			ResponseDTO responseDto = new ResponseDTO();
			BeanUtils.copyProperties(customer, responseDto);
			customerList.add(responseDto);
		}
		logger.info("FetchAllData Service Layer sended all list and close Total records : {} ", customerList.size());
		return customerList;
	}

	@Override
	public String UserDetailUpdate(Long id, RequestDTO requestDto) {
		logger.info("UserDetailUpdate Service Layer called for user Id : {} ", id);
		CustomerRegister returnData = customerRepository.findById(id).orElseThrow(() -> {
			logger.warn("User Id : {} not Found!  ", id);
			return new CustomerIdNotFoundException("Enter Id Please");
		});

		CustomerRegister customerRegister = returnData;
		logger.debug("old user Detail : firstName={}, lastName={}, email={}. mobile={}",
				customerRegister.getFirstName(), customerRegister.getLastName(), customerRegister.getEmail(),
				customerRegister.getMobile());

		customerRegister.setFirstName(requestDto.getFirstName());
		customerRegister.setLastName(requestDto.getLastName());
		customerRegister.setEmail(requestDto.getEmail());
		customerRegister.setMobile(requestDto.getMobile());

		if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
			BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
			String hashPassword = encode.encode(requestDto.getPassword());
			logger.debug("Password Updated for User ID {} ", id);
			customerRegister.setPassword(hashPassword);
		} else {
			logger.debug("PassWord  not changed for user Id: {}", id);
		}
		customerRepository.save(customerRegister);
		logger.info("User Detail updated SeccessFull user id : {}", id);
		logger.info("User Updated Details : firstName={} , lastName={}, email={} , moblie ={}",
				customerRegister.getFirstName(), customerRegister.getLastName(), customerRegister.getEmail(),
				customerRegister.getMobile());
		return "User Updated Successfully Your First Name :" + customerRegister.getFirstName();
	}

	@Override
	public Page<ResponseDTO> FetchPageByPage(int page, int size, String columnName, String sortOrder) {

		logger.info("Fetching Data page By Page page = {}, size={} , columnName = {}, sortOrder = {}", page, size,
				columnName, sortOrder);

		Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(columnName).ascending()
				: Sort.by(columnName).descending();
	
		logger.debug("Sort object created {}",sort);
	
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		logger.debug("Page Request create {}", pageRequest);
		Page<CustomerRegister> userPages = customerRepository.findAll(pageRequest);
		
		logger.info("Total element fetch: {} , Total Pages : {}, ", userPages.getTotalElements(),userPages.getTotalPages());

		 Page<ResponseDTO> responsePage = userPages.map(users -> {
			ResponseDTO responseDto = new ResponseDTO();
			
			BeanUtils.copyProperties(users, responseDto);
			logger.trace("Mapped Customer ID, {} to Response ",responseDto.getId());
			return responseDto;
		});
		
		 logger.info("Successfull fatching page {} with records {} ", page , userPages.getNumberOfElements() );
		logger.info("Fetching Page By Page closing");
		
		return responsePage;

	}

}
