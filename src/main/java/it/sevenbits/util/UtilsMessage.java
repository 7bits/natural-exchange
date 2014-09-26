package it.sevenbits.util;

import java.util.HashMap;
import java.util.Map;

public class UtilsMessage {

    public static Map<String, String> createLetterForExchange(
        final String title, final String text, final String receiverEmail, final String offerEmail,
        final String advertisementUrlOwner, final String advertisementUrlOffer, final String ownerName
    ) {
        Map<String, String> response = new HashMap<>();
        StringBuilder message = new StringBuilder("Пользователь с email'ом : ");
        message.append(offerEmail);
        message.append("\nХочет обменяться с вами на вашу вещь : \n");
        message.append(advertisementUrlOwner);
        message.append("\nИ предлагает вам взамен : \n");
        message.append(advertisementUrlOffer);
        message.append("\nПрилагается сообщение : \n");
        message.append(text);
        message.append("\n Уважаемый(ая) ");
        message.append(ownerName);
        message.append("\nВ данный момент наш сервис находится в разработке, так что мы оставляем за вами ");
        message.append("право связаться с пользователем, заинтересовавшимся в вашей вещи.\n");
        message.append("\nЕсли ваш обмен состоится, то, пожалуйста, удалите ваши предложения с нашего сервиса.\n");
        message.append("Спасибо!");
        response.put("email", receiverEmail);
        response.put("title", title);
        response.put("text", message.toString());
        return response;
    }

    public static Map<String, String> createLetterToThingWanter() {
        Map<String, String> response = new HashMap<>();
        StringBuilder message = new StringBuilder("Мы послали сообщение об обмене владельцу заинтересовавшей вас вещи\n");
        message.append("и надеемся, что в скорем времени он(она) с вами свяжется :-)");
        response.put("title", "Уведомление об обмене");
        response.put("text", message.toString());
        return response;
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

    public static Map<String, String> createLetterForBannedUser(
            final String address, final String userName, final String messageTitle, final String banMessage
    ) {
        Map<String, String> letterParams = new HashMap<>();
        StringBuilder message = new StringBuilder("Уведомления пользователя: " + userName + '\n');
        message.append(banMessage + '\n');
        letterParams.put("email", address);
        letterParams.put("title", messageTitle);
        letterParams.put("text", message.toString());
        return letterParams;
    }
}
