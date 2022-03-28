package uz.pdp;

import uz.pdp.menus.Menu;

import java.util.Objects;

import static uz.pdp.services.menuService.MenuServiceFunctions.authMenuFunction;
import static uz.pdp.services.menuService.MenuServiceFunctions.mainMenuFunction;
import static uz.pdp.session.Session.session;
import static uz.pdp.utils.Color.RED;
import static uz.pdp.utils.Input.getStr;
import static uz.pdp.utils.Print.println;

/**
 * @author Narzullayev Husan, Thu 10:05 AM. 12/2/2021
 */

public class App {


    public static void main(String[] args) {
        run();
    }

    public static void run() {
        if (Objects.isNull(session)) {
            Menu.authMenu();
            String choice = getStr("-> ");
            if (choice.startsWith("e")) {
                println(RED, "Bye....");
                return;
            }
            authMenuFunction(choice);
        } else {
            Menu.mainMenu();
            String choice = getStr("-> ");
            if (choice.startsWith("e")) {
                println(RED, "Bye....");
                return;
            }
            mainMenuFunction(choice);
        }
        run();
    }


}
