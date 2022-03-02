package com.devsuperior.clients.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.clients.dto.ClientDTO;
import com.devsuperior.clients.entities.Client;
import com.devsuperior.clients.repositories.ClientRepository;
import com.devsuperior.clients.services.exceptions.ResourceNotFoundException;


@Service
public class ClientService {
     
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true )
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		Page<Client> list = repository.findAll(pageRequest);
		
		return list.map(x -> new ClientDTO(x));
	}
	
	@Transactional(readOnly = true )
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client category = new Client();
		copyDtoToEntity(dto, category);
	    return new ClientDTO(repository.save(category));
	}
	
	private void copyDtoToEntity(ClientDTO dto, Client cli) {
		cli.setName(dto.getName());
		cli.setCpf(dto.getCpf());
		cli.setIncome(dto.getIncome());
		cli.setBirthDate(dto.getBirthDate());
		cli.setChildren(dto.getChildren());
	}
}
