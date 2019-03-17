import java.io.*;
import java.util.Scanner;

/**
 * Главный класс задания.
 *
 * @author Gorkin Alexander
 * @version 0.1 17.03.2019
 */
public class Main {
    private final static String REGEX = "-";
    private final static String FILE_NAME = "book.txt";
    private final static String COPY_FILE_NAME = "file_copy.txt";

    /**
     * Вызываемый метод класса.
     *
     * @param args параметры командной строки.
     */
    public static void main(String[] args) {
        File file = new File(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        int k = -1;
        while (k != 0) {
            System.out.println("\n0. Завершить запись"
                    + "\n1. Вывести содержимое файла"
                    + "\n2. Добавить контакт в телефонную книгу"
                    + " ( формат: ИМЯ-ТЕЛЕФОН )"
            );
            do {
                k = scanner.nextInt();
            } while ((k < 0) || (k > 2));
            if (k == 1) {
                printBook(file);
            } else if (k == 2) {
                System.out.print("Ожидается ввод...  ");
                addRecord(file, scanner.next());
            }
        }
//        new File(COPY_FILE_NAME).delete();
    }

    /**
     * Добавление нового контакта в телефонную книгу.
     *
     * @param file   файл, в который производится запись
     * @param string строка новго контакта
     */
    private static void addRecord(File file, String string) {
        File fileCopy = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            fileCopy = new File(COPY_FILE_NAME);
            br = new BufferedReader(new FileReader(file));
            bw = new BufferedWriter(new FileWriter(fileCopy));
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            if (!isFormat(string)) {
                throw new WrongFormatException();
            }
            if (isExist(file, string)) {
                throw new ContactException();
            }
            bw.write(string);
            bw.newLine();
            br.close();
            bw.close();
            file.delete();
            fileCopy.renameTo(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WrongFormatException e) {
            e.printStackTrace();
        } catch (ContactException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                br.close();
                new File(COPY_FILE_NAME).delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Проверка правильности формата записи.
     *
     * @param string строка записи.
     * @return true - формат верный,
     * false - формат неверный.
     */
    private static boolean isFormat(String string) {
        String[] strings = string.split(REGEX);
        if (strings.length != 2) return false;
        return true;
    }

    /**
     * Проверка существования контакта с таким же именем.
     *
     * @param file   файл телефонной книги.
     * @param string запись на вход.
     * @return true - такое имя уже существует в книге,
     * false - в ином случае.
     */
    private static boolean isExist(File file, String string) {
        BufferedReader br = null;
        try {
            String name0 = string.split(REGEX)[0];
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String name = line.split(REGEX)[0];
                if (name.equals(name0)) return true;
            }

        } catch (
                IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }


    /**
     * Вывод содержимого файла.
     *
     * @param file входной файл.
     */
    private static void printBook(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
            {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
