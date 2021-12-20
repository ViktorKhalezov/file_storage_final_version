package ru.geekbrains.client;

import ru.geekbrains.common.AbstractMessage;

public interface Callback {

    void onReceive(AbstractMessage msg);
}

