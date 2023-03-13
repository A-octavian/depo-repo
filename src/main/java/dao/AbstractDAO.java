package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import connection.ConnectionFactory;

/**
 * Clasa corespunzatoare metodelor de inserare, stergere, updatare
 * si actualizare a bazei de date
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}

	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	private String createInsertQuery(String[] fields) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ");
		sb.append(" INTO ");
		sb.append(type.getSimpleName());
		sb.append(" VALUES " + "( ");
		for( String s : fields)
			sb.append("?,");
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}

	private String createUpdateQuery(String[] fields) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
		sb.append(" SET ");
		for(int i = 0; i < fields.length; i+=2)
			sb.append(fields[i] + " = ?,");
		sb.deleteCharAt(sb.length()-1);
		sb.append(" WHERE id =? ");
		return sb.toString();
	}

	private String createDeleteQuery(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE id =" + id);
		return sb.toString();
	}

	public List<String> findNames() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM " + type.getSimpleName();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createNames(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM " + type.getSimpleName();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	public List<Integer> findIds(){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT id FROM " + type.getSimpleName();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createIds(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findIds " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	public List<String> createNames(ResultSet resultSet) {
		List<String> list = new ArrayList<>();
		try {
			int count = 1;
			while (resultSet.next()) {
				String nume = resultSet.getString(2);
				list.add(nume);
				count++;
			}
			return list;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Integer> createIds(ResultSet resultSet) {
		List<Integer> list = new ArrayList<>();
		try {
			int count = 1;
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				list.add(id);
				count++;
			}
			return list;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	public T findByName(String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("name");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T)ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean insert(String fields[]) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createInsertQuery(fields);
		for(String s : fields)
			if( s.equals("")) return false;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			int count = 1;
			for(Field field : type.getDeclaredFields()) {
				if( field.getGenericType().getTypeName() == "int" ){
					statement.setInt(count,Integer.parseInt(fields[count-1]));
			}
				else{
					statement.setString(count,fields[count -1]);
			}
				count++;
			}
			int t=statement.executeUpdate();
			if(t>0) return true;
			else return false;

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:Insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return false;
	}

	public boolean update(int id, String[] fields) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		for( String s : fields)
			if(s.equals("")) return false;
		String query = createUpdateQuery(fields);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			int count = 1;
			for( int i = 0; i < fields.length; i+=2) {
				for (Field field : type.getDeclaredFields()) {
					if (field.getName() == fields[i]) {
						if (field.getGenericType().getTypeName() == "int")
							statement.setInt(count, Integer.parseInt(fields[i + 1]));
						else {
							statement.setString(count, fields[i + 1]);
						}
						break;
					}
				}
				count++;
			}
			statement.setInt(count,id);
			if(statement.executeUpdate() > 0) return true;

			else return false;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:Update " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return false;
	}

	public T delete(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createDeleteQuery(id);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
			return null;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:DElete " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}
}
