package bll;

/**
 * Clasa in care se creeaza obiecte de tipul client si se apeleaza pe aceste obiecte
 * metode din cadrul clasei AbstractDAO
 */

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

public class ClientBLL {

	private List<Validator<Client>> validators;
	private ClientDAO clientDAO;

	public ClientBLL() {
		clientDAO = new ClientDAO();
	}

	public Client findClientById(int id) {
		Client st = clientDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The client with id =" + id + " was not found!");
		}
		return st;
	}
	public boolean insertInto(String[] fields){
        return clientDAO.insert(fields);
	}
	public boolean UpdateClient(int id, String[] fields){
		return clientDAO.update(id, fields);
	}
	public void deleteClient( int id){
		Client c = clientDAO.delete(id);
	}
	public List<Client> selectClient(){
		List<Client> list= clientDAO.findAll();
		return list;
	}
	public List<Integer> findIds(){
		List <Integer> list = clientDAO.findIds();
		return list;
	}
	public List<String> findNames(){
		List < String> list = clientDAO.findNames();
		return list;
	}
	public Client findByName(String name){
		Client c = clientDAO.findByName(name);
		return c;
	}
}
