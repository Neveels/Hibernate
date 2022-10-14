package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class AddressBookRunner {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        AddressBookRunner addressBookRunner = new AddressBookRunner();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            menu();
            int value = scanner.nextInt();
            switch (value) {
                case 1 -> addressBookRunner.listAddressBook().forEach(System.out::println);
                case 2 -> addressBookRunner.addAddressBook("Ilya", "Ignatovich", "Finland", "neevels@mail.ru", "+375297694430", 20);
                case 3 -> addressBookRunner.updateAddressBook(1,38);
                case 4 -> addressBookRunner.removeAddressBook(2);
                default -> {return;}

            }
        }

    }

    public void addAddressBook(String name, String surname, String country, String email, String phoneNumber, int age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        AddressBook addressBook = new AddressBook(1, age, country, name, surname, phoneNumber, email);
        session.save(addressBook);
        transaction.commit();
        session.close();
    }

    public List<AddressBook> listAddressBook() {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List<AddressBook> addressBooks = session.createQuery("FROM AddressBook").list();

        transaction.commit();
        session.close();
        return addressBooks;
    }

    public void updateAddressBook(int id, int age) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        AddressBook addressBook = (AddressBook) session.get(AddressBook.class, id);
        addressBook.setAge(age);
        session.update(addressBook);
        transaction.commit();
        session.close();
    }

    public void removeAddressBook(int id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        AddressBook addressBook = (AddressBook) session.get(AddressBook.class, id);
        session.delete(addressBook);
        transaction.commit();
        session.close();
    }

    public static void menu() {
        System.out.println("1. Read all table");
        System.out.println("2. Add row");
        System.out.println("3. Update row");
        System.out.println("4. Delete row");
        System.out.println("Another one -> Shut down");
    }

}
