package com.mobile.application.controller;

import java.util.Objects;

import org.modelmapper.ModelMapper;
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
import com.mobile.application.repository.UserRepository;
import com.mobile.application.service.ItemServicesAdmin;
import com.mobile.application.service.UserServicesAdmin;

@Controller
@RequestMapping("/Admin")
@ResponseBody
public class AdminController {
	@Autowired
	PaymentRepository paymentRepo;
	@Autowired
	ItemServicesAdmin itemService;
	@Autowired
	UserServicesAdmin userService;
	UserRepository userRepo;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ItemRepository itemRepo;
	@Value("${item_size}")
	private int size;
	Image image = new Image();

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
			size = 25;
		if (Objects.isNull(sort))
			sort = "id";
		Page<User> allUsers = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		allUsers = userService.getAllUser(pageable);
		if (Objects.isNull(allUsers)) {
			throw new ItemNotfoundException("No Users Found.!");
		}
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
			size = 25;
		if (Objects.isNull(sort))
			sort = "paymentid";
		Page<Payment> allOrders = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		allOrders = paymentRepo.findAll(pageable);
		if (Objects.isNull(allOrders)) {
			throw new OrderNotfoundException("No Orders Found.!");
		}
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
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";

		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		products = itemService.getAllItems(pageable);
		if (Objects.isNull(products)) {

			throw new ItemNotfoundException("No products Found.!");
		}
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
	@PostMapping("/delete/{model}")
	public Page<ItemDto> deleteItem(@PathVariable("model") int model,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {

		Page<Item> products = null;
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		itemService.deleteItem(model);
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		products = itemService.getAllItems(pageable);
		if (Objects.isNull(products)) {
			throw new ItemNotfoundException("No products Found.!");
		}
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
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";

		Item item = modelMapper.map(itemDto, Item.class);
		itemService.saveItem(item);
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		products = itemService.getAllItems(pageable);
		if (Objects.isNull(products)) {
			throw new ItemNotfoundException("No products Found.!");
		}
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
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		if (itemService.getItemByModel(model) != null) {

			Item item = modelMapper.map(itemDto, Item.class);
			itemService.saveItem(item);
			Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
			products = itemService.getAllItems(pageable);
		} else {
			throw new ItemNotfoundException("No products Found.!");
		}
		if (Objects.isNull(products)) {
			throw new ItemNotfoundException("product cannot Update.!");
		}
		return products.map(items -> {
			return modelMapper.map(items, ItemDto.class);
		});

	}

}
