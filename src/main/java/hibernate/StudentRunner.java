package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class StudentRunner {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        StudentRunner studentRunner = new StudentRunner();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            menu();
            int value = scanner.nextInt();
            switch (value) {
                case 1 -> studentRunner.listAddressBook().forEach(System.out::println);
                case 2 -> studentRunner.addAddressBook("Ilya", "Ignatovich", "Finland", "neevels@mail.ru", "+375297694430", 20);
                case 3 -> studentRunner.updateAddressBook(4, 38);
                case 4 -> studentRunner.removeAddressBook(2);
                default -> {
                    return;
                }

            }
        }

    }

    public void addAddressBook(String name, String surname, String university, String email, String phoneNumber, int age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Student addressBook = new Student(1, age, university, name, surname, phoneNumber, email);
        session.save(addressBook);
        transaction.commit();
        session.close();
    }

    public List<Student> listAddressBook() {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List<Student> students = session.createQuery("from Student", Student.class).getResultList();

        transaction.commit();
        session.close();
        return students;
    }

    public void updateAddressBook(int id, int age) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Student student = (Student) session.get(Student.class, id);
        student.setAge(age);
        session.update(student);
        transaction.commit();
        session.close();
    }

    public void removeAddressBook(int id) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Student student = (Student) session.get(Student.class, id);
        session.delete(student);
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
