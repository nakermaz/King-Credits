package com.kingleaks.king_credits.bot.state.admin.withdrawal_requests;

import com.kingleaks.king_credits.bot.BotService;
import com.kingleaks.king_credits.bot.command.Command;
import com.kingleaks.king_credits.service.WithdrawalOfCreditsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WithdrawalRequestsState implements Command {
    private final BotService botService;
    private final WithdrawalOfCreditsService withdrawalOfCreditsService;

    @Override
    public void execute(Update update) {
        SendMessage message = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Запросы на вывод")
                .build();
        message.setReplyMarkup(ReplyKeyboardMarkup.builder()
                .keyboardRow(new KeyboardRow(List.of(new KeyboardButton("\uD83D\uDCC3 Меню"))))
                .resizeKeyboard(true).build());
        botService.sendMessage(message);

        SendMessage result = new SendMessage();
        result.setChatId(update.getMessage().getChatId());

        String list = withdrawalOfCreditsService.getAllListWithdrawalRequest();
        if (list != null) {
            InlineKeyboardButton selectCreditsRub = new InlineKeyboardButton();
            selectCreditsRub.setText("Выбрать заявку");
            selectCreditsRub.setCallbackData("SELECT_WITHDRAWAL_REQUEST");

            List<InlineKeyboardButton> buttons = List.of(selectCreditsRub);
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            markup.setKeyboard(List.of(buttons));

            result.setText("Заявки ждущие подтверждения\n\n" + list);
            result.setReplyMarkup(markup);
            botService.sendMessage(result);
        } else {
            result.setText("Нету ничего");
            botService.sendMessage(result);
        }
    }
}
