package com.mobile.application.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mobile.application.model.Item;
import com.mobile.application.repository.ItemRepository;

@Service
@Transactional
public class ItemServicesAdmin {

	@Autowired
	ItemRepository itemRepository;

	public Page<Item> getAllItems(Pageable pageable) {
		return (Page<Item>) itemRepository.findAll(pageable);
	}

	public Item getItemByModel(int model) {
		return itemRepository.findById(model).get();
	}

	public void saveItem(Item item) {
		itemRepository.save(item);
	}

	public void deleteItem(int model) {
		itemRepository.deleteById(model);
	}

}
