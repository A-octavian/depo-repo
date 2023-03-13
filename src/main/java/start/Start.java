package start;

/**
 * Clasa MAIN
 */

import java.sql.SQLException;
import java.util.logging.Logger;
import presentation.ClientFrame;
import presentation.OrderFrame;
import presentation.ProductFrame;
public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException {
		OrderFrame o = new OrderFrame();
		ClientFrame c = new ClientFrame();
		ProductFrame p = new ProductFrame();

	}

}
