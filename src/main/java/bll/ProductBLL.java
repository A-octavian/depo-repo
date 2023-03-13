package bll;
/**
 * Clasa in care se creeaza obiecte de tipul produs si se apeleaza pe aceste obiecte
 * metode din cadrul clasei AbstractDAO
 */

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

public class ProductBLL {

    private List<Validator<Product>> validators;
    private ProductDAO ProductDAO;

    public ProductBLL() {
        ProductDAO = new ProductDAO();
    }

    public Product findProductById(int id) {
        Product st = ProductDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The Product with id =" + id + " was not found!");
        }
        return st;
    }
    public boolean insertInto(String[] fields){
        return ProductDAO.insert(fields);
    }
    public boolean UpdateProduct(int id, String[] fields){

        return ProductDAO.update(id, fields);
    }
    public void deleteProduct( int id){
        Product c = ProductDAO.delete(id);
    }
    public List<Product> selectProduct(){
        List<Product> list= ProductDAO.findAll();
        return list;
    }
    public List<Integer> findIds(){
        List <Integer> list = ProductDAO.findIds();
        return list;
    }
    public List<String> findNames(){
        List < String> list = ProductDAO.findNames();
        return list;
    }
    public Product findByName(String name){
        Product p = ProductDAO.findByName(name);
        return p;
    }
}