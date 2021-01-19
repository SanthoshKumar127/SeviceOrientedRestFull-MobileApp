package com.mobile.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.application.model.Item;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemServiceImpl itemRepository;

	public List<Item> getItems() {
		return (List<Item>) itemRepository.findAll();
	}

	private List<Item> findAll() {
		// TODO Auto-generated method stub
		return (List<Item>) itemRepository.findAll();
	}

	@Override
	public Item findById(int id) {
		Item item = itemRepository.findById(id);
		return item;
	}

	@Override
	public Item findByModel(int valueOf) {
		Item item = itemRepository.findByModel(valueOf);
		return item;
	}

	@Override
	public Page<Item> findByItemname(String searchItem, Pageable pageable) {
		Page<Item> Item = itemRepository.findAll(pageable);
		for (var iterate : Item) {
			if (iterate.getItemname().contains(searchItem))
				return Item;
			else {
				return new PageImpl<Item>(new ArrayList<Item>());
			}
		}
		return Item;
	}

	private Page<Item> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Item> findByItemtype(String brand, Pageable pageable) {
		Page<Item> item = itemRepository.findByItemtype(brand, pageable);
		return item;
	}

	public void save(Item item) {
		itemRepository.save(item);
		
	}
}
