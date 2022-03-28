package uz.pdp.menus;

import static uz.pdp.utils.Print.println;

/**
 * @author Narzullayev Husan, Thu 10:13 AM. 12/2/2021
 */
public class Menu {
    public static void authMenu() {
        println("1. Login");
        println("2. Create account \uD83D\uDC68\u200D⚕️");
        exit();
    }

    public static void mainMenu() {
        println("1. Search \uD83D\uDD0D");
        println("2. Send message \uD83D\uDD8A");
        println("3. Show Inboxes \uD83D\uDCE8");
        println("4. Menu  \uD83D\uDDC4");
        println("5. Logout");
        exit();
    }

    public static void settingMenu() {
        println("1. Contact services");
        println("2. Group services");
        println("3. Channel services");
        println("4. All requests");
        println("5. Profile Settings");
        println("6. Chat counter");
        back();
    }

    private static void back() {
        println("(b). BACK ");
    }

    private static void exit() {
        println("EXIT-> e..");
    }

    public static void contactServiceMenu() {
        println("1. Add contact");
        println("2. Delete contact");
        println("3. Block contact");
        println("4. Unblock contact");
        back();
    }

    public static void groupServiceMenu() {
        println("1. Create new group");
        println("2. Delete group");
        println("3. Rename group");
        back();
    }

    public static void channelServiceMenu() {
        println("1. Create new channel");
        println("2. Delete channel");
        println("3. Rename channel");
        back();
    }

    public static void profileServiceMenu() {
        println("1. Change name");
        println("2. Change username");
        back();
    }

    public static void searchMenu() {
        println("1. Among Users");
        println("2. Among Groups");
        println("3. Among Channels");
        back();
    }

    public static void showMenu() {
        println("1. Show User Chats");
        println("2. Show Group Chats");
        println("3. Show Channels");
        back();
    }
}
