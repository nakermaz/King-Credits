package com.kingleaks.king_credits.bot.state.user.leaderboard;

import com.kingleaks.king_credits.bot.BotService;
import com.kingleaks.king_credits.bot.command.Command;
import com.kingleaks.king_credits.repository.StateImageRepository;
import com.kingleaks.king_credits.service.TelegramUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.ByteArrayInputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LeaderboardState implements Command {
    private String stateName = "Таблица лидеров";
    private final StateImageRepository stateImageRepository;
    private final TelegramUsersService telegramUsersService;
    private final BotService botService;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        String result = telegramUsersService.getLeaderboard();
        if (!stateImageRepository.isStateImageHasPictureByName(stateName)){
            byte[] photoData = stateImageRepository.findByNameState(stateName).getPhotoData();

            ByteArrayInputStream inputStream = new ByteArrayInputStream(photoData);
            InputFile inputFile = new InputFile(inputStream, "photo.jpg");
            SendPhoto returnPhoto = new SendPhoto();
            returnPhoto.setChatId(chatId.toString());
            returnPhoto.setPhoto(inputFile);
            returnPhoto.setCaption("\uD83D\uDCC3 Список лидеров за все время:\n" + result);
            returnPhoto.setReplyMarkup(ReplyKeyboardMarkup.builder()
                    .keyboardRow(new KeyboardRow(List.of(new KeyboardButton("\uD83D\uDCC3 Меню"))))
                    .resizeKeyboard(true).build());
            botService.sendPhoto(returnPhoto);
        } else {
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text("\uD83D\uDCC3 Список лидеров за все время:\n" + result)
                    .build();
            message.setReplyMarkup(ReplyKeyboardMarkup.builder()
                    .keyboardRow(new KeyboardRow(List.of(new KeyboardButton("\uD83D\uDCC3 Меню"))))
                    .resizeKeyboard(true).build());
            botService.sendMessage(message);
        }
    }
}
