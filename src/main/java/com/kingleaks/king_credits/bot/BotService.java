package com.kingleaks.king_credits.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;

public interface BotService {
    void sendMessage(SendMessage message);
    void deleteMessage(DeleteMessage message);
    void sendPhoto(SendPhoto message);
    void sendEditMessageMedia(EditMessageMedia messageMedia);
}
