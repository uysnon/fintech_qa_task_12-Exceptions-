/**
 * Исключение повторной записи одного и того же контакта
 */
public class ContactException extends RuntimeException{
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println("Такое имя уже было в телефонной книге");
    }
}
