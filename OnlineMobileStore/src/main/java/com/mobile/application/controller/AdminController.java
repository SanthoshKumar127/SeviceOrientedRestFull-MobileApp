package com.mobile.application.controller;

import java.util.Objects;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mobile.application.dto.ItemDto;
import com.mobile.application.dto.PaymentDto;
import com.mobile.application.dto.UserDto;
import com.mobile.application.exception.ItemNotfoundException;
import com.mobile.application.exception.OrderNotfoundException;
import com.mobile.application.model.Image;
import com.mobile.application.model.Item;
import com.mobile.application.model.Payment;
import com.mobile.application.model.User;
import com.mobile.application.repository.ItemRepository;
import com.mobile.application.repository.PaymentRepository;
import com.mobile.application.repository.UserServiceImpl;
import com.mobile.application.service.ItemServicesAdmin;
import com.mobile.application.service.UserServicesAdmin;

@Controller
@RequestMapping("/Admin")
@ResponseBody
public class AdminController {
	
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	ItemServicesAdmin itemService;
	@Autowired
	UserServicesAdmin userService;
	UserServiceImpl userRepo;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ItemRepository itemRepo;
	@Value("${item_size}")
	private int size;
	Image image = new Image();
	Logger log = LoggerFactory.getLogger(AccessoriesController.class);

	/**
	 * Displays all Registered Users
	 * 
	 * @param mod
	 * @param users
	 * @return
	 */
	@GetMapping(value = "/users")
	public Page<UserDto> userList(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "id";
		Page<User> allUsers = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		allUsers = userService.getAllUser(pageable);	
		if (Objects.isNull(allUsers)) {
			log.error("error found in userList");
			throw new ItemNotfoundException("No Users Found.!");
		}
		log.info("AdminController userList() response{}", allUsers);

		return allUsers.map(user -> {
			return modelMapper.map(user, UserDto.class);
		});
	}

	/**
	 * Displays All Successful Orders made by Users
	 * 
	 * @param mod
	 * @param users
	 * @return
	 */
	@GetMapping(value = "/ordersadmin")
	public Page<PaymentDto> orderList(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "paymentid";
		Page<Payment> allOrders = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		allOrders = paymentRepository.findAll(pageable);
		if (Objects.isNull(allOrders)) {
			log.error("error found in orderList");
			throw new OrderNotfoundException("No Orders Found.!");
		}
		log.info("AdminController orderList() response{}", allOrders);

		return allOrders.map(orders -> {
			return modelMapper.map(orders, PaymentDto.class);
		});
	}

	/**
	 * Displays all Products List
	 * 
	 * @param mod
	 * @param users
	 * @return
	 */
	@GetMapping(value = "/products")
	public Page<ItemDto> productList(@RequestParam(value = "page", required = false) Integer pageNumber,
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
		log.info("AdminController productList() response{}", products);

		return products.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});

	}

	/**
	 * Request to Add New Product
	 * 
	 * @param users
	 * @return
	 */
	@RequestMapping("/addproduct")
	public String addP(@SessionAttribute("Admin") User users) {
		return "addproduct";
	}

	/**
	 * A product is deleted
	 * 
	 * @param model
	 * @param users
	 * @return
	 */
	@DeleteMapping("/delete/{model}")
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
		log.info("AdminController deleteItem() response{}", products);

		return products.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});

	}

	/**
	 * New Product is Saved
	 * 
	 * @param item
	 * @param users
	 * @return
	 */
	@PostMapping(value = "/saveItem")
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
		log.info("AdminController addNewProduct() response{}", products);

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
	@PutMapping(value = "/update/{model}")
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
		log.info("AdminController updateItem() response{}", products);

		return products.map(items -> {
			return modelMapper.map(items, ItemDto.class);
		});

	}

}
