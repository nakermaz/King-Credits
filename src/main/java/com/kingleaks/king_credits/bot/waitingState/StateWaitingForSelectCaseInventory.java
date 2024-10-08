package com.kingleaks.king_credits.bot.waitingState;

import com.kingleaks.king_credits.domain.entity.StatePaymentHistory;
import com.kingleaks.king_credits.service.CasesService;
import com.kingleaks.king_credits.service.StateManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StateWaitingForSelectCaseInventory implements StateWaitingQueryHandler {
    private final StateManagerService stateManager;
    private final CasesService casesService;

    public SendMessage waitingForSelectCaseInventory(StatePaymentHistory paymentHistory,
                                              Long chatId, String messageText, Long telegramUserID){
        if (paymentHistory != null){
            try {
                Long selectId = Long.parseLong(messageText);
                String result = casesService.selectCaseForOpen(selectId);
                SendMessage message = new SendMessage();
                message.setChatId(chatId);

                if (result != null){
                    message.setText(result);

                    InlineKeyboardButton openThisCase = new InlineKeyboardButton();
                    openThisCase.setText("Открыть кейс");
                    openThisCase.setCallbackData("OPEN_CASE_INVENTORY__"+selectId);

                    List<InlineKeyboardButton> buttons = List.of(openThisCase);
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    markup.setKeyboard(List.of(buttons));
                    message.setReplyMarkup(markup);

                    stateManager.deleteUserState(telegramUserID);
                    return message;
                } else {
                    message.setText("Вы не правильно указали номер кейса, отправьте корректный номер кейса например 1");
                    return message;
                }
            } catch (NumberFormatException e) {
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Ошибка: введите корректную сумму.");

                return message;
            }
        }
        return null;
    }

    @Override
    public boolean canHandle(String stateStatus) {
        return "WAITING_FOR_SELECT_CASE_INVENTORY".equals(stateStatus);
    }

    @Override
    public SendMessage handle(StatePaymentHistory paymentHistory, Long chatId, String messageText, Long telegramUserID) {
        return waitingForSelectCaseInventory(paymentHistory, chatId, messageText, telegramUserID);
    }
}
