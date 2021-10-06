package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.User;



public class UserDAO {
	public User insert(String firstName, String lastName, String email, String password, String accessLevel)
			throws DAOException {
		String sqlQuery = "insert into user(`first_name`, `last_name`, `email`, `password`, `access_level`) values (?, ?, ?, ?, ?)";

		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DAOFactory.getInstance().getConnection();

			statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, email);
			statement.setString(4, password);
			statement.setString(5, accessLevel);
			int rows = statement.executeUpdate();
			System.out.printf("%d row(s) added!\n", rows);

			if (rows == 0) {
				throw new DAOException("Creating user failed, no rows affected!");
			} else {
				resultSet = statement.getGeneratedKeys();

				if (resultSet.next()) {
					user = new User(resultSet.getInt(1), firstName, lastName, email, password, accessLevel);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Creating user failed!", e);
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

		System.out.println(user + " is added to database!");
		return user;
	}

	public List<User> readAll() throws DAOException {
		String sqlQuery = "select * from user";

		List<User> userList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DAOFactory.getInstance().getConnection();
			statement = connection.prepareStatement(sqlQuery);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				userList.add(new User(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
						resultSet.getString("access_level")));
			}
		} catch (SQLException e) {
			throw new DAOException("Getting list of users failed!", e);
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

		return userList;
	}

	public User readByID(int id) throws DAOException {
		String sqlQuery = "select * from user where id = ?";

		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DAOFactory.getInstance().getConnection();
			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				user = new User(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
						resultSet.getString("access_level"));
			}
		} catch (SQLException e) {
			throw new DAOException("Getting user by id failed!", e);
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

		System.out.println(user + " is getted from database!");
		return user;
	}

	public User readByEmail(String email) throws DAOException {
		String sqlQuery = "select * from user where email = ?";

		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DAOFactory.getInstance().getConnection();
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, email);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				user = new User(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"),
						resultSet.getString("access_level"));
			}
		} catch (SQLException e) {
			throw new DAOException("Getting user by email failed!", e);
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

		System.out.println(user + " is getted from database!");
		return user;
	}

	public boolean updateByID(int id, String firstName, String lastName, String email, String password,
			String accessLevel) throws DAOException {
		String sqlQuery = "update user set first_name = ?, last_name = ?, email = ?, password = ?, access_level = ? where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			connection = DAOFactory.getInstance().getConnection();

			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, email);
			statement.setString(4, password);
			statement.setString(5, accessLevel);
			statement.setInt(6, id);
			int rows = statement.executeUpdate();
			System.out.printf("%d row(s) updated!\n", rows);

			if (rows == 0) {
				throw new DAOException("Updating user failed, no rows affected!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException("Updating user failed!", e);
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
			System.err.println("Updating user failed, no rows affected!");
		} else {
			System.out.println("User with ID#" + id + " is updated in database!");
		}
		return result;
	}

	public boolean updateByEmail(String firstName, String lastName, String email, String password, String accessLevel)
			throws DAOException {
		String sqlQuery = "update user set first_name = ?, last_name = ?, password = ?, access_level = ? where email = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			connection = DAOFactory.getInstance().getConnection();

			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, password);
			statement.setString(4, accessLevel);
			statement.setString(5, email);
			int rows = statement.executeUpdate();
			System.out.printf("%d row(s) updated!\n", rows);

			if (rows == 0) {
				throw new DAOException("Updating user failed, no rows affected!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException("Updating user failed!", e);
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
			System.err.println("Updating user failed, no rows affected!");
		} else {
			System.out.println("User with email: " + email + " is updated in database!");
		}
		return result;
	}

	public boolean delete(int id) throws DAOException {
		String sqlQuery = "delete from user where id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		boolean result = false;

		try {
			connection = DAOFactory.getInstance().getConnection();

			statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, id);
			int rows = statement.executeUpdate();
			System.out.printf("%d row(s) deleted!", rows);

			if (rows == 0) {
				throw new DAOException("Deleting user failed, no rows affected!");
			} else {
				result = true;
			}
		} catch (SQLException e) {
			throw new DAOException("Deleting user failed!", e);
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
			System.err.println("Deleting user failed, no rows affected!");
		} else {
			System.out.println("User with ID#" + id + " is deleted from database!");
		}
		return result;
	}
}
