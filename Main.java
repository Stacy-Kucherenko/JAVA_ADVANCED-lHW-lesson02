import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.DAOException;
import dao.MagazineDAO;
import dao.SubscribeDAO;
import dao.UserDAO;
import main.User;

public class Main {

	public static void main(String[] args) throws DAOException {
		List<User> userList = new ArrayList<>();
		userList.add(new User("Иван", "Петренко", "petrenko@gmail.com", "123456", "USER"));
		userList.add(new User("Василий", "Иванов", "vas_ivanov@gmail.com", "123456", "USER"));

		UserDAO userDAO = new UserDAO();
		userList.forEach(user -> {
			try {
				System.out.println(userDAO.insert(user.getFirstName(), user.getLastName(), user.getEmail(),
						user.getPassword(), user.getAccessLevel()));
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println(userDAO.readByID(2));
		System.out.println(userDAO.readByEmail("petrenko@gmail.com"));
		userDAO.updateByID(1, "Петя", "Питерс", "petrenko@gmail.com", "123456", "АDMIN");
		userDAO.updateByEmail("Василий", "Иванов", "vas_ivanov@gmail.com", "123456", "USER");
		userDAO.delete(1);
		userDAO.readAll().forEach(System.out::println);

		MagazineDAO magazineDAO = new MagazineDAO();
		System.out.println(
				magazineDAO.insert("Коспополитан", "Кейт Уинслет и Дикаприо в новом фильме ...",
						LocalDate.parse("2019-04-01"), 6005));
		magazineDAO.readAll().forEach(System.out::println);

		SubscribeDAO subscribeDAO = new SubscribeDAO();
		System.out.println(subscribeDAO.insert(2, 1, true, LocalDate.parse("2019-04-26"), 12));
		subscribeDAO.readAll().forEach(System.out::println);
	}
}
	


