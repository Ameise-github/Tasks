import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /*Цвета для вывод текста*/
    //region
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    //endregion

    public static void main(String[] Args) {
        ArrayList<String> todoList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nВведите команду. Если не знаете что, то наберите HELP: ");
            System.out.print(">> ");
            String command = sc.nextLine();
            Integer pos = null;   //Передаваемое число
            StringBuilder nameWork = new StringBuilder();   //Передаваемое сообщение

            String[] temp = command.split(" ");
            if (temp.length > 1) {
                pos = new Main().tryParsInt(temp[1]);   //Передаваемое число
                if (pos != null) {
                    for (int i = 2; i < temp.length; i++) {
                        nameWork.append(temp[i]).append(" ");
                    }
                } else {
                    for (int i = 1; i < temp.length; i++) {
                        nameWork.append(temp[i]).append(" ");
                    }
                }
            }

            switch (temp[0]) {
                case "LIST":
                    new Main().viewList(todoList);
                    continue;
                case "ADD":
                    new Main().addList(todoList, pos, nameWork);
                    continue;
                case "EDIT":
                    new Main().editList(todoList, pos, nameWork);
                    continue;
                case "DELETE":
                    new Main().deletList(todoList, pos);
                    continue;
                case "HELP":
                    new Main().help();
                    continue;
                case "EXIT":
                    System.out.println("Программа завершила работу!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Введенная команда не распознана. Попробуйте еще раз!\n");
                    new Main().help();
                    continue;
            }
        }
    }

    /*Вывод списка дел*/
    public void viewList(ArrayList<String> list) {
        if (list.size() == 0) {
            System.out.println("Список дел пуст.");
        } else {
            System.out.println("Список дел:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println("\t" + (i + 1) + " - " + list.get(i));
            }
        }
    }

    /*help*/
    public void help() {
        System.out.println("Введите одну из следующих команд: ");
        System.out.println(ANSI_RED + "LIST" + ANSI_RESET + " - ВЫВОД ВСЕГО СПИСКА ДЕЛ.");
        System.out.println(ANSI_RED + "ADD название дела" + ANSI_RESET + " - ДОБАВЛЕНИЕ ДЕЛА В КОНЕЦ СПИСКА.");
        System.out.println(ANSI_RED + "ADD 3 название дела" + ANSI_RESET + " - ДОБАВЛЕНИЕ ДЕЛА НА " + ANSI_RED
                + "3" + ANSI_RESET + " ПОЗИЦИЮ СПИСКА.");
        System.out.println(ANSI_RED + "EDIT 3 Новое название дела" + ANSI_RESET + " - ИЗМЕНЕНИЕ НАИМЕНОВАНИЯ ДЕЛА С НОМЕРОМ "
                + ANSI_RED + "3.");
        System.out.println(ANSI_RED + "DELETE 7" + ANSI_RESET + " - УДАЛЕНИЕ ДЕЛА С НОМЕРОМ " + ANSI_RED + "7.");
        System.out.println(ANSI_RED + "EXIT" + ANSI_RESET + " - ВЫХОД ИЗ ПРОГРАММЫ.");
    }

    /*Добавление  дело*/
    public void addList(ArrayList<String> list, Integer pos, StringBuilder nameWork) {
        if (pos != null && (pos <= 0 || pos > list.size())) {
            if (list.size() == 0 || list.size() == 1) {
                System.out.println("Введенный номер находится за пределами списка дел. Номер может иметь значение 1.");
            } else {
                System.out.println("Введенный номер находится за пределами списка дел. Номер может находится в интервале от 1 - "
                        + list.size() + ".");
            }
        } else if(nameWork.length() == 0){
            System.out.println("Введите наименование дела!");
        } else if (pos != null && pos > 0 && pos < list.size()) {
            list.add((pos-1), nameWork.toString());
            System.out.println("Дело добавлено в список на позиию " + pos);
        } else {
            list.add(nameWork.toString());
            System.out.println("Дело добавлено в конец списка.");
        }
    }

    /*Изменение наименование дела*/
    public void editList(ArrayList<String> list, Integer pos, StringBuilder newNameWork) {
        if (pos == null) {
            System.out.println("Введите номер для изменения наименования дела!");
        } else if(newNameWork.length() == 0){
            System.out.println("Введите наименование!");
        } else if ((pos - 1 > list.size() - 1) || pos <= 0) {
            if (list.size() == 0 || list.size() == 1) {
                System.out.println("Введенный номер находится за пределами списка дел. Номер может иметь значение 1.");
            } else {
                System.out.println("Введенный номер находится за пределами списка дел. Номер может находится в интервале от 1 - "
                        + list.size() + ".");
            }
        } else {
            list.set((pos - 1), newNameWork.toString());
            System.out.println("Дело было изменено.");
        }
    }

    /*удаление из списка*/
    public void deletList(ArrayList<String> list, Integer pos) {
        if (pos == null) {
            System.out.println("Введите номер для удаления из списка!");
        } else if ((pos - 1 > list.size() - 1) || pos <= 0) {
            if (list.size() == 0 || list.size() == 1) {
                System.out.println("Введенный номер находится за пределами списка дел. Номер может иметь значение 1.");
            } else {
                System.out.println("Введенный номер находится за пределами списка дел. Номер может находится в интервале от 1 - "
                        + list.size() + ".");
            }
        } else {
            list.remove((int) pos - 1);
            System.out.println("Дело с номером " + pos + " успешно удалено!");
        }
    }

    /*Преобразование в int*/
    private Integer tryParsInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
