package com.github.kaa.covid19.informer.support;

import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Emoji {
    public String get(String code) {
        final com.vdurmont.emoji.Emoji emoji = EmojiManager.getForAlias(code);
        if (emoji == null) {
            return "";
        }
        return emoji.getUnicode();
    }

    public String clear(String str) {
        return EmojiParser.removeAllEmojis(str);
    }
}
