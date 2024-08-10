package com.kingleaks.king_credits.bot.state.help;

import com.kingleaks.king_credits.bot.BotService;
import com.kingleaks.king_credits.bot.command.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class HelpState implements Command {
    private final BotService botService;

    public HelpState(BotService botService) {
        this.botService = botService;
    }

    @Override
    public void execute(Update update) {
        SendMessage message = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Это страница помощи. Здесь вы можете найти ответы на часто задаваемые вопросы.")
                .build();

        botService.sendMessage(message);
    }
}
