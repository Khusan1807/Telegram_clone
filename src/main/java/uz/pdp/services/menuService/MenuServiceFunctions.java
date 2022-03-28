package uz.pdp.services.menuService;

import uz.pdp.menus.Menu;
import uz.pdp.services.authService.AuthService;
import uz.pdp.services.channelService.ChannelService;
import uz.pdp.services.contactService.ContactService;
import uz.pdp.services.groupService.GroupService;
import uz.pdp.services.messageService.MessageService;
import uz.pdp.services.profileService.ProfileService;

import static uz.pdp.utils.Color.RED;
import static uz.pdp.utils.Input.getStr;
import static uz.pdp.utils.Print.println;

/**
 * @author Narzullayev Husan, Fri 2:57 PM. 12/3/2021
 */
public class MenuServiceFunctions {
    public static void mainMenuFunction(String choice) {
        switch (choice) {
            case "1" -> MenuService.search();
            case "2" -> mainMenuCase2();
            case "3" -> {
                Menu.showMenu();
                String opt = getStr("? :");
                if (opt.startsWith("b")) return;
                mainMenuCase3(opt);
            }
            case "4" -> {
                Menu.settingMenu();
                String option = getStr("? :");
                if (option.startsWith("b"))
                    return;
                settingsMenuCase4(option, choice);
            }
            case "5" -> AuthService.logout();
            default -> println(RED, "Wrong choice ");
        }
    }

    private static void settingsMenuCase4(String option, String choice) {
        switch (option) {
            case "1" -> settingsMenuCase1();
            case "2" -> settingsMenuCase2();
            case "3" -> settingsMenuCase3(choice);
            case "4" -> ProfileService.showAllRequests();
            case "5" -> settingsMenuCase5();
            case "6" -> MenuService.chatCouters();
            default -> println(RED, "Wrong option!!!");
        }
    }

    public static void settingsMenuCase5() {
        Menu.profileServiceMenu();
        String opt = getStr("? : ");
        switch (opt) {
            case "1" -> ProfileService.changeName();
            case "2" -> ProfileService.changeUsername();
        }
    }

    public static void settingsMenuCase3(String choice) {
        Menu.channelServiceMenu();
        String opt = getStr("? : ");
        switch (opt) {
            case "1" -> ChannelService.creatNewChannel();
            case "2" -> ChannelService.DeleteChannel();
            case "3" -> ChannelService.RenameChannel();
            default -> {
                println(RED, "Wrong choise");
                mainMenuFunction(choice);
            }
        }
    }

    public static void settingsMenuCase2() {
        Menu.groupServiceMenu();
        String opt = getStr("? : ");
        switch (opt) {
            case "1" -> GroupService.creatNewGroup();
            case "2" -> GroupService.DeleteGroup();
            case "3" -> GroupService.RenameGroup();
        }
    }

    public static void settingsMenuCase1() {
        Menu.contactServiceMenu();
        String opt = getStr("? : ");
        switch (opt) {
            case "1" -> ContactService.addContact();
            case "2" -> ContactService.deleteContact();
            case "3" -> ContactService.BlockContact();
            case "4" -> ContactService.UnblockContact();
        }
    }

    public static void mainMenuCase3(String opt) {
        switch (opt) {
            case "1" -> MessageService.showMessage();
            case "2" -> GroupService.showMessageGroup();
            case "3" -> ChannelService.showChannelMessage();
        }
    }

    public static void mainMenuCase2() {
        AuthService.SendingMenu();
        String op = getStr("? : ");
        switch (op) {
            case "1" -> MessageService.sendMessage();
            case "2" -> GroupService.sentMessageGroup();
            case "3" -> ChannelService.sentMessageChannel();
            default -> println(RED, "Wrong option!!!");
        }
    }

    public static void authMenuFunction(String choice) {
        if (choice.equals("1")) {
            AuthService.login();
        } else if (choice.equals("2")) {
            AuthService.creatNewAccount();
        } else {
            println(RED, "Wrong choice ");
        }
    }
}
