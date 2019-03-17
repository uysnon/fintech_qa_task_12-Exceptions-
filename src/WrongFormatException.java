/**
 * Исключение неправильного формата ввода записи.
 */
public class WrongFormatException extends RuntimeException {
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println("Неправильный формат ввода");
    }
}
