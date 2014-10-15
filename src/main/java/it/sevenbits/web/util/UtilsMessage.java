package it.sevenbits.web.util;

import java.util.HashMap;
import java.util.Map;

public class UtilsMessage {

    public static Map<String, String> createLetterForExchange(
        final String title, final String text, final String receiverEmail, final String offerEmail,
        final String advertisementUrlOwner, final String advertisementUrlOffer, final String ownerName,
        final String offerName
    ) {
        Map<String, String> response = new HashMap<>();
        StringBuilder message = new StringBuilder("Здравствуйте, уважаемый пользователь ");
        message.append(ownerName);
        message.append("!\n");
        message.append("Пользователь ");
        message.append(offerName);
        message.append(" предлагает вам обмен вещи ");
        message.append(advertisementUrlOwner);
        message.append(" на ");
        message.append(advertisementUrlOffer);
        if (!text.equals("")) {
            message.append(" с подписью \"");
            message.append(text);
            message.append("\"");
        }
        message.append(". Если вас заинтересовало это предложение, то вы можете связаться с данным пользователем по email-у: ");
        message.append(offerEmail);
        message.append(".\nМы расчитываем на вашу порядочность в том, что, при совершении обмена, вы удалите ваше предложение с сайта самостоятельно\n");
        message.append("С наилучшими пожеланиями, администрация проекта naturalexchange.ru");
        response.put("email", receiverEmail);
        response.put("title", title);
        response.put("text", message.toString());
        return response;
    }

    public static String createLetterToBannedUser(final String userName) {
        StringBuilder message = new StringBuilder("Уважаемый пользователь, ");
        message.append(userName);
        message.append('\n');
        message.append("Администрация сайта naturalexchange.ru посчитала некоторые ваши действия неполиткорректными, поэтому вы получаете статус забаненного пользователя.\n");
        message.append("Если вы с чем-то несогласны, то напишите нам пись на email: naturalexchangeco@gmail.com\n");
        message.append("До свидания");
        return message.toString();
    }

    public static String createLetterToUnbannedUser(final String userName) {
        StringBuilder message = new StringBuilder("Уважаемый пользователь, ");
        message.append(userName);
        message.append("!\n");
        message.append("Администрация сайта naturalexchange.ru восстанавливает вас в правах и вы теряете статус забаненного пользователя.\n");
        message.append("Возвращайтесь поскорее :-)\n");
        message.append("С наилучшими пожеланиями, администрация naturalexchange.ru");
        return message.toString();
    }

    public static Map<String, String> createLetterToUserFromModerator(
        final String advertisementTitle, final String receiverEmail, final String moderatorAction,
        final String advertisementText, final String userName, final String emailTitle
    ) {
        Map<String, String> response = new HashMap<>();
        StringBuilder message = new StringBuilder(userName);
        message.append("\nВаше предложение с заголовком : ");
        message.append(advertisementTitle);
        message.append("\nС описанием : ");
        message.append(advertisementText + '\n');
        message.append(moderatorAction);
        response.put("email", receiverEmail);
        response.put("title", emailTitle);
        response.put("text", message.toString());
        return response;
    }
//
//    public static Map<String, String> createLetterForBannedUser(
//            final String address, final String userName, final String messageTitle, final String banMessage
//    ) {
//        Map<String, String> letterParams = new HashMap<>();
//        StringBuilder message = new StringBuilder("Уведомления пользователя: " + userName + '\n');
//        message.append(banMessage + '\n');
//        letterParams.put("email", address);
//        letterParams.put("title", messageTitle);
//        letterParams.put("text", message.toString());
//        return letterParams;
//    }
}
