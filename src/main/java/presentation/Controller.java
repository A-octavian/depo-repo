package presentation;

/**
 * Clasa pe post de intermediar intre interfata grafica
 * si clasele care lucreaza direct cu baza de date
 */

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.Orderr;
import model.Product;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.mysql.cj.xdevapi.Type.JSON;

public class Controller {

    public static void bill(String[] fields, int quant, int price){
        String[] factura = new String[5];
        factura[0] = "Client: " + fields[0];
        factura[1] = "Produs: " + fields[1];
        factura[2] = "Cantitate: " + quant;
        factura[3] = "Pret: " + price;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        factura[4] = formatter.format(date);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("factura.pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK);
            for (String s : factura) {
                Chunk chunk = new Chunk(s, font);
                document.add(chunk);
                document.add(new Paragraph("\n"));
            }
            document.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean insertClient(String[] fields){
        ClientBLL clientBLL = new ClientBLL();
        return clientBLL.insertInto(fields);
    }
    public static Vector<Integer> findClientIds(){
        List<Integer> list = new ArrayList<>();
        Vector<Integer> vector = new Vector<>();
        ClientBLL clientBLL = new ClientBLL();
        list = clientBLL.findIds();
        for(int i = 0 ; i < list.size();i++) {
            vector.add(list.get(i));
        }
        return vector;
    }

    public static Vector<String> findClientNames(){
        List <String> list = new ArrayList<>();
        Vector<String> vector = new Vector<>();
        ClientBLL clientBLL = new ClientBLL();
        list = clientBLL.findNames();
        for( int i = 0; i < list.size(); i++){
            vector.add(list.get(i));
        }
        return vector;
    }

    public static Vector<String> findProductNames(){
        List <String> list = new ArrayList<>();
        Vector<String> vector = new Vector<>();
        ProductBLL productBLL = new ProductBLL();
        list = productBLL.findNames();
        for( int i = 0; i < list.size(); i++){
            vector.add(list.get(i));
        }
        return vector;
    }

    public static boolean updateClient(int id, String[] fields){
        ClientBLL clientBll = new ClientBLL();
        int count = 0;
        while(fields[count] != null)
            count++;
        String[] fields1 = new String[count];
        for( int i = 0 ; i < count; i++)
            fields1[i] = fields[i];
        return clientBll.UpdateClient(id,fields1);
    }

    public static void deleteClient(int id){
        ClientBLL clientBLL = new ClientBLL();
        clientBLL.deleteClient(id);
    }

    public static boolean insertProduct(String[] fields){
        ProductBLL productBLL = new ProductBLL();
        return productBLL.insertInto(fields);
    }
    public static Vector<Integer> findProductIds(){
        List<Integer> list = new ArrayList<>();
        Vector<Integer> vector = new Vector<>();
        ProductBLL productBLL = new ProductBLL();
        list = productBLL.findIds();
        for(int i = 0 ; i < list.size();i++) {
            vector.add(list.get(i));
        }
        return vector;
    }
    public static boolean updateProduct(int id, String[] fields){
        ProductBLL productBLL = new ProductBLL();
        int count = 0;
        while(fields[count] != null)
            count++;
        String[] fields1 = new String[count];
        for( int i = 0 ; i < count; i++)
            fields1[i] = fields[i];
        return productBLL.UpdateProduct(id,fields1);
    }

    public static void deleteProduct(int id){
        ProductBLL productBLL = new ProductBLL();
        productBLL.deleteProduct(id);
    }

    public static int insertOrder(String[] fields, int quant){
        OrderBLL orderBLL = new OrderBLL();
        ProductBLL productBLL = new ProductBLL();
        ClientBLL clientBLL = new ClientBLL();
        Client c = clientBLL.findByName(fields[0]);
        Product p = productBLL.findByName(fields[1]);
        if(p.getQuant() < quant) return 0;
        else{
            Random rand = new Random();
            String[] f = new String[5];
            f[0] = String.valueOf(rand.nextInt(1000));
            f[1] = fields[0];
            f[2] = fields[1];
            f[3] = String.valueOf(quant);
            f[4] = String.valueOf(quant * p.getPrice());
            orderBLL.insertInto(f);

            String[] fields1 = new String[2];
            fields1[0] = "quant";
            fields1[1] = String.valueOf(p.getQuant() - quant);
            int id = p.getId();
            productBLL.UpdateProduct(id, fields1);
            return quant * p.getPrice();
        }
    }

    public static String[] getColoaneClient(){
        ClientBLL clientBLL = new ClientBLL();
        List<Client> list = clientBLL.selectClient();
        String[] columns = retrieveColumns(list.get(0));
        return columns;
    }

    public static String[][] getDataClient(int dim){
        ClientBLL clientBLL = new ClientBLL();
        List<Client> list = clientBLL.selectClient();
        List<Object> objList = new ArrayList<Object>(list);
        String[][] data = retrieveData(objList,dim);
        return data;
    }

    public static String[] getColoaneProduct(){
        ProductBLL productBLL = new ProductBLL();
        List<Product> list = productBLL.selectProduct();
        String[] columns = retrieveColumns(list.get(0));
        return columns;
    }

    public static String[][] getDataProduct(int dim){
        ProductBLL productBLL = new ProductBLL();
        List<Product> list = productBLL.selectProduct();
        List<Object> objList = new ArrayList<Object>(list);
        String[][] data = retrieveData(objList,dim);
        return data;
    }

    public static String[] getColoaneOrder(){
        OrderBLL orderBLL = new OrderBLL();
        List<Orderr> list = orderBLL.selectOrder();
        String[] columns = retrieveColumns(list.get(0));
        return columns;
    }

    public static String[][] getDataOrder(int dim){
        OrderBLL orderBLL = new OrderBLL();
        List<Orderr> list = orderBLL.selectOrder();
        List<Object> objList = new ArrayList<Object>(list);
        String[][] data = retrieveData(objList,dim);
        return data;
    }

    public static String[] retrieveColumns(Object object) {
        int count = 0;
        for (Field field : object.getClass().getDeclaredFields()){
            count++;
        }
        String[] coloane = new String[count];
        count = 0;
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                coloane[count] = field.getName();
                count++;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return coloane;
    }

    public static String[][] retrieveData(List<Object> list, int dim) {

        String[][] data = new String[list.size()][dim];
        int linie=0, coloana;
        for(Object object : list) {
            coloana = 0;
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(object);
                    data[linie][coloana] = String.valueOf(value);
                    coloana++;

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            linie++;
        }
        return data;
    }

}
