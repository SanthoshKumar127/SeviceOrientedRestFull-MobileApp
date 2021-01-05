package com.mobile.application.controller;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobile.application.dto.ItemDto;
import com.mobile.application.exception.ItemNotfoundException;
import com.mobile.application.model.Image;
import com.mobile.application.model.Item;
import com.mobile.application.repository.ItemRepository;

@Controller
@ResponseBody

public class AccessoriesController {

	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ModelMapper modelMapper;
	Image image;

	/**
	 * maps to Accessories page
	 * 
	 * @return
	 */
	@RequestMapping("/access")
	public String access() {
		return "/access";
	}

	/**
	 * 
	 * 
	 * maps to item->powerBank page
	 * 
	 * @return
	 */
	@GetMapping("/power")
	public Page<ItemDto> power(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "powerbank";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->HeadSet page
	 * 
	 * @return
	 */
	@GetMapping("/headset")
	public Page<ItemDto> headset(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "headset";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->Charger page
	 * 
	 * @return
	 */
	@GetMapping("/charger")
	public Page<ItemDto> charger(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "charger";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->MobileCover page
	 * 
	 * @return
	 */
	@GetMapping("/cover")
	public Page<ItemDto> cover(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "mobilecover";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "charger";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->MobileScreen page
	 * 
	 * @return
	 */
	@GetMapping("/screen")
	public Page<ItemDto> screen(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "mobilescreen";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->USB page
	 * 
	 * @return
	 */
	@GetMapping("/usb")
	public Page<ItemDto> usb(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "usb";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "charger";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->AppleMobile page
	 * 
	 * @return
	 */
	@GetMapping("/apple")
	public Page<ItemDto> apple(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "applemobile";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->VivoMobile page
	 * 
	 * @return
	 */
	@GetMapping("/vivo")
	public Page<ItemDto> vivo(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "vivomobile";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "charger";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->RealmeMobile page
	 * 
	 * @return
	 */
	@GetMapping("/realme")

	public Page<ItemDto> realme(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "realmemobile";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->OnePlusMobile page
	 * 
	 * @return
	 */
	@GetMapping("/oneplus")
	public Page<ItemDto> oneplus(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "oneplusmobile";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "charger";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->SamsungMobile page
	 * 
	 * @return
	 */
	@GetMapping("/samsung")
	public Page<ItemDto> samsung(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "samsungmobile";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "charger";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->RedmiMobile page
	 * 
	 * @return
	 */
	@GetMapping("/mi")
	public Page<ItemDto> mi(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "redmimobile";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "charger";
		product = itemRepository.findByItemtype(itemType, pageable);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

}
