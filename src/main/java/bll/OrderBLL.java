package bll;
/**
 * Clasa in care se creeaza obiecte de tipul order si se apeleaza pe aceste obiecte
 * metode din cadrul clasei AbstractDAO
 */

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import bll.validators.Validator;
import dao.OrderDAO;
import model.Orderr;

public class OrderBLL {

    private List<Validator<Orderr>> validators;
    private OrderDAO orderDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    public boolean insertInto(String[] fields){
        return orderDAO.insert(fields);
    }
    public List<Orderr> selectOrder(){
        List<Orderr> list= orderDAO.findAll();
        return list;
    }

}