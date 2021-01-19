package com.mobile.application.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobile.application.dto.ItemDto;
import com.mobile.application.exception.ItemNotfoundException;
import com.mobile.application.model.Item;
import com.mobile.application.repository.ItemRepository;
import com.mobile.application.repository.PaymentRepository;
import com.mobile.application.repository.UserServiceImpl;
import com.mobile.application.service.ItemServicesAdmin;
import com.mobile.application.service.UserServicesAdmin;

@Controller
@ResponseBody
@RequestMapping("/Admin")
public class StocksController {
	@Autowired
	PaymentRepository paymentRepo;
	@Autowired
	ItemServicesAdmin itemService;
	@Autowired
	UserServicesAdmin userService;
	UserServiceImpl userRepo;
	@Autowired
	ItemRepository itemRepo;
	@Value("${item_size}")
	private int size;
	@Autowired
	HttpServletRequest request;
	@Autowired
	ModelMapper modelMapper;

	Logger log = LoggerFactory.getLogger(StocksController.class);
	/**
	 * Stocks home
	 * 
	 * @param model
	 * @return page of item dto
	 */

	@GetMapping(value = "/stockProducts")
	public Page<ItemDto> stockProductList(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		Page<Item> products = null;
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "model";

		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		products = itemService.getAllItems(pageable);
		if (Objects.isNull(products)) {
			log.error("error found in productList");
			throw new ItemNotfoundException("No products Found.!");
		}
		log.info("StocksController productList() response{}", products);

		return products.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});


	}

	/**
	 * maps To add new stock
	 * 
	 * @return
	 */
	@RequestMapping("/addstocks")
	public String add() {
		return "addstocks";
	}

	/**
	 * Delete Specific item in Stocks
	 * 
	 * @param model
	 * @param mod
	 * @return
	 */
	@PostMapping("/deleteStockItem/{model}")
	public Page<ItemDto> deleteItem(@PathVariable("model") int model,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {

		Page<Item> products = null;
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "model";
		if (Objects.isNull(model)) {
			log.warn("Enter correct model number");
			throw new ItemNotfoundException("No products Found.!");
		}	
		itemService.deleteItem(model);
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		products = itemService.getAllItems(pageable);
		if (Objects.isNull(products)) {
			log.error("error found in deleteItem");
			throw new ItemNotfoundException("No products Found.!");
		}
		log.info("StocksController deleteItem() response{}", products);

		return products.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});

	}

	/**
	 * Saves newly added Stock
	 * 
	 * @param item
	 * @param mod
	 * @return
	 */
	@PostMapping(value = "/saveStockItem")
	public Page<ItemDto> addNewProduct(@RequestBody ItemDto itemDto,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {

		Page<Item> products = null;
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "model";

		Item item = modelMapper.map(itemDto, Item.class);
		if (Objects.isNull(item)) {
			log.warn("Enter correct Item Details");
			throw new ItemNotfoundException("No products Saved.!");
		}	
		itemService.saveItem(item);
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		products = itemService.getAllItems(pageable);
		if (Objects.isNull(products)) {
			log.error("error found in addNewProduct");
			throw new ItemNotfoundException("No products Found.!");
		}
		log.info("StocksController addNewProduct() response{}", products);

		return products.map(items -> {
			return modelMapper.map(items, ItemDto.class);
		});

	}

	/**
	 * A Request to Update Specific product
	 * 
	 * @param model
	 * @param users
	 * @return
	 */
	@PutMapping(value = "/updateStockItem/{model}")
	public Page<ItemDto> updateItem(@PathVariable int model, @RequestBody ItemDto itemDto,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {


		Page<Item> products = null;
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "model";
		if (itemService.getItemByModel(model) != null) {

			Item item = modelMapper.map(itemDto, Item.class);
			itemService.saveItem(item);
			Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
			products = itemService.getAllItems(pageable);
		} else {
			log.warn("Enter correct Model Number");
			throw new ItemNotfoundException("No products Found.!");
		}
		if (Objects.isNull(products)) {
			log.error("error found in updateItem");
			throw new ItemNotfoundException("product cannot Update.!");
		}
		log.info("StocksController updateItem() response{}", products);

		return products.map(items -> {
			return modelMapper.map(items, ItemDto.class);
		});
	}
}
