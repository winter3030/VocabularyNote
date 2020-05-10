package com.example.roomvocabularys1.audio;

public interface OnEventListener<T> {
    public void onSuccess(T object);
    public void onFailure(T object);
}
