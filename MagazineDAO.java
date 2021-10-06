package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import main.Magazine;


public class MagazineDAO {
	public Magazine insert(String title, String description, LocalDate publishDate, int subscribePrice)
			throws DAOException {
		String sqlQuery = "insert into magazine(`title`, `description`, `publish_date`, `subscribe_price`) values (?, ?, ?, ?)";

		Magazine magazine = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DAOFactory.getInstance().getConnection();

			statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, title);
			statement.setString(2, description);
			statement.setDate(3, Date.valueOf(publishDate));
			statement.setInt(4, subscribePrice);
			int rows = statement.executeUpdate();
			System.out.printf("%d row(s) added!\n", rows);

			if (rows == 0) {
				throw new DAOException("Creating magaziner failed, no rows affected!");
			} else {
				resultSet = statement.getGeneratedKeys();

				if (resultSet.next()) {
					magazine = new Magazine(resultSet.getInt(1), title, description, publishDate, subscribePrice);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Creating magazine failed!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				System.err.println("Result set can't be closed!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Prepared statement can't be closed!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println("Connection can't be closed!" + e);
			}
		}

		System.out.println(magazine + " is added to database!");
		return magazine;
	}

	public List<Magazine> readAll() throws DAOException {
		String sqlQuery = "select * from magazine";

		List<Magazine> magazineList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DAOFactory.getInstance().getConnection();
			statement = connection.prepareStatement(sqlQuery);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				magazineList.add(new Magazine(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("description"), resultSet.getDate("publish_date").toLocalDate(),
						resultSet.getInt("subscribe_price")));
			}
		} catch (SQLException e) {
			throw new DAOException("Getting list of magazines failed!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				System.err.println("Result set can't be closed!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Prepared statement can't be closed!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println("Connection can't be closed!" + e);
			}
		}

		return magazineList;
	}

	public Magazine readByID(int id) throws DAOException {
		String sqlQuery = "select * from magazine where id = ?";

		Magazine magazine = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DAOFactory.getInstance().getConnection();
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				magazine = new Magazine(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("description"), resultSet.getDate("publish_date").toLocalDate(),
						resultSet.getInt("subscribe_price"));
			}
		} catch (SQLException e) {
			throw new DAOException("Getting magazine by id failed!", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				System.err.println("Result set can't be closed!" + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Prepared statement can't be closed!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println("Connection can't be closed!" + e);
			}
		}

		System.out.println(magazine + " is getted from database!");
		return magazine;
	}

	public boolean updateByID(int id, String title, String description, LocalDate publishDate, int subscribePrice)
			throws DAOException {
		String sqlQuery = "update magazine set title = ?, description = ?, publish_date = ?, subscribe_price = ? where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			connection = DAOFactory.getInstance().getConnection();

			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, title);
			statement.setString(2, description);
			statement.setDate(3, Date.valueOf(publishDate));
			statement.setInt(4, subscribePrice);
			statement.setInt(5, id);
			int rows = statement.executeUpdate();
			System.out.printf("%d row(s) updated!\n", rows);

			if (rows == 0) {
				throw new DAOException("Updating magazine failed, no rows affected!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException("Updating magazine failed!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Prepared statement can't be closed!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println("Connection can't be closed!" + e);
			}
		}

		if (result == false) {
			System.err.println("Updating magazine failed, no rows affected!");
		} else {
			System.out.println("Magazine with ID#" + id + " is updated in database!");
		}
		return result;
	}

	public boolean delete(int id) throws DAOException {
		String sqlQuery = "delete from magazine where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			connection = DAOFactory.getInstance().getConnection();

			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);
			int rows = statement.executeUpdate();
			System.out.printf("%d row(s) deleted!\n", rows);

			if (rows == 0) {
				throw new DAOException("Deleting magazine failed, no rows affected!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException("Deleting magazine failed!", e);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Prepared statement can't be closed!" + e);
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.err.println("Connection can't be closed!" + e);
			}
		}

		if (result == false) {
			System.err.println("Deleting magazine failed, no rows affected!");
		} else {
			System.out.println("Magazine with ID#" + id + " is deleted from database!");
		}
		return result;
	}
}
