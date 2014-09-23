package it.sevenbits.util;

import java.util.HashMap;
import java.util.Map;

public class UtilsMessage {

    public static Map<String, String> createLetterForExchange(final String title, final String text, final String receiverEmail, final String offerEmail,
        final String advertisementUrlOwner, final String advertisementUrlOffer, final String ownerName) {
        Map<String, String> response = new HashMap<>();
        StringBuilder message = new StringBuilder("Пользователь с email'ом : ");
        message.append(offerEmail);
        message.append("\nХочет обменяться с вами на вашу вещь : \n");
        message.append(advertisementUrlOwner);
        message.append("\nИ предлагает вам взамен : \n");
        message.append(advertisementUrlOffer);
        message.append("\nПрилагается сообщение : \n");
        message.append(text);
        message.append("\n Уважаемый ");
        message.append(ownerName);
        message.append("\nПока что наш сервис находится в разработке, так что мы оставляем за вами ");
        message.append("право связаться с заинтересованным пользователем на вашу вещь.\n");
        message.append("\nЕсли ваш обмен состоится, то, пожалуйста, удалите ваши объявления с нашего сервиса.\n");
        message.append("Спасибо!");
        response.put("email", receiverEmail);
        response.put("title", title);
        response.put("text", message.toString());
        return response;
    }

    public static Map<String, String> createLetterToUserFromModerator(
        final String advertisementTitle, final String receiverEmail, final String moderatorAction,
        final String advertisementText, final String userName, final String emailTitle
    ) {
        Map<String, String> response = new HashMap<>();
        StringBuilder message = new StringBuilder(userName);
        message.append("\nВаше объявление с заголовком : ");
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
        message.append("Всего хорошего");
        letterParams.put("email", address);
        letterParams.put("title", messageTitle);
        letterParams.put("text", message.toString());
        return letterParams;
    }
}
